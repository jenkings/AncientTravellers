package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import Entity.Controller;
import Entity.Enemy;
import Entity.Exit;
import Entity.Explosion;
import Entity.HUD;
import Entity.Controllers.Lever;
import Entity.Enemies.Slugger;
import Entity.Players.Aporis;
import Entity.Players.Dryfus;
import Entity.Players.Eustac;
import GameState.GameStateManager;
import GameState.LevelState;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;
import Workers.BlockWorker;

public class Level1State extends LevelState
{
	private TileMap tileMap;
	private Background bg;
	
	
	public Level1State(GameStateManager gsm){
		super(gsm);
		init();
	}
	
	public void init() {
		tileMap = new TileMap(60);
		tileMap.loadTiles("/Tilesets/desert.png");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		exit = new Exit(tileMap,1200,500,gsm,3);
		
		bg = new Background("/Backgrounds/desertbg.png", 0.1);
		eustac = new Eustac(tileMap);
		eustac.setPosition(1300, 150);
		
		aporis = new Aporis(tileMap);
		aporis.setPosition(1200, 1200);
		
		dryfus = new Dryfus(tileMap);
		dryfus.setPosition(190, 1200);
		
		hud = new HUD(eustac,aporis,dryfus);
		hud.setActualPlayer(actualPlayer);
		
		populateEnemies();
		createControlls();
		
		explosions = new ArrayList<Explosion>();
		
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	eustac.say(GamePanel.LOCALISATION.getWord("lvl1_intro1"));
		            }
		        }, 
		        1000 
		);
		
	}
	
	private void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		
		Slugger s; 
		Point[] points = new Point[] {
			new Point(50,50),
			new Point(200,50),
			new Point(80,200)
		};
		for(int i = 0; i < points.length; i++){
		 	s = new Slugger(tileMap);
		 	s.setPosition(points[i].x*2, points[i].y*2);
			enemies.add(s);
		}
	}
	
	private void createControlls(){
		controllers = new ArrayList<Controller>();
		Controller c; 
		BlockWorker w;
		c = new Lever(tileMap, 1200, 150);
		w = new BlockWorker(tileMap);
		w.addChangingBlock(new Point(14,3),0,9);
		w.addChangingBlock(new Point(15,3),0,9);
		w.addChangingBlock(new Point(16,3),0,9);
		w.addChangingBlock(new Point(17,3),0,9);
		c.setWorker(w);
		controllers.add(c);
			
	}
	
	
	public void update() {
		checkAlive();
		// update player
		eustac.update();
		aporis.update();
		dryfus.update();
		switch (actualPlayer) {
        	case 0:  tileMap.setPosition(GamePanel.WIDTH / 2 - eustac.getx(), GamePanel.HEIGHT / 2 - eustac.gety()); break;
        	case 1:  tileMap.setPosition(GamePanel.WIDTH / 2 - aporis.getx(), GamePanel.HEIGHT / 2 - aporis.gety());break;
        	case 2:  tileMap.setPosition(GamePanel.WIDTH / 2 - dryfus.getx(), GamePanel.HEIGHT / 2 - dryfus.gety());break;
		}
		eustac.checkAttack(enemies);
		aporis.checkAttack(enemies);
		dryfus.checkAttack(enemies);
		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++){
			Enemy e  = enemies.get(i);
			e.update();
			if(e.isDead())
			{
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		
		// update all explosions
		for(int i = 0; i < explosions.size(); i ++){
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove())
					{
						explosions.remove(i);
						i--;
					}
		}
		
		//Check if characters are near controller
		aporis.setNearestController(null);
		eustac.setNearestController(null);
		dryfus.setNearestController(null);
		for(int j = 0; j < controllers.size(); j++){
			if(controllers.get(j).intersects(aporis)){aporis.setNearestController(controllers.get(j));}
			if(controllers.get(j).intersects(eustac)){eustac.setNearestController(controllers.get(j));}
			if(controllers.get(j).intersects(dryfus)){dryfus.setNearestController(controllers.get(j));}
		}
		
		//check, if all characters are in finish zone
		int finished = 0;
		if(exit.intersects(aporis)){finished++;}
		if(exit.intersects(eustac)){finished++;}
		if(exit.intersects(dryfus)){finished++;}
		exit.setInFinish(finished);
		
	}
	
	public void draw(Graphics2D g) {
		// draw background
		bg.draw(g); 
		exit.update();
		// draw tilemap
		tileMap.draw(g);	
		exit.draw(g);
		// draw controllers
		for(int i = 0; i < controllers.size(); i++){
			controllers.get(i).draw(g);
		}
		
		// draw players
		eustac.draw(g);
		aporis.draw(g);
		dryfus.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}

		// draw explosions
		for(int i = 0; i < explosions.size(); i++)
		{
			explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		
		// draw hud
		hud.draw(g);
	}
		
}
