package Entity.Collectibles;

import java.awt.Graphics2D;

import Entity.MapObject;
import Entity.Player;
import TileMap.TileMap;
import Workers.Worker;

public class Collectible extends MapObject{
	protected Worker worker;
	protected int actualMove;
	protected int maxMove;

	public Collectible(TileMap tm, int x, int y){
		super(tm);
		super.setPosition(x, y);
	}
	
	public void use(Player player){
		//Play sound maybe ?
	}
	
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
}

