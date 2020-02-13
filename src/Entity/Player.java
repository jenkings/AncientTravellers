package Entity;
import java.awt.Graphics2D;

import Dialogs.Monolog;
import Entity.Collectibles.Collectible;
import TileMap.TileMap;

public class Player extends MapObject{
	protected int health;
	protected int maxHealth;
	protected int climbSpeed;
	protected Controller nearestController;
	protected Monolog monolog;
	protected TileMap tm;
	protected Inventory inventory;
	
	
	public Player(TileMap tm) {
		super(tm);
		this.tm = tm;
		this.inventory = new Inventory();
		nearestController = null;
	}
	
	public Controller getNearestController() {
		return nearestController;
	}

	public void setNearestController(Controller nearestController) {
		this.nearestController = nearestController;
	}
	
	public void setLeft(boolean b) { 
		left = b; 
		if(onLadder) {
			onLadder = false;
			falling = true;
		}
		

	}
	public void setRight(boolean b) { 
		right = b; 
		if(onLadder) {
			onLadder = false;
			falling = true;
		}
	}
	
	public void interact() {
		if(this.nearestController != null){
			nearestController.use(this);
		}
	}
	
	public Monolog getMonolog() {
		return this.monolog;
	}
	
	protected void drawDialog(Graphics2D g) {
		if(this.monolog != null)
			this.monolog.getChatBubble().draw(g);
	}
	
	public void setMonolog(Monolog m) {
		this.monolog = m;
	}
	
	public void say(String text) {
		this.monolog = new Monolog(this.tm,text,this);
	}

	public boolean touchedCollectible(Collectible c){
		return inventory.addToInventory(c);
	}

}
