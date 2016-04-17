package model;
import java.util.Arrays;
import java.util.Collections;


public final class MazeFactory {
	private MazeFactory() {
    }
 
	public static int [][] createMaze(int rows, int cols) {
		int [][]maze = new int[rows][cols];
		generateMaze(0, 0, maze, rows, cols);
		return maze;
	}
	
	private static void generateMaze(int cx, int cy, int[][] maze,int rows,int cols) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			if (between(nx, rows) && between(ny, cols)
					&& (maze[nx][ny] == 0)) {
				maze[cx][cy] |= dir.bit;
				maze[nx][ny] |= dir.opposite.bit;
				generateMaze(nx, ny, maze, rows, cols);
			}
		}
	}
	
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}
	
 
	public enum DIR {
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;
 
		// use the static initializer to resolve forward references
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}
 
		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
		
		public static DIR getByBit(int bit) {

			return DIR.E.bit == bit? DIR.E :
				(DIR.S.bit == bit)? DIR.S :
					(DIR.N.bit == bit)? DIR.N:
					(DIR.W.bit == bit)? DIR.W : null;
		}
		
		public int getX() {
			return this.dx;
		}
		
		public int getY() {
			return this.dy;
		}
		
		public boolean canMoveTo(int value) {
			return (value & bit) != 0;
		}
		
	};
 
}