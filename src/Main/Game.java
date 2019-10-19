package Main;

import javax.swing.JFrame;

public class Game {
	public static final String VERSION = "0.1 beta";
	
	public static void main(String[] args) 
	{
		JFrame window = new JFrame("Ancient Travellers");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);

	}
}
