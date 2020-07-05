package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import Main.GamePanel;
import Sound.Sound;
import TileMap.Background;

public class MenuState extends GameState
{
	private Background logo;
	private int currentChoice = 0;
	private int optionsLength = 3;
	
	private Font font;
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		
		try{
			
			logo = new Background("/Backgrounds/logo.png", 1);
			logo.setPosition(30, 20);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Font/joystix.ttf")));
			font = new Font("Joystix Monospace", Font.PLAIN, 40);
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void init() {}
	public void update() {
		//bg.update();
	}
	public void draw(Graphics2D g ){
		g.clearRect(0, 0, GamePanel.WIDTH,GamePanel.HEIGHT);
		logo.draw(g);
		
		String[] options = 
			{
				GamePanel.LOCALISATION.getWord("mainmenu_start"), 
				GamePanel.LOCALISATION.getWord("mainmenu_help"), 
				GamePanel.LOCALISATION.getWord("mainmenu_quit") 
			};
		
		
		//draw menu options
		g.setFont(font);
		for(int i = 0; i < optionsLength; i++) {
			if (i == currentChoice){
				g.setColor(new Color(255, 255, 50));
			}else {
				g.setColor(Color.white);
			}
			g.drawString(options[i], 450, 300 + i * 50);
		}
	}
	
	private void select(){
		if(currentChoice == 0){
			// start
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1){
			// help
			gsm.setState(GameStateManager.HELPSTATE);
		}
		if(currentChoice == 2){
			// quit
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		
		if(k == KeyEvent.VK_ENTER){
			Sound sound = new Sound("/Sound/menu-interact.wav");
			sound.play();
			select();
		}
		if(k == KeyEvent.VK_UP){
			Sound sound = new Sound("/Sound/menu.wav");
			sound.play();
			currentChoice --;
			if(currentChoice == -1){
				currentChoice = optionsLength -1;
			}
		}
		if(k == KeyEvent.VK_DOWN){
			Sound sound = new Sound("/Sound/menu.wav");
			sound.play();
			currentChoice ++;
			if(currentChoice == optionsLength){
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}
}
