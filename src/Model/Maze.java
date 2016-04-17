package Model;

import java.awt.Point;

import Model.MazeFactory.DIR;

public class Maze implements ISearchDomain {
	private int rows;
	private int cols;
	private Node[][] maze;
	
	public Maze(int [][] matrix) {
		this.rows = matrix.length;
		this.cols = matrix[0].length;
		this.maze = convertToNodes(matrix);
	}
	
	private static Node [][] convertToNodes(int[][] matrix) {
    	Node [][] maze = new Node [matrix.length][matrix[0].length];
    	for (int i=0; i< matrix.length ; i++) {
    		for (int j=0 ; j < matrix[i].length; j++) {
    			maze[i][j] = new Node(i, j, matrix[i][j]);
    		}
    	}
    	
    	return maze;
	}
	
	private double getDistanceToEnd(int x,int y) {
		return Math.abs((y - cols - 1)+ (x - rows - 1));
	}
	
	private boolean canMove(Node node, DIR dir) {
		return dir.canMoveTo(node.val);
	}

	@Override
	public Node getInitialNode(Point initialLocation) {
		return maze[initialLocation.x][initialLocation.y];
	}

	@Override
	public boolean isTarget(Node node) {
		return (node.x == rows -1 && node.y == cols -1);
	}

	@Override
	public void foreachSuccessor(Node current, SuccessorLogic successorLogic) {
		for (int i = 1; i <= 8; i *= 2) {
        	DIR dir = MazeFactory.DIR.getByBit(i);
            int tr = current.x + dir.getX();
            int tc = current.y + dir.getY();
         
            if (tr >= 0 && tr < rows && tc >= 0 && tc < cols && canMove(current, dir)) {
            	Node sibling = maze[tr][tc];
            	successorLogic.checkSuccessor(sibling, getDistanceToEnd(tr, tc));
            }
        }
		
	}
}
