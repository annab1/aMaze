package Model;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import java.awt.Point;
import java.util.ArrayList;

public class AStar{
	
        public static void main(String[] args){
        	Maze maze = new Maze();
        	Node startNode = new Node(false);
        	Node endNode = new Node(false);
        	maze.start = startNode;
        	maze.end = endNode;
        	maze.cells = new Node[][] {
        		{startNode, new Node(false), new Node(false), new Node(false)},
        		{new Node(false), new Node(true), new Node(false), new Node(false)},
        		{new Node(false), new Node(true), new Node(false), new Node(false)},
        		{new Node(false), new Node(true), new Node(false), new Node(false)},
        		{new Node(false), new Node(true), new Node(false), new Node(true)},
        		{new Node(false), new Node(true), new Node(false), new Node(false)},
        		{new Node(false), new Node(true), endNode, new Node(false)},
        		{new Node(false), new Node(false), new Node(false), new Node(false)}
        		};
           List<Node> solution = AStar.solve(maze);
           for(Node[] arr : maze.cells) {
        	   for (Node n : arr) {
        		   if (n.isBlocked) {
        			   System.out.print('X');
        		   } else if (solution.contains(n)) {
        			   System.out.print('!');
        		   } else {
        			   System.out.print('O');
        		   }
        	   }
        	   System.out.println();
           }
        }
           
        public static List<Node> solve(final Maze maze) {

            // create a heap / priority queue for tracking the currently open vertices that we are searching
            PriorityQueue<Node> pQueue = new PriorityQueue<Node>(10, new Comparator<Node>() {
                public int compare(Node node1, Node node2) {
                	return (int) (node1.priority - node2.priority);
                }
            });
            
            Map<Node, Double> costSoFar = new HashMap<Node, Double>();
            costSoFar.put(maze.start, 0.0);
            
            Map<Node, Double> cameFrom = new HashMap<Node, Double>();
            cameFrom.put(maze.start, null);
            
            // init the queue with the starting location, mark this location as visited
            Node current = maze.start;

            current.visited = true;

            // the distance to the target is the key for the priority queue since we want to examine
            // candidates according to how close they are to the target
            pQueue.add(current);

            // seek a path to the target until the queue is empty
            while (pQueue.size() != 0) {

                //=dequeue the visited node with the shortest distance to the target.
                current = pQueue.poll();

                // if we have reached the target we are done
                if (current == maze.end) {
                	List<Node> path = new ArrayList<Node>();
                    // exit the search loop
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
                int[] offsets = {1, 0, 0, -1, -1, 0, 0, 1};

                for (int i = 0; i < offsets.length; i += 2) {

                	Point location = maze.getLocation(current);
                    int tr = location.x + offsets[i];
                    int tc = location.y + offsets[i + 1];
                    
                    if (tr >= 0 && tr < maze.cells.length && tc >= 0 && tc < maze.cells[tr].length) {
                    	Node sibling = maze.cells[tr][tc];
                    	double newCost = costSoFar.get(current) + 1;
                    	
                    	if(!sibling.isBlocked && (!costSoFar.containsKey(sibling) || newCost < costSoFar.get(sibling))) {
                    		costSoFar.put(sibling, newCost);
                    		sibling.priority = newCost + maze.getDistanceToEnd(sibling);
	                    	sibling.parent = current;
	                    	sibling.visited = true;
	                        pQueue.add(sibling);
                    	}
                    }
                }
            }
            
            return null;
        }       
}