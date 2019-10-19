package Dialogs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.geom.RoundRectangle2D;

import Entity.MapObject;
import Main.GamePanel;
import TileMap.TileMap;
import Utils.DrawUtilities;

public class ChatBubble extends MapObject{
	static final int PADDING = 8;
	static final Color COLOR = new Color(128, 36, 56);
	
	protected Font font;
	private int x;
	private int y;
	private String text;
	
	public ChatBubble(TileMap tm,int x, int y,String text){
		super(tm);
		this.x = x;
		this.y = y;
		this.text = text;
			
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Font/MyGameFont.otf")));
			this.font = new Font("My Font", Font.BOLD, 16);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g){
		boolean left = (x < (GamePanel.WIDTH/2) ? false : true);
		int drawX = x;
		int drawY = y - 80;
		
		setMapPosition();
		g.setColor(COLOR);

		int textWidth = g.getFontMetrics().stringWidth(text);
		int textHeight = (int)Math.ceil(textWidth / (GamePanel.WIDTH/2.5)) * 16 * 2;
		textWidth*=2;
		if(textWidth > (int)(GamePanel.WIDTH/2.5)) textWidth = (int)(GamePanel.WIDTH/2.5);
		if(left) drawX -= 50 + textWidth ; else drawX +=50;
		
		RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(drawX-PADDING, drawY-16 - PADDING, textWidth+PADDING, textHeight + (2*PADDING), 10, 10);
		g.fill(roundedRectangle);
		
		g.setColor(Color.white);
		g.setStroke(new BasicStroke(3));
		g.draw(roundedRectangle);
		g.setFont(this.font);
		DrawUtilities.drawStringMultiLine(g, text, (int)(GamePanel.WIDTH/2.5), drawX, drawY);
	}
}