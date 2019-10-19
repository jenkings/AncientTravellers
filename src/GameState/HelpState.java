package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import Main.Game;
import Main.GamePanel;
import TileMap.Background;

public class HelpState extends GameState
{
	private Background logo;
	
	private Font font;
	
	public HelpState(GameStateManager gsm)
	{
		this.gsm = gsm;

		try{
			
			logo = new Background("/Backgrounds/logo.png", 1);
			logo.setPosition(30, 20);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Font/joystix.ttf")));
			font = new Font("Joystix Monospace", Font.PLAIN, 20);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public void init() {}
	public void update(){}
	public void draw(Graphics2D g ) {
		g.clearRect(0, 0, GamePanel.WIDTH,GamePanel.HEIGHT);
		logo.draw(g);
		
		//draw menu options
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(GamePanel.LOCALISATION.getWord("helpmenu_version") + " " + Game.VERSION, 50, 180);
		g.drawString(GamePanel.LOCALISATION.getWord("helpmenu_author") + " Jan Škoda (jan-skoda.cz)" , 50, 210);
		
		
		g.drawString(GamePanel.LOCALISATION.getWord("helpmenu_controls") , 50, 260);
		g.drawString(GamePanel.LOCALISATION.getWord("helpmenu_ctrl_arrows") , 50, 290);
		g.drawString(GamePanel.LOCALISATION.getWord("helpmenu_ctrl_spec1") , 50, 320);
		g.drawString(GamePanel.LOCALISATION.getWord("helpmenu_ctrl_spec2") , 50, 350);
		g.drawString(GamePanel.LOCALISATION.getWord("helpmenu_ctrl_change") , 50, 380);
		g.drawString(GamePanel.LOCALISATION.getWord("helpmenu_ctrl_use") , 50, 410);

	}
	
	private void select(){
			gsm.setState(GameStateManager.MENUSTATE);
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER || k == KeyEvent.VK_ESCAPE){
			select();
		}
	}
	public void keyReleased(int k) {}
}
