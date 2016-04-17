package model;

import java.awt.Point;
import java.util.List;

public interface ISearch {
	public List<Point> solve(ISearchDomain searchDomain, Point start, int steps); 
}
