package Workers;

import TileMap.TileMap;

public class Worker {
	protected TileMap tileMap;

	public Worker (TileMap tm){
		tileMap = tm;
	}
	
	public void doWork() {}
	public void undoWork() {}
}
