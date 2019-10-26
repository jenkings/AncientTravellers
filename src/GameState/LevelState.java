package GameState;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import Entity.Controller;
import Entity.Enemy;
import Entity.Exit;
import Entity.Explosion;
import Entity.HUD;
import Entity.Solid;
import Entity.Players.Aporis;
import Entity.Players.Dryfus;
import Entity.Players.Eustac;
import Main.GamePanel;

public class LevelState extends GameState{
	public static boolean DEBUG = false;
	
	protected Eustac eustac;
	protected Aporis aporis;
	protected Dryfus dryfus;
	protected int actualPlayer = 0;
	protected HUD hud;
	protected ArrayList<Enemy> enemies;
	protected ArrayList<Explosion> explosions;
	protected ArrayList<Controller> controllers;
	protected ArrayList<Solid> solids;
	protected Exit exit;
	protected boolean paused = false;
	private int pauseChoice = 0;

	private Font font;
	private Font pausefont;

	public LevelState(GameStateManager gsm){
		this.gsm = gsm;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Font/joystix.ttf")));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		font = new Font("Joystix Monospace", Font.PLAIN, 15);
		pausefont = new Font("Joystix Monospace", Font.PLAIN, 30);
		init();
	}

	@Override
	public void init() {
		enemies = new ArrayList<Enemy>();
		solids = new ArrayList<Solid>();
		explosions = new ArrayList<Explosion>();
		controllers = new ArrayList<Controller>();
	}
	
	protected void checkAlive() {
		if(aporis.getHealth() <= 0 && dryfus.getHealth() <= 0 && eustac.getHealth() <= 0)
			failedLevel();
		if(actualPlayer == 0 && eustac.getHealth() <=0)
			switchActualPlayer();
		else if(actualPlayer == 1 && aporis.getHealth() <=0)
			switchActualPlayer();
		else if(actualPlayer == 2 && dryfus.getHealth() <=0)
			switchActualPlayer();
	}

