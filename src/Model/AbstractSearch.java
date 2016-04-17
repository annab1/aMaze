package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSearch implements ISearch {
	
	public List<Point> solve(ISearchDomain searchDomain, Point start, int steps) {
		Node endNode = innerSolve(searchDomain, start, steps);
		
		List<Point> path = new ArrayList<Point>();
        while (endNode != null) {
        	path.add(new Point(endNode.x, endNode.y));
        	endNode = endNode.parent != null? endNode.parent : null;
        }
        return path;
	}
	
	protected abstract Node innerSolve(ISearchDomain searchDomain, Point start, int steps);
	
	
}
