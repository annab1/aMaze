package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class BestFirstSearch extends AbstractSearch {
	  public Node innerSolve(ISearchDomain searchDomain, Point start, int steps) {
	       	
            final PriorityQueue<Node> open = new PriorityQueue<Node>(10, new Comparator<Node>() {
                public int compare(Node node1, Node node2) {
                	return (int) (node1.priority - node2.priority);
                }
            });
            
            final List<Node> closed = new ArrayList<Node>();
            
            Node startNode = searchDomain.getInitialNode(start);
            
            Node current = startNode;
            open.add(current);
            int stepsCounter = -1;
            while (open.size() != 0) {

                current = open.poll();
                closed.add(current);
                stepsCounter++;
                
                boolean reachedStepsAmount = steps > 0 && stepsCounter == steps;
                if (reachedStepsAmount || searchDomain.isTarget(current)) {
                	return current;
                }

                final Node currentCopy = current;
                searchDomain.foreachSuccessor(current, new SuccessorLogic() {

					@Override
					public void checkSuccessor(Node successor, double distanceToEnd) {
						if (!open.contains(successor) && !closed.contains(successor)) {
							successor.priority = distanceToEnd;
							successor.parent = currentCopy;
		                    open.add(successor);
	                    }
					}
                });
                
            }
            
            return null;
	  }
}
