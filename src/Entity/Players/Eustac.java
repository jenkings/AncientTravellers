package Entity.Players;

import TileMap.*;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Arrow;
import Entity.Enemy;
import Entity.Player;
import Entity.Solid;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Eustac extends Player
{
	
	// player stuff
	private int fire;
	private int maxFire;
	private boolean flinching;
	private long flinchTimer;
	
	// fireball
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<Arrow> arrows;
	
	// scratch
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;
	
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {2, 5, 2, 4, 4, 4, 1};
	
	// animations actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int FALLING = 2;
	private static final int FIREBALL = 3;
	private static final int SCRATCHING = 4;
	private static final int CLIMBING = 5;
	private static final int ONLADDER = 6;
	
	public Eustac(TileMap tm) 
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
		
		facingRight = true;
		
		health = maxHealth = 3;
		fire = maxFire = 2500;
		
		fireCost = 200;
		fireBallDamage = 5;
		arrows = new ArrayList<Arrow>();
		
		scratchDamage = 8;
		scratchRange = 70;
		
		// load sprites
		try 
		{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/eustac.png"));
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 7; i++)
			{
				BufferedImage [] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++)
				{
					if(i != 4)
					{
						bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);						
					}
					else 
					{
						bi[j] = spritesheet.getSubimage(j * width * 2, i * height, width * 2, height);
					}
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
	public int getFire() { return fire; }
	public int getMaxFire() { return maxFire; }
	
	public void setFiring() 
	{
		firing = true;
		
	}
	
	public void setScratching() 
	{
		scratching = true;
	}

	
	public void checkAttack(ArrayList<Enemy> enemies)
	{
		// loop through enemies
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			
			//scratch attack
			if(scratching)
			{
				if(facingRight)
				{
					if(e.getx() > x && e.getx() < x + scratchRange && e.gety() > y - height / 2 && e.gety() < y + height / 2)
					{
						e.hit(scratchDamage);
					}
				}
				else 
				{
					if(e.getx() < x && e.getx() > x - scratchRange && e.gety() > y - height / 2 && e.gety() < y + height / 2)
					{
						e.hit(scratchDamage);
					}
				}				
			}
			// arrows
			for(int j = 0; j < arrows.size(); j++)
			{
				if(arrows.get(j).intersects(e))
				{
					e.hit(fireBallDamage);
					arrows.get(j).setHit();
					break;
				}
			}
			
			// check enemy collision
			if(intersects(e)){
				hit(e.getDamage());
			}
			
		}
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
		
		// can't move while attacking, except in air
		if((currentAction == SCRATCHING)  || (currentAction == FIREBALL) && !falling)
		{
			dx = 0;
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
			if (!onLadder){
				dy += fallSpeed;
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
		
		// check attack has stopped
		if(currentAction == SCRATCHING)
		{
			if(animation.hasPlayedOnce())
			{
				scratching = false;
			}
		}
		if(currentAction == FIREBALL)
		{
			if(animation.hasPlayedOnce())
			{
				firing = false;
					if(fire > fireCost)
					{
						fire -= fireCost;
						Arrow fb = new Arrow(tileMap, facingRight);
						fb.setPosition(x, y);
						arrows.add(fb);
					}
			}
		}
		
		// arrow attack
		fire += 1;
		if(fire > maxFire)
		{
			fire = maxFire;
		}
		
		//update arrows
		for(int i = 0; i < arrows.size(); i++)
		{
			arrows.get(i).update();
			if(arrows.get(i).shouldRemove())
			{
				arrows.remove(i);
				i--;
			}
		}
		
		// check done flinching 
		if(flinching)
		{
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000)
			{
				flinching = false;
			}
		}
		
		
		// set animation
		if(scratching)
		{
			if(currentAction != SCRATCHING)
			{
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
				width = 120;
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
		else if(firing) 
		{
			if(currentAction != FIREBALL)
			{
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(80);
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
		else if(left || right)
		{
			if(currentAction != WALKING)
			{
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
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
		
		animation.update();
		
		// set direction 
		if(currentAction != SCRATCHING && currentAction != FIREBALL)
		{
			if(right) 
			{
				facingRight = true;			
			}
			if(left)
			{
				facingRight = false;
			}
		}
	}
	
	public void draw(Graphics2D g)
	{
		this.drawDialog(g);
		// draw arrows
		for(int i = 0; i < arrows.size(); i++){
			arrows.get(i).draw(g);
		}
					
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