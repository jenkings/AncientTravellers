package Dialogs;

import Entity.MapObject;
import TileMap.TileMap;

public class Monolog {
	private String text;
	private MapObject talker;
	//private Color color;
	private TileMap tileMap;
	private ChatBubble chatBubble;
	
	public Monolog(TileMap tm,String text, MapObject talker) {
		this.tileMap = tm;
		this.text = text;
		this.talker = talker;
		this.chatBubble = new ChatBubble(tm,talker.getx() + (int)tileMap.getx(),talker.gety() + (int)tileMap.gety(),text);
	}
	
	public ChatBubble getChatBubble() {
		return this.chatBubble;
	}

	
}
