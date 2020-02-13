package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Players.Aporis;
import Entity.Players.Dryfus;
import Entity.Players.Eustac;

public class HUD {
	private Eustac eustac;
	private Aporis aporis;
	private Dryfus dryfus;
	private BufferedImage image;
	private Font font;
	private BufferedImage[] health;
	private BufferedImage[] aporisSprites;
	private BufferedImage[] dryfusSprites;
	private BufferedImage[] eustacSprites;
	
	private int aporisState = 1;
	private int eustacState = 1;
	private int dryfusState = 1;
	
	public HUD(Eustac eu,Aporis ap, Dryfus dr)
	{
		eustac = eu;
		aporis = ap;
		dryfus = dr;
		
		health = new  BufferedImage[3]; 
		aporisSprites = new BufferedImage[3];
		dryfusSprites = new BufferedImage[3];
		eustacSprites = new BufferedImage[3];
		try 
		{
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.png"));
			health[0] = ImageIO.read(getClass().getResourceAsStream("/HUD/hp1.png"));
			health[1] = ImageIO.read(getClass().getResourceAsStream("/HUD/hp2.png"));
			health[2] = ImageIO.read(getClass().getResourceAsStream("/HUD/hp3.png"));
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/HUD/characters/aporis.png"));
			for(int i = 0; i < 3; i++){
				aporisSprites[i] = spritesheet.getSubimage(0, i * 40, 40,40);						
			}
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/HUD/characters/dryfus.png"));
			for(int i = 0; i < 3; i++){
				dryfusSprites[i] = spritesheet.getSubimage(0, i * 40, 40,40);						
			}	
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/HUD/characters/eustac.png"));
			for(int i = 0; i < 3; i++){
				eustacSprites[i] = spritesheet.getSubimage(0, i * 40, 40,40);						
			}	
			
			font = new Font("Arial", Font.PLAIN, 14);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setActualPlayer(int player) {
		if(eustac.getHealth() <= 0) eustacState = 2;else eustacState=1;
		if(aporis.getHealth() <= 0) aporisState = 2;else aporisState=1;
		if(dryfus.getHealth() <= 0) dryfusState = 2;else dryfusState=1;
		
		switch(player) {
			case 0:  eustacState = 0; break;
	        case 1:  aporisState = 0;break;
	        case 2:  dryfusState = 0;break;
		}
	}
	
	public void draw(Graphics2D g) 
	{
		g.drawImage(image, 10, 500, null);
		g.drawImage(image, 310, 500, null);
		g.drawImage(image, 610, 500, null);
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		if(eustac.getHealth() > 0)
			g.drawImage(health[eustac.getHealth()-1], 100, 519, null);
		g.drawImage(eustacSprites[eustacState], 25, 525, null);
		
		
		if(aporis.getHealth() > 0)
			g.drawImage(health[aporis.getHealth()-1], 400, 519, null);
		g.drawImage(aporisSprites[aporisState], 325, 525, null);
		
		
		if(dryfus.getHealth() > 0)
			g.drawImage(health[dryfus.getHealth()-1], 700, 519, null);
		g.drawImage(dryfusSprites[dryfusState], 625, 525, null);
	}
}