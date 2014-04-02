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
    }
}