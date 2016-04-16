package view;

import java.util.List;

import org.eclipse.swt.graphics.GC;

import Model.AStar;
import Model.MazeFactory;
import Model.MazeFactory.DIR;
import Model.Node;

public class Maze {
	private int [][] maze;
	public Maze(int[][] maze) {
		this.maze = maze;
	}
	
	public List<Node> solve() {
		return AStar.solve(this.maze);
	}
	
	public void movePlayer(DIR dir, Player player) {
    	boolean canMove = MazeFactory.canMove(player.getX(), player.getY(), dir, this.maze);
		if (canMove) {
			player.move(dir);
		}
    }
	
	public void draw(GC gc) {
		int cols = this.maze.length;
		int rows = this.maze[0].length;
		int tileSize = GameSettings.tileSize;
		int margin = GameSettings.margin;
		
		for (int y= 0; y < rows; y++) {
			int newY = (y * tileSize) + margin;
			// draw the north edge
			for (int x= 0; x < cols; x++) {
				int newX = (x * tileSize) + margin;
				int tile = this.maze[x][y];
				boolean isNorth = (tile & 1) == 0;
				if (isNorth) {
					gc.drawLine(newX,newY ,newX + tileSize,newY);
				}
			}
			// draw the west edge
			for (int x = 0; x < cols; x++) {
				int newX = (x * tileSize) + margin;
				boolean isWest = (this.maze[x][y] & 8) == 0;
				if (isWest) {
					gc.drawLine(newX,newY,newX, newY + tileSize);
				}
			}
			int lastX = (cols * tileSize) + margin;
			gc.drawLine(lastX, newY, lastX, newY + tileSize);
		}
		
		int lastY = (rows * tileSize) + margin;
		int lastX = (cols * tileSize) + margin;
		gc.drawLine(margin,lastY,lastX, lastY);
	}

}
