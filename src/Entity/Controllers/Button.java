package Entity.Controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Controller;
import Entity.Player;
import TileMap.TileMap;

public class Button extends Controller
{
	
	private BufferedImage[] offSprite;
	private BufferedImage[] onSprite;
	
	public Button(TileMap tm,int x, int y)
	{
		super(tm,x,y);

		width = 20;
		height = 20;
		cwidth = 5;
		cheight = 5;
		offSprite = new BufferedImage[1];
		onSprite = new BufferedImage[1];
		// load sprites
		try
		{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Controllers/button.png"));

			offSprite[0] = spritesheet.getSubimage(0, 0, width, height);
			onSprite[0] = spritesheet.getSubimage(width, 0, width, height);
			
			animation = new Animation();
			animation.setFrames(offSprite);
			animation.setDelay(400);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	public void use(Player player){
		status = true; //prepnuti prepinace
		
		//zmena animace
		animation = new Animation();
		animation.setFrames(onSprite);
		animation.setDelay(400);
		
		//vykonání workeru
		if(worker != null)
			worker.doWork();
	}
	

	public void draw(Graphics2D g)
	{
		setMapPosition();
		super.draw(g);
	}
}
