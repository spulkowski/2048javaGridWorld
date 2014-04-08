/*
2048 Runner
*/

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.*;
import info.gridworld.actor.Actor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Runner {
    public static void main(String[] args) {
        final ActorWorld world = new ActorWorld(new BoundedGrid(4,4));
        world.setMessage("Score: 0");
        // Logic Accessor
        final Logic hello = new Logic(world.getGrid());
        // Lego Start
        Lego one = new Lego();
        Lego two = new Lego();
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
		colors.add(new Color(231,47,39)); // 4
		colors.add(new Color(238,113,25)); // 8
		colors.add(new Color(255,200,8)); // 16
		colors.add(new Color(170,198,27)); // 32
		colors.add(new Color(19,166,50)); // 64
		colors.add(new Color(4,148,87)); // 128
		colors.add(new Color(1,134,141)); // 256
		colors.add(new Color(3,86,155)); // 512
		colors.add(new Color(204,63,92)); // 1024
		colors.add(Color.gray); // 2048
		score = new Score();
		grd = grid;
	}

	public void move(int direction) {
		// Transversal Works, I Guess? and We Need a Won Clause
		ArrayList<Location> actors = grd.getOccupiedLocations();
		ArrayList<Location> missed = new ArrayList<Location>();
		// HULK SMASH	
		for(Location loc : actors) {
			// Transversal
			boolean hi = false;
			Location moving = moveAway(loc, direction);
			if (moving == loc) {
				missed.add(loc);
			}
			if (grd.get(loc) != null && grd.isValid(moving)) {
				grd.get(loc).moveTo(moving);
				hi = true;
			}
			if (hi == true) {
				if (grd.isValid(moving.getAdjacentLocation(direction))) {
					if (grd.get(moving.getAdjacentLocation(direction)) != null) {
						if(grd.get(moving.getAdjacentLocation(direction)).getColor().equals(grd.get(moving).getColor())) {
							grd.get(moving).removeSelfFromGrid();
							grd.get(moving.getAdjacentLocation(direction)).setColor(nextColor(grd.get(moving.getAdjacentLocation(direction)).getColor()));
						}
					}
				}
			} else {
				if (grd.isValid(loc.getAdjacentLocation(direction))) {
					if (grd.get(loc.getAdjacentLocation(direction)) != null) {
						if(grd.get(loc.getAdjacentLocation(direction)).getColor().equals(grd.get(loc).getColor())) {
							grd.get(loc).removeSelfFromGrid();
							grd.get(loc.getAdjacentLocation(direction)).setColor(nextColor(grd.get(loc.getAdjacentLocation(direction)).getColor()));
						}
					}
				}
			}
		}
		/*
		for (Location loc : missed) {
			Location moving = moveAway(loc, direction);
			if (grd.get(loc) != null && grd.isValid(moving)) {
				grd.get(loc).moveTo(moving);
			}
		}
		*/
		randomTile();
		actors = grd.getOccupiedLocations();
		for (int i = actors.size()-1; i>=0; i--) {
			Location moving = moveAway(actors.get(i), direction);
			if (grd.get(actors.get(i)) != null && grd.isValid(moving)) {
				grd.get(actors.get(i)).moveTo(moving);
			}
		}
		
		if (gameWon()) {
			javax.swing.JOptionPane.showMessageDialog(null, "Score: " + score.getScore(), "You Won!", 0);
			System.exit(0);
		}
		if (isGameOver()) {
			javax.swing.JOptionPane.showMessageDialog(null, "Score: " + score.getScore(), "Game Over!", 0);
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
			Lego r = new Lego();
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