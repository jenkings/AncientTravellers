package Entity.Players;

import TileMap.*;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import Entity.Player;
import Entity.Solid;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Dryfus extends Player 
{

	private boolean flinching;
	private long flinchTimer;
	
	
	// gliding
	private boolean gliding;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {2, 4, 2, 1, 5, 1};
	
	// animations actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int FALLING = 2;
	private static final int JUMPING = 3;
	private static final int CLIMBING = 4;
	private static final int ONLADDER = 5;

	
	public Dryfus(TileMap tm) 
	{
		super(tm);
		
		width = 60;
		height = 60;
		cwidth = 30;
		cheight = 50;
		
		climbSpeed = 2;
		moveSpeed = 0.3;
		maxSpeed = 2.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 5.0;
		jumpStart = -6.6;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		super.health = maxHealth = 3;
		
		// load sprites
		try 
		{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/dryfus.png"));
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 6; i++)
			{
				BufferedImage [] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++)
				{
						bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);						
				}
				sprites.add(bi);
			}			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}		
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		
		
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	
	
	public void setGliding(boolean b)
	{
		gliding = b;
	}

	
	public void hit(int damage)
	{
		if(flinching)
		{
			return;
		}
		health -= damage;
		if(health < 0)
		{
			health = 0;
		}
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	private void getNextPosition()
	{
		// movement
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
		else 
		{
			if(dx > 0)
			{
				dx -= stopSpeed;
				if(dx < 0)
				{
					dx = 0;
				}
			}
			else if(dx < 0) 
			{
				dx += stopSpeed;
				if(dx > 0)
				{
					dx = 0;
				}
			}
		}
		
		// jumping
		if(jumping  && !falling)
		{
			dy = jumpStart;
			falling = true;
		}
		
		
		
		if(nearLadder && up && (x%tileSize >= tileSize/2) && (x%tileSize <= tileSize/2 + 5)) {
			onLadder = true;
			dy = -climbSpeed;
		}
		if(nearLadder && down && (x%tileSize >= tileSize/2 && (x%tileSize <= tileSize/2 + 5))) {
			onLadder = true;
			dy = +climbSpeed;
		}
		if(!up && !down && onLadder) {
			dy = 0;
		}

		
		// falling
		if(falling)
		{
			if(dy > 0 && gliding){
				dy += fallSpeed * 0.1;
			}else if (!onLadder){
				dy += fallSpeed;
			}
			
			if(dy > 0){
				jumping = false;
			}
			if(dy < 0 && !jumping) {
				dy += stopJumpSpeed;
			}
			if(dy > maxFallSpeed){
				dy = maxFallSpeed;
			}
		}
		
		if(!nearLadder)
			onLadder = false;
	}
	
	public void update(ArrayList<Solid> solids) 
	{
		// update position
		getNextPosition();
		checkTileMapCollision();
		super.checkSolidCollision(solids);
		setPosition(xtemp, ytemp);
		
		
		// check done flinching 
		if(flinching)
		{
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000)
			{
				flinching = false;
			}
		}else if(onLadder) {
			if(currentAction != CLIMBING && dy != 0) {
				currentAction = CLIMBING;
				animation.setFrames(sprites.get(CLIMBING));
				animation.setDelay(100);
				width = 60;
			}else if(currentAction != ONLADDER && dy == 0){
				currentAction = ONLADDER;
				animation.setFrames(sprites.get(ONLADDER));
				animation.setDelay(500);
				width = 60;
			}
			
		}
		
		else if(dy > 0)		
		{
			if(currentAction != FALLING)
			{
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 60;
			}
		}
		else if(dy < 0)
		{
			if(currentAction != JUMPING)
			{
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 60;
			}
		}
		else if(left || right)
		{
			if(currentAction != WALKING)
			{
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(80);
				width = 60;
			}
		}
		else 
		{
			if(currentAction != IDLE)
			{
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 60;		
			}
		}		
		
		if(right) 
		{
			facingRight = true;			
		}
		if(left)
		{
			facingRight = false;
		}
		
		animation.update();
		
	}
	
	public void checkAttack(ArrayList<Enemy> enemies)
	{
		// loop through enemies
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			
			
			// check enemy collision
			if(intersects(e))
			{
				hit(e.getDamage());
			}
			
		}
	}
	
	public void draw(Graphics2D g){
		this.drawDialog(g);
		if(health>0) {
			setMapPosition();
			// draw player
			if(flinching)
			{
				long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
				if(elapsed / 100 % 2  == 0)
				{
					return;
				}
			}		
			super.draw(g);
		}
	}
}