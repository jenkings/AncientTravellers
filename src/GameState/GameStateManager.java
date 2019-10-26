package GameState;

import java.util.ArrayList;

import GameState.Levels.Level1State;
import GameState.Levels.Level2State;

public class GameStateManager 
{
	private ArrayList <GameState> gameStates;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int HELPSTATE = 1;
	public static final int LEVEL1STATE = 2;
	public static final int LEVEL2STATE = 3;
	

	public GameStateManager()
	{
		gameStates = new ArrayList<GameState>();
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new HelpState(this));
		gameStates.add(new Level1State(this));
		gameStates.add(new Level2State(this));
	}
	
	public void setState(int state)
	{
		currentState = state;
		gameStates.get(currentState).init();
	}
	
	public void update()
	{
		gameStates.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g)
	{
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k)
	{
		gameStates.get(currentState).keyPressed(k);
	}
	
	public int getCurrentState() {
		return this.currentState;
	}
	
	public void keyReleased(int k)
	{
		gameStates.get(currentState).keyReleased(k);
	}
}
