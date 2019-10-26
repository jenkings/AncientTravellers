package Entity;

import TileMap.TileMap;

public class Solid extends MapObject{
	protected boolean active = true;
	
	public Solid(TileMap tm, int x, int y){
		super(tm);
		super.setPosition(x, y);
	}
	
	public void setActive(boolean active){this.active = active;}
	public void update(){}
	public void destroy(){}
	
}
