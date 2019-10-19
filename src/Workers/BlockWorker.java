package Workers;

import java.awt.Point;
import java.util.ArrayList;

import TileMap.TileMap;
import Utils.BlockChangeHolder;

public class BlockWorker extends Worker{
	
	private ArrayList<BlockChangeHolder> changeBlocks;
	

	public BlockWorker(TileMap tm) {
		super(tm);
		changeBlocks = new ArrayList<BlockChangeHolder>();
	}
	
	public void addChangingBlock(Point point,int from,int to) {
		changeBlocks.add(new BlockChangeHolder(point, from, to));
	}
	
	public void doWork() {
		for(int j = 0; j < changeBlocks.size(); j++){
				tileMap.setBlock(changeBlocks.get(j).getCoords(),changeBlocks.get(j).getTo());
			}
	}
	
	public void undoWork() {
		for(int j = 0; j < changeBlocks.size(); j++){
				tileMap.setBlock(changeBlocks.get(j).getCoords(),changeBlocks.get(j).getFrom());
			}
	}
	
	
}
