package mazes.generators.maze;

import datastructures.concrete.Graph;
import datastructures.interfaces.ISet;
import mazes.entities.Maze;
import mazes.entities.Room;
import mazes.entities.Wall;

import java.util.Random;

public class KruskalMazeCarver implements MazeCarver {

    @Override
    public ISet<Wall> returnWallsToRemove(Maze maze) {
        ISet<Room> rooms = maze.getRooms();
        ISet<Wall> walls = maze.getWalls();
        Random rand = new Random();
        for (Wall wall : walls) {
            wall.setDistance(rand.nextDouble());
        }
        Graph<Room, Wall> graph = new Graph<>(rooms, walls);
        ISet<Wall> mst = graph.findMinimumSpanningTree();
        for (Wall wall : walls) {
            wall.resetDistanceToOriginal();
        }
        return mst;
    }
}
