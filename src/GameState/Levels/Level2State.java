package GameState.Levels;

import java.awt.Graphics2D;
import java.awt.Point;

import Entity.Controller;
import Entity.Exit;
import Entity.HUD;
import Entity.Controllers.Lever;
import Entity.Enemies.Ghost;
import Entity.Enemies.Mummy;
import Entity.Players.Aporis;
import Entity.Players.Dryfus;
import Entity.Players.Eustac;
import Entity.Solids.Platform;
import GameState.GameStateManager;
import GameState.LevelState;
import Main.GamePanel;
import Sound.Sound;
import TileMap.Background;
import TileMap.TileMap;
import Workers.BlockWorker;

public class Level2State extends LevelState{
	private TileMap tileMap;
	private Background bg;

	public Level2State(GameStateManager gsm){
		super(gsm);
	}
	
	public void init() {
		super.init();
		tileMap = new TileMap(60);
		tileMap.loadTiles("/Tilesets/desert.png");
		tileMap.loadMap("/Maps/level1-2.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		exit = new Exit(tileMap,1750,140,gsm,0);
		
		bg = new Background("/Backgrounds/desertbg.png", 0.1);
		eustac = new Eustac(tileMap);
		eustac.setPosition(400, 150);

		aporis = new Aporis(tileMap);
		aporis.setPosition(440, 150);

		dryfus = new Dryfus(tileMap);
		dryfus.setPosition(480, 150);

		hud = new HUD(eustac,aporis,dryfus);
		hud.setActualPlayer(actualPlayer);

		solids.add(new Platform(tileMap,200,280));

		populateEnemies();
		createControlls();
		
		theme = new Sound("/Sound/Background/sound1.wav");
		theme.loop();
	}
	
	private void populateEnemies(){
		Ghost s; 
		Point[] points = new Point[] {
			new Point(200,1400),
			new Point(700,1400),
			new Point(1100,800)
		};
		for(int i = 0; i < points.length; i++){
		 	s = new Ghost(tileMap);
		 	s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		Mummy m = new Mummy(tileMap);
		m.setPosition(550,215);
		enemies.add(m);
	}
	
	private void createControlls(){
		Controller c; 
		BlockWorker w;
		c = new Lever(tileMap, 1100, 325);
		w = new BlockWorker(tileMap);
		w.addChangingBlock(new Point(21,9),0,22);
		w.addChangingBlock(new Point(22,9),0,22);
		c.setWorker(w);
		controllers.add(c);
		
		c = new Lever(tileMap, 530, 800);
		w = new BlockWorker(tileMap);
		w.addChangingBlock(new Point(23,9),0,22);
		w.addChangingBlock(new Point(24,9),0,22);
		c.setWorker(w);
		controllers.add(c);
	}
	
	public void update() {
		try {
			super.update();
			if(paused) return;
			
			switch (actualPlayer) {
		    	case 0:  tileMap.setPosition(GamePanel.WIDTH / 2 - eustac.getx(), GamePanel.HEIGHT / 2 - eustac.gety()); break;
		    	case 1:  tileMap.setPosition(GamePanel.WIDTH / 2 - aporis.getx(), GamePanel.HEIGHT / 2 - aporis.gety());break;
		    	case 2:  tileMap.setPosition(GamePanel.WIDTH / 2 - dryfus.getx(), GamePanel.HEIGHT / 2 - dryfus.gety());break;
			}
		}catch(Exception e) {}
		
	}
	
	public void draw(Graphics2D g) {
		try {
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
	
			// draw solids
			for(int i = 0; i < solids.size(); i++){
				solids.get(i).draw(g);
			}
	
			// draw explosions
			for(int i = 0; i < explosions.size(); i++){
				explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
				explosions.get(i).draw(g);
			}
	
			// draw hud
			hud.draw(g);
			super.draw(g);
		}catch(Exception e) {}
	}
		
}
