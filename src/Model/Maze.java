package Model;
import java.awt.Point;

public class Maze {
	public Node start;
	public Node end;
	public Node[][] cells;
	
	public Point getLocation(Node node) {
		for(int i=0; i < cells.length; i++) {
			for (int j=0; j < cells[i].length ; j++) {
				if (cells[i][j] == node) {
					return new Point(i, j);
				}
			}
		}
		
		return null;
	}
	
	public double getDistance(Node node1, Node node2) {
		Point node1Location = this.getLocation(node1);
		Point node2Location = this.getLocation(node2);
		return Math.abs((node1Location.y - node2Location.y)+(node1Location.x - node2Location.x));
	}
	
	public double getDistanceToEnd(Node node1) {
		return this.getDistance(node1, this.end);
	}
}