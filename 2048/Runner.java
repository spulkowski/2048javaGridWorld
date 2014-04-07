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
        final ActorWorld world = new ActorWorld(new BoundedGrid(4,4));
        world.setMessage("Score: 0");
        // Logic Accessor
        final Logic hello = new Logic(world.getGrid());
        // Rock Start
        Rock one = new Rock();
        Rock two = new Rock();
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
				world.setMessage("Score: " + hello.getScore());
				return true; 
			} 
		});         
    }
    
}

class Logic extends Actor {
	ArrayList<Color> colors = new ArrayList<Color>();
	Grid<Actor> grd;
	Score score;

	Logic(Grid<Actor> grid) {
		colors.add(Color.black); // 2
		colors.add(new Color(241, 196, 15)); // 4
		colors.add(new Color(230, 126, 34)); // 8
		colors.add(new Color(231, 76, 60)); // 16
		colors.add(new Color(26, 188, 156)); // 32
		colors.add(new Color(46, 204, 113)); // 64
		colors.add(new Color(155, 89, 182)); // 128
		colors.add(new Color(52, 73, 94)); // 256
		colors.add(new Color(127, 140, 141)); // 512
		colors.add(new Color(219, 252, 0)); // 1024
		colors.add(Color.gray); // 2048
		score = new Score();
		grd = grid;
	}

	public void move(int direction) {
		// Transversal Works, I Guess? and We Need a Won Clause
		ArrayList<Location> actors = grd.getOccupiedLocations();
		// HULK SMASH	
		for(Location loc : actors) {
			if (grd.isValid(loc.getAdjacentLocation(direction))) {
				if (grd.get(loc.getAdjacentLocation(direction)) != null) {
					if(grd.get(loc.getAdjacentLocation(direction)).getColor().equals(grd.get(loc).getColor())) {
						grd.get(loc).removeSelfFromGrid();
						grd.get(loc.getAdjacentLocation(direction)).setColor(nextColor(grd.get(loc.getAdjacentLocation(direction)).getColor()));
					}
				}
			}
			// Transversal
			Location moving = moveAway(loc, direction);
			if (grd.get(loc) != null && grd.isValid(moving)) {
				grd.get(loc).moveTo(moving);
			}
		}
		randomTile();
		if (gameWon()) {
			javax.swing.JOptionPane.showMessageDialog(null, "Score: " + score.getScore(), "YOU WON!", 0);
			System.exit(0);
		}
	}

	private boolean gameWon() {
		ArrayList<Location> actors = grd.getOccupiedLocations();
		for (Location loc : actors) {
			if (grd.get(loc).getColor() == Color.gray) {
				return true;
			}
		}
		return false;
	}

	private Location moveAway(Location loc, int direction) {
		Location answer = loc;
		for (int i = 0; i < 4; i++) {
			if (grd.isValid(answer.getAdjacentLocation(direction)) && grd.get(answer.getAdjacentLocation(direction)) == null) {
				answer = answer.getAdjacentLocation(direction);
			}
		}
		return answer;
	}

	private Color nextColor(Color current) {
		// Use This To Go Through the List of Colors. 
		for(int i = 0; i < colors.size() - 1; i++) {
			if (colors.get(i) == current) {
				int scoreToAdd = (int) (Math.pow(2, (i + 1)) * 2);
				score.addScore(scoreToAdd);
				return colors.get(i + 1);
			}
		}
		return Color.yellow;
	}


	private ArrayList<Location> emptyTiles() {
		ArrayList<Location> answer = new ArrayList<Location>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if(grd.get(new Location(i, j)) == null) {
					answer.add(new Location(i, j));
				}

			}
		}
		return answer;
	}

	private void randomTile() {
		ArrayList<Location> locs = emptyTiles();
		if (locs.size() == 0) {
			// Don't Add Tile
		} else {
			int n = (int) (Math.random() * locs.size());
			Rock r = new Rock();
			r.putSelfInGrid(grd, locs.get(n));
		}
	}
	
	public int getScore() {
		return score.getScore();
	}
	
	private boolean isGameOver()
	{
		if ((canMove(0) == false && canMove(90) == false && canMove(180) ==false && canMove(270)==false) && emptyTiles().size()== 0) 
		{
			return true;
		}
		return false;	
	}
	
	private boolean canMove(int d)
	{
		ArrayList<Location> actors = grd.getOccupiedLocations();
		boolean status = false;
		for(Location loc : actors)
		{
			if (grd.isValid(loc.getAdjacentLocation(d))) {
				if (grd.get(loc.getAdjacentLocation(d)) != null) {
					if(grd.get(loc.getAdjacentLocation(d)).getColor().equals(grd.get(loc).getColor()))
					status = true;
				}
			}
		}
		return status;
		 
	}

}

class Score {
	private int currentScore;

	Score() {
		currentScore = 0;
	}

	public void addScore(int add) {
		currentScore += add;
	}

	public int getScore() {
		return currentScore;
	}
	
	
}