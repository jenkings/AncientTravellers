package Entity.Collectibles;

import java.awt.Graphics2D;

import Entity.MapObject;
import Entity.Player;
import TileMap.TileMap;
import Workers.Worker;

public class Collectible extends MapObject {
	protected Worker worker;
	protected int actualMove;
	protected int maxMove;
	protected boolean moveDirection = true;



	public Collectible(TileMap tm, int x, int y) {
		super(tm);
		super.setPosition(x, y);
		actualMove = 0;
		maxMove = 5;
		facingRight = true; //kvůli vykreslování
	}

	public void use(Player player) {
		//Play sound maybe ?
	}

	public void getNextPosition() {
		if (moveDirection){
			if(actualMove< 5) {
				dy = 0.001;
				actualMove++;
			}
			else
				moveDirection = !moveDirection;
		}else{
			if(actualMove > -5) {
				dy = -0.001;
				actualMove --;
			}
			else
				moveDirection = !moveDirection;
		}
	}


	public void update(){
		animation.update();
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	
	
	public void draw(Graphics2D g){
		setMapPosition();
		super.draw(g);
	}
}

