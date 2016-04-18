package model;

import java.awt.Point;
import java.util.List;

import Common.MazeFactory;

public class MazeLogics {
	public static int[][] createMaze(int rows, int cols) {
		return MazeFactory.createMaze(rows, cols);
	}
	
	public static List<Point> solve(int[][] maze, Point start, int steps) {
		final ServiceConnector service= new ServiceConnector();
		try {
			return service.solve(maze, start, steps);
		} catch (Exception e) {
			return null;
		}
	}
}
