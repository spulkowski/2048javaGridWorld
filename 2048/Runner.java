/*
2048 Runner
*/

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.*;
import info.gridworld.actor.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Runner {
    public static void main(String[] args) {
        ActorWorld world = new ActorWorld(new BoundedGrid(4,4));
        // Logic Accessor
        final Logic hello = new Logic();
        // Rock Start
        Rock one = new Rock();
        Rock two = new Rock();
        world.add(new Location(0, 0), hello);
        world.add(new Location(1, 1), one);
        world.add(new Location(1, 2), two);
        // World Start
        world.show();

		java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new java.awt.KeyEventDispatcher() { 
			public boolean dispatchKeyEvent(java.awt.event.KeyEvent event) { 
				String key = javax.swing.KeyStroke.getKeyStrokeForEvent(event).toString(); 
				if (key.equals("pressed UP")) {
					hello.move(0);
				}
				
				if (key.equals("pressed RIGHT")){
					hello.move(90);
				} 
				 
				if (key.equals("pressed DOWN")) {
				 	hello.move(180);
				}
				
				if (key.equals("pressed LEFT")) {
				 	hello.move(270);
				}
				
				return true; 
			} 
		});         
    }
    
}

class Logic extends Actor {
	ArrayList<Color> colors = new ArrayList<Color>();

	Logic() {
		colors.add(Color.black);
		colors.add(new Color(241, 196, 15));
		colors.add(new Color(230, 126, 34));
		colors.add(new Color(231, 76, 60));
		Score score = new Score();
	}

	public void move(int direction) {
<<<<<<< HEAD
		// Transveral Still Not Implemented in this Situation
	
		Grid<Actor> gr = getGrid();
=======
		// Start Transfersal In the Beginning and Then Proceed with Smashing

		// HULK SMASH
>>>>>>> 107015e951ebcd0cf57b011aaa6b5fe54386b991
		ArrayList<Location> actors = getGrid().getOccupiedLocations();
		
		 for(Location lok : actors) 
		{
		
			
		if (getGrid().get(lok.getAdjacentLocation(direction)) == null && getGrid().isValid(lok.getAdjacentLocation(direction)))
			{
				moveTo(lok.getAdjacentLocation(direction));
			}
			
		
//			 
//        				if (gr.isValid(next))
//            				
		}
            				
		
		for(Location loc : actors) {
			if (getGrid().get(loc.getAdjacentLocation(direction)) instanceof Rock && getGrid().isValid(loc.getAdjacentLocation(direction))) {
				if(getGrid().get(loc.getAdjacentLocation(direction)).getColor().equals(getGrid().get(loc).getColor())) {
					getGrid().get(loc).removeSelfFromGrid();
					getGrid().get(loc.getAdjacentLocation(direction)).setColor(nextColor(getGrid().get(loc.getAdjacentLocation(direction)).getColor()));
					
		
        				
					
				} 
			}
			//if(getGrid().get(loc.getAdjacentLocation(direction)) == null && getGrid().isValid(loc.getAdjacentLocation(direction))) {
				
			//	getGrid().put(loc.getAdjacentLocation(direction), getGrid().get(loc));
				
			//}
		}
		randomTile();
	}

	public Color nextColor(Color current) {
		// Use This To Go Through the List of Colors. 
		for(int i = 0; i < colors.size() - 1; i++) {
			if (colors.get(i) == current) {
				return colors.get(i + 1);
			}
		}
		return Color.yellow;
	}
<<<<<<< HEAD
	
	public void randomTile()
	{
		boolean status = false;
		Grid<Actor> gr = getGrid();
		Random rand = new Random();
		int x = 0;
		int y = 0;
		while(status == false) 
			x = rand.nextInt(4);
			y = rand.nextInt(4);
			Location loc = new Location(x,y);
			if(gr.get(loc) == null)
			{
				//	
=======

	public ArrayList<Location> emptyTiles() {
		ArrayList<Location> answer = new ArrayList<Location>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if(getGrid().get(new Location(i, j)) == null) {
					answer.add(new Location(i, j));
				}
>>>>>>> 107015e951ebcd0cf57b011aaa6b5fe54386b991
			}
		}
		return answer;
	}

	public void randomTile() {
		ArrayList<Location> locs = emptyTiles();
		if (locs.size() == 0) {
			// Don't Add Tile
		} else {
			int n = (int) (Math.random() * locs.size());
			Rock r = new Rock();
			r.putSelfInGrid(getGrid(), locs.get(n));
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