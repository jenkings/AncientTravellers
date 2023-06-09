package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import Sound.Sound;
import TileMap.TileMap;

public class Ghost extends Enemy
{
	private BufferedImage[] sprites;
	
	public Ghost(TileMap tm)
	{
		super(tm);
		
		moveSpeed = 0.5;
		maxSpeed = 0.5;
		fallSpeed = 0.2;
		maxFallSpeed = 5.00;
		 
		width = 60;
		height = 60;
		cwidth = 50;
		cheight = 50;
		
		health = maxHealth = 2;
		damage = 1;

		// load sprites
		try 
		{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/ghost.png"));
			sprites = new BufferedImage[3];
			for(int i = 0; i < sprites.length; i++)
			{
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(150);
		
		right = true;
		facingRight = true;
	}	
	
	private void getNextPosition()
	{
		if(left)
		{
			dx -= moveSpeed;
			if(dx < -maxSpeed)
			{
				dx = -maxSpeed;
			}
		}
		else if(right)
		{
			dx += moveSpeed;
			if(dx > maxSpeed)
			{
				dx = maxSpeed;
			}
		}
		
		// falling
		if(falling)
		{
			dy += fallSpeed;
		}
	}
	
	public void update()
	{
		// update position;
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check flinching
		if(flinching)
		{
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400)
			{
				flinching = false;
			}
		}
				
		// if it hits a wall, go other direction
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}

		//update animation
		animation.update();
	}
	
	public boolean isDead() {
		if(dead) {
			Sound sound = new Sound("/Sound/Effects/ghost-death.wav");
			sound.play();
		}
		
		return dead;
	}
	
	public void draw(Graphics2D g)
	{
		setMapPosition();
		super.draw(g);
	}
	
}