	@Override
	public void update() {
		if(paused) return;
		checkAlive();
		// update player
		eustac.update();
		aporis.update();
		dryfus.update();
		
		eustac.checkAttack(enemies);
		aporis.checkAttack(enemies);
		dryfus.checkAttack(enemies);
		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++){
			Enemy e  = enemies.get(i);
			e.update();
			if(e.isDead()){
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		
		// update all explosions
		for(int i = 0; i < explosions.size(); i ++){
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()){
				explosions.remove(i);
				i--;
			}
		}
		
		// update all solids
		for(int i = 0; i < solids.size(); i ++){
			solids.get(i).update();
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

	@Override
	public void draw(Graphics2D g) {
		if(LevelState.DEBUG) {
			g.setFont(font);
			g.setColor(Color.white);
			
			switch (actualPlayer) {
	    		case 0:  g.drawString("CurrentPos: X:" + eustac.getx() + " Y: " + eustac.gety(), 2, 20);break;
	    		case 1:  g.drawString("CurrentPos: X:" + aporis.getx() + " Y: " + aporis.gety(), 2, 20);break;
	    		case 2:  g.drawString("CurrentPos: X:" + dryfus.getx() + " Y: " + dryfus.gety(), 2, 20);break;
			}
		}
		drawPauseMenu(g);
	}

	public void keyPressed(int k) {
		if(eustac.getMonolog() != null) eustac.setMonolog(null);
		if(aporis.getMonolog() != null) aporis.setMonolog(null);
		if(dryfus.getMonolog() != null) dryfus.setMonolog(null);
		
		if(k == KeyEvent.VK_F11){
			LevelState.DEBUG = !LevelState.DEBUG;
		}
		
		if(k == KeyEvent.VK_ESCAPE){
			if(this.paused == false) {
				this.pauseChoice = 0;
			}
			this.paused = !this.paused;
		}
		
		if(k == KeyEvent.VK_P){
			if(this.paused == false) {
				this.paused = true;
				this.pauseChoice = 0;
			}
		}
		
		//ovládání hry
		if(!paused) {
			if(k == KeyEvent.VK_LEFT){
				switch (actualPlayer) {
	            case 0:  eustac.setLeft(true); break;
	            case 1:  aporis.setLeft(true);break;
	            case 2:  dryfus.setLeft(true);break;
				}
			}
			if(k == KeyEvent.VK_RIGHT){
				switch (actualPlayer) {
	            	case 0:  eustac.setRight(true);break;
	            	case 1:  aporis.setRight(true);break;
	            	case 2:  dryfus.setRight(true);break;
				}
			}
			if(k == KeyEvent.VK_UP){
				switch (actualPlayer) {
					case 0:  eustac.setUp(true);break;
					case 1:  aporis.setUp(true);break;
					case 2:  dryfus.setUp(true);break;
				}
			}
			if(k == KeyEvent.VK_DOWN)
			{
				switch (actualPlayer) {
	            	case 0:  eustac.setDown(true);break;
	            	case 1:  aporis.setDown(true);break;
	            	case 2:  dryfus.setDown(true);break;
				}
			}
			if(k == KeyEvent.VK_SPACE){
				switch (actualPlayer) {
	    			case 0:  eustac.setScratching();break;
	        		case 1:  aporis.setGliding(true);break;
	        		case 2:  dryfus.setJumping(true);break;
				}
			}
			if(k == KeyEvent.VK_SHIFT){
				switch (actualPlayer) {
					case 0:  eustac.setFiring();break;
	    			case 1:  break;
	    			case 2:  break;
				}
			}
			if(k == KeyEvent.VK_CONTROL){
				switchActualPlayer();
				eustac.setUp(false);eustac.setDown(false);eustac.setLeft(false);eustac.setRight(false);
				aporis.setUp(false);aporis.setDown(false);aporis.setLeft(false);aporis.setRight(false);
				dryfus.setUp(false);dryfus.setDown(false);dryfus.setLeft(false);dryfus.setRight(false);
				
			}
			if(k == KeyEvent.VK_ALT){
				switch (actualPlayer) {
					case 0:  eustac.interact();break;
					case 1:  aporis.interact();break;
					case 2:  dryfus.interact();break;
				}
			}
		}else {
			if(k == KeyEvent.VK_UP){
				pauseChoice --;
				if(pauseChoice < 0) pauseChoice = 2;
			}
			if(k == KeyEvent.VK_DOWN){
				pauseChoice ++;
				if(pauseChoice > 2) pauseChoice = 0;
			}
			if(k == KeyEvent.VK_ENTER){
				switch(pauseChoice) {
				case 0:
					paused = false;
					break;
				case 1:
					gsm.setState(gsm.getCurrentState());
					paused = false;
					break;
				case 2:
					gsm.setState(GameStateManager.MENUSTATE);
					paused = false;
					break;
				}
			}
		}
	}

	public void keyReleased(int k) {
		if(!paused) {
			if(k == KeyEvent.VK_LEFT){
				switch (actualPlayer) {
	            	case 0:  eustac.setLeft(false);break;
	            	case 1:  aporis.setLeft(false);break;
	            	case 2:  dryfus.setLeft(false);break;
				}
			}
			if(k == KeyEvent.VK_RIGHT){
				switch (actualPlayer) {
	            	case 0:  eustac.setRight(false);break;
	            	case 1:  aporis.setRight(false); break;
	            	case 2:  dryfus.setRight(false);break;
				}
			}
			if(k == KeyEvent.VK_UP){
				switch (actualPlayer) {
	            	case 0:  eustac.setUp(false);break;
	            	case 1:  aporis.setUp(false);break;
	            	case 2:  dryfus.setUp(false);break;
				}
			}
			if(k == KeyEvent.VK_DOWN){
				switch (actualPlayer) {
	            	case 0:  eustac.setDown(false);break;
	            	case 1:  aporis.setDown(false);break;
	            	case 2:  dryfus.setDown(false);break;
				}
			}
			if(k == KeyEvent.VK_SPACE){
				switch (actualPlayer) {
	        		case 0:  break;
		        	case 1:  aporis.setGliding(false);break;
		        	case 2:  dryfus.setJumping(false);break;
				}
			}	
		}
	}

	protected void failedLevel() {
		gsm.setState(gsm.getCurrentState());
	}

	private void drawPauseMenu(Graphics2D g) {
		if(paused) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g.setColor(Color.BLACK);
			g.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			
			g.setFont(pausefont);
			g.setColor(pauseChoice == 0 ? new Color(255, 255, 50) : Color.white);
			g.drawString("RETURN", GamePanel.WIDTH/2 - 70, 280);
			g.setColor(pauseChoice == 1 ? new Color(255, 255, 50) : Color.white);
			g.drawString("RESTART", GamePanel.WIDTH/2 - 70, 330);
			g.setColor(pauseChoice == 2 ? new Color(255, 255, 50) : Color.white);
			g.drawString("QUIT", GamePanel.WIDTH/2 - 70, 380);
		}
	}

	protected void switchActualPlayer() {
		actualPlayer ++;
		if(actualPlayer >2)
				actualPlayer=0;
		hud.setActualPlayer(actualPlayer);
		
		if(actualPlayer == 0 && eustac.getHealth() <=0)
			switchActualPlayer();
		else if(actualPlayer == 1 && aporis.getHealth() <=0)
			switchActualPlayer();
		else if(actualPlayer == 2 && dryfus.getHealth() <=0)
			switchActualPlayer();
	}
}
