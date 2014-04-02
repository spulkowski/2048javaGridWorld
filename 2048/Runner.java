/*
2048 Runner
*/

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.*;
import info.gridworld.actor.*;
import java.awt.Color;
import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        ActorWorld world = new ActorWorld(new BoundedGrid(4,4));
        world.show();
        
	 java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager() 
	 .addKeyEventDispatcher(new java.awt.KeyEventDispatcher() { 
	 public boolean dispatchKeyEvent(java.awt.event.KeyEvent event) { 
	 String key = javax.swing.KeyStroke.getKeyStrokeForEvent(event).toString(); 
	 if (key.equals("pressed UP")) 
	 {
	 	//2048.move(0);
	 }
	
	 if (key.equals("pressed RIGHT"))
	 {
	 	//2048.move(90);
	 } 
	 
	 if (key.equals("pressed DOWN")) 
	 {
	 	//2048.move(180);
	 }
	
	 if (key.equals("pressed LEFT")) 
	 {
	 	//2048.move(270);
	 }
	
	 return true; 
	 } 
	 	
	 }); 
        
        
    }
    
    
}

class Logic extends Actor {
	ArrayList<Color> colors = new ArrayList<Color>();

	Logic() {
		// Create List of Colors Here or Somewhere Else if No Constructor is Needed
	}

	public void move(int direction) {
		// BIGGEST ISSUE: Figure Out How to Take the Method with Locs.getAdjacentLocaiton(direction).getColor() to work as they are not actors but locations
		// Transveral Still Not Implemented in this Situation
		ArrayList<Location> actors = getGrid().getOccupiedLocations();
		for(Location loc : actors) {
			if (loc.getAdjacentLocation(direction) != null && getGrid().isValid(loc.getAdjacentLocation(direction))) {
				if(loc.getAdjacentLocation(direction).getColor() == loc.getColor()) {
					// Update Next Ones Color and Delete the Original
					getGrid().remove(loc);
					loc.getAdjacentLocation(direction).nextColor(loc.getAdjacentLocation(direction).getColor());
				}
			}
		}
	}

	public Color nextColor(Color current) {
		// Use This To Go Through the List of Colors. 
		for(int i = 0; i < colors.length - 1; i++) {
			if (colors.get(i) == current) {
				return colors.get(i + 1);
			}
		}
	}
}

class Score {
	private int currentScore;

	public void addScore(int add) {
		currentScore += add;
		//world.setMessage(currentScore + "");
	}

	public int getScore() {
		return currentScore;
	}
}