package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import GameState.GameStateManager;
import TileMap.TileMap;

public class Exit extends MapObject{

	private BufferedImage[] sprites;
	private int inFinish;
	private GameStateManager gsm;
	private int nextState;
	
	public Exit(TileMap tm, int x, int y, GameStateManager gsm,int nextState){
		super(tm);
		super.setPosition(x, y);
		this.nextState = nextState;
		this.gsm = gsm;
		inFinish = 0;
		width = 80;
		height = 80;
		cwidth = 80;
		cheight = 80;
		sprites = new BufferedImage[3];
		// load sprites
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/portal.png"));

			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
					
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(100);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update() {
		animation.update();
	}
	
	public void setInFinish(int count) {
		this.inFinish = count;
		if(inFinish == 3)
			finishedLevel();
	}
	
	private void finishedLevel() {
		gsm.setState(nextState);
	}
	
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
}
