package Entity.Controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Controller;
import Entity.Player;
import TileMap.TileMap;

public class Lever extends Controller{

	private BufferedImage[] offSprite;
	private BufferedImage[] onSprite;
	
	public Lever(TileMap tm, int x, int y) {
		super(tm, x, y);
		width = 30;
		height = 30;
		cwidth = 10;
		cheight = 10;
		offSprite = new BufferedImage[1];
		onSprite = new BufferedImage[1];
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Controllers/lever.png"));
			
			offSprite[0] = spritesheet.getSubimage(0, 0, width, height);
			onSprite[0] = spritesheet.getSubimage(width, 0, width, height);
			
			animation = new Animation();
			animation.setFrames(offSprite);
			animation.setDelay(400);
		}catch(Exception e)
		{
			e.printStackTrace();
		
		}
	}
	
	public void use(Player player){
		if(status == false) {
			status = true;
			
			animation = new Animation();
			animation.setFrames(onSprite);
			animation.setDelay(400);
			
			if(worker != null)
				worker.doWork();
		}else {
			status = false;
			animation = new Animation();
			animation.setFrames(offSprite);
			animation.setDelay(400);
			
			if(worker != null)
				worker.undoWork();
			
		}
	}
	

	public void draw(Graphics2D g)
	{
		setMapPosition();
		super.draw(g);
	}

}
