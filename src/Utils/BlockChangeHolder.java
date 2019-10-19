package Utils;

import java.awt.Point;

public class BlockChangeHolder {

	private Point coords;
	private int from;
	private int to;
	
	public BlockChangeHolder(Point coords, int from, int to) {
		this.coords = coords;
		this.from = from;
		this.to = to;
	}
	
	public Point getCoords(){return coords;}
	public int getFrom(){return from;}
	public int getTo(){return to;}
}
