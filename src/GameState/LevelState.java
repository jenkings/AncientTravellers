package GameState;

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
import Entity.Players.Aporis;
import Entity.Players.Dryfus;
import Entity.Players.Eustac;

public class LevelState extends GameState{
	protected Eustac eustac;
	protected Aporis aporis;
	protected Dryfus dryfus;
	protected int actualPlayer = 0;
	protected HUD hud;
	protected ArrayList<Enemy> enemies;
	protected ArrayList<Explosion> explosions;
	protected ArrayList<Controller> controllers;
	protected Exit exit;
	
	private Font font;

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
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		/*
		g.setFont(font);
		g.setColor(Color.white);
		
		switch (actualPlayer) {
    		case 0:  g.drawString("CurrentPos: X:" + eustac.getx() + " Y: " + eustac.gety(), 2, 50);;break;
    		case 1:  g.drawString("CurrentPos: X:" + aporis.getx() + " Y: " + aporis.gety(), 2, 50);;break;
    		case 2:  g.drawString("CurrentPos: X:" + dryfus.getx() + " Y: " + dryfus.gety(), 2, 50);;break;
		}
		
		*/
		
		
	}

	public void keyPressed(int k) {
		if(eustac.getMonolog() != null) eustac.setMonolog(null);
		if(aporis.getMonolog() != null) aporis.setMonolog(null);
		if(dryfus.getMonolog() != null) dryfus.setMonolog(null);
			
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
		}
		if(k == KeyEvent.VK_ALT){
			switch (actualPlayer) {
				case 0:  eustac.interact();break;
				case 1:  aporis.interact();break;
				case 2:  dryfus.interact();break;
			}
		}
	}

	public void keyReleased(int k) {
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
	
	protected void failedLevel() {
		//TODO: restart
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
