package Model;
import java.util.PriorityQueue;

import Model.MazeFactory.DIR;

import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;

public class AStar{
           
        public static List<Node> solve(final int[][] origMaze) {
        	// convert to node array
        	Node [][] maze = new Node [origMaze.length][origMaze[0].length];
        	for (int i=0; i< origMaze.length ; i++) {
        		for (int j=0 ; j < origMaze[i].length; j++) {
        			maze[i][j] = new Node(i, j, origMaze[i][j]);
        		}
        	}
        	
            PriorityQueue<Node> pQueue = new PriorityQueue<Node>(10, new Comparator<Node>() {
                public int compare(Node node1, Node node2) {
                	return (int) (node1.priority - node2.priority);
                }
            });
            
            int rows = maze.length;
            int cols = maze[0].length;
            
            Node startNode = maze[0][0];
            Map<Node, Double> costSoFar = new HashMap<Node, Double>();
            costSoFar.put(startNode, 0.0);
            
            Node current = startNode;
            pQueue.add(current);
            
            while (pQueue.size() != 0) {

                current = pQueue.poll();

                // if we have reached the target we are done
                if (current.x == rows -1 && current.y == cols -1) {
                	List<Node> path = new ArrayList<Node>();
                    while (current != null) {
                    	path.add(current);
                    	current = current.parent != null? current.parent : null;
                    }
                    return path;
                }

                // we are not at the target so add all unvisited nodes that are adjacent to the current node to the priority queue

                // we only consider orthagonally connected unblocked, unvisited cells, there are there row / column offsets relative
                // to the current location

                // orthogonal neighbor offsets (row/column pairs)
                Node sibling = null;
                for (int i = 1; i <= 8; i *= 2) {
                	DIR dir = MazeFactory.DIR.getByBit(i);
                    int tr = current.x + dir.getX();
                    int tc = current.y + dir.getY();
                 
                    if (tr >= 0 && tr < rows && tc >= 0 && tc < cols && MazeFactory.canMove(current.x,  current.y, dir , origMaze)) {
                    	sibling = maze[tr][tc];
                    	double newCost = costSoFar.get(current) + 1;
                    	
                    	if((!costSoFar.containsKey(sibling) || newCost < costSoFar.get(sibling))) {
                    		costSoFar.put(sibling, newCost);
                    		sibling.priority = newCost + MazeFactory.getDistanceToEnd(tr, tc, rows -1, cols -1);
	                    	sibling.parent = current;
	                        pQueue.add(sibling);
                    	}
                    }
                }
            }
            
            return null;
        }       
}