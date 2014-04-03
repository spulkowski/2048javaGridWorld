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
		// BIGGEST ISSUE: ALL THE NULL POINTER ERRORS EVAR (Apparently Colors Be Stupid Dawg)
		// Transveral Still Not Implemented in this Situation
		ArrayList<Location> actors = getGrid().getOccupiedLocations();
		for(Location loc : actors) {
			if (getGrid().get(loc.getAdjacentLocation(direction)) instanceof Rock && getGrid().isValid(loc.getAdjacentLocation(direction))) {
				// The Below Line Doesn't Work and Is Driving Me Mad But Is Basically Supposed to Just Check if the Two Rocks are the Same Color
				if(getGrid().get(loc.getAdjacentLocation(direction)).getColor().equals(getGrid().get(loc).getColor())) {
					// Update Next Ones Color and Delete the Original (Tested and Doesn't Work...YAY .___.)
					getGrid().got(loc).removeSelfFromGrid();
					//getGrid().get(loc.getAdjacentLocation(direction)).setColor(nextColor(getGrid().get(loc.getAdjacentLocation(direction)).getColor()));
				}
				//System.out.println(getGrid().get(loc).getColor());
				//System.out.println(getGrid().get(loc.getAdjacentLocation(direction)).getColor());
				//System.out.println("=====================");
			}
		}
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
	/*
	public void randomTile()
	{
		boolean status = false;
		Grid<Actor> gr = getGrid();
		Random rand = new Random();
		int x = 0;
		int y = 0;
		Location loc = new Location;
		while(status == false) 
			x = rand.nextInt(4);
			y = rand.nextInt(4);
			loc = (x,y);
			if(gr.get(loc) == null)
			{
				
			}
	}
	*/
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