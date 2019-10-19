package Entity;

import java.awt.Graphics2D;


import TileMap.TileMap;
import Workers.Worker;

public class Controller extends MapObject
{
	
	protected boolean status = false; //on or off
	protected Worker worker;
	

	public Controller(TileMap tm, int x, int y){
		super(tm);
		super.setPosition(x, y);
	}

	public void setOn() {status = true;}
	public void setOff() {status = false;}
	public boolean getStatus() {return status;}
	
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
