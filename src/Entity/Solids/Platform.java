package Entity.Solids;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import Entity.Solid;
import TileMap.TileMap;

public class Platform extends Solid
{
	private BufferedImage[] sprites;
	
	public Platform(TileMap tm,int posx,int posy)
	{
		super(tm,posx,posy);
		moveSpeed = 0.5;
		maxSpeed = 2;
		width = 40;
		height = 25;
		cwidth = 40;
		cheight = 25;

		
		// load sprites
		try 
		{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Solids/platform.png"));
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
		animation.setDelay(50);
		right = false;
	}	
	
	private void getNextPosition()
	{
		if(right)
		{
			dx += moveSpeed;
			if(dx > maxSpeed)
			{
				dx = maxSpeed;
			}
		}
		else 
		{
			dx -= moveSpeed;
			if(dx < -maxSpeed)
			{
				dx = -maxSpeed;
			}
		}
		
	}
	
	public void update()
	{
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
	
		// if it hits a wall, go other direction
		if(right && dx == 0) {
			right = false;
		}
		else if(!right && dx == 0) {
			right = true;
		}

		//update animation
		animation.update();
	}
	
	public void draw(Graphics2D g)
	{
		setMapPosition();
		g.drawImage(animation.getImage(),
				(int) (x + xmap - width / 2), 
				(int) (y + ymap - height / 2),
				null);
	}
	
}
