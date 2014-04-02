/*
2048 Runner
*/

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.*;
import java.awt.Color;

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
	 	2048.move(0);
	 }
	
	 if (key.equals("pressed RIGHT"))
	 {
	 	2048.move(90);
	 } 
	 
	 if (key.equals("pressed DOWN")) 
	 {
	 	2048.move(180);
	 }
	
	 if (key.equals("pressed LEFT")) 
	 {
	 	2048.move(270);
	 }
	
	 return true; 
	 } 
	 	
	 }); 
        
        
    }
    
    
}

class 2048 extends Actor {
	public void move(int direction) {
		ArrayList<Actor> actors = getActors();
		for(Actor a : actors)
		{
			
			if()
		}
	}
}