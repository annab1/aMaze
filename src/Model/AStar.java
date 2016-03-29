package Model;
import java.util.PriorityQueue;

import Model.MazeFactory.DIR;

import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import java.awt.Point;
import java.util.ArrayList;

public class AStar{
	
//        public static void main(String[] args){
//        	Maze maze = new Maze();
//        	Node startNode = new Node(false);
//        	Node endNode = new Node(false);
//        	maze.start = startNode;
//        	maze.end = endNode;
//        	maze.cells = new Node[][] {
//        		{startNode, new Node(false), new Node(false), new Node(false)},
//        		{new Node(false), new Node(true), new Node(false), new Node(false)},
//        		{new Node(false), new Node(true), new Node(false), new Node(false)},
//        		{new Node(false), new Node(true), new Node(false), new Node(false)},
//        		{new Node(false), new Node(true), new Node(false), new Node(true)},
//        		{new Node(false), new Node(true), new Node(false), new Node(false)},
//        		{new Node(false), new Node(true), endNode, new Node(false)},
//        		{new Node(false), new Node(false), new Node(false), new Node(false)}
//        		};
//           List<Node> solution = AStar.solve(maze);
//           for(Node[] arr : maze.cells) {
//        	   for (Node n : arr) {
//        		   if (n.isBlocked) {
//        			   System.out.print('X');
//        		   } else if (solution.contains(n)) {
//        			   System.out.print('!');
//        		   } else {
//        			   System.out.print('O');
//        		   }
//        	   }
//        	   System.out.println();
//           }
//        }
           
        public static List<Node> solve(final int[][] maze) {

            // create a heap / priority queue for tracking the currently open vertices that we are searching
            PriorityQueue<Node> pQueue = new PriorityQueue<Node>(10, new Comparator<Node>() {
                public int compare(Node node1, Node node2) {
                	return (int) (node1.priority - node2.priority);
                }
            });
            
            int rows = maze.length;
            int cols = maze[0].length;
            
            Node startNode = new Node(0, 0, maze[0][0]);
            Map<Node, Double> costSoFar = new HashMap<Node, Double>();
            costSoFar.put(startNode, 0.0);
            
            Map<Node, Double> cameFrom = new HashMap<Node, Double>();
            cameFrom.put(startNode, null);
            
            // init the queue with the starting location, mark this location as visited
            Node current = startNode;

            current.visited = true;

            // the distance to the target is the key for the priority queue since we want to examine
            // candidates according to how close they are to the target
            pQueue.add(current);

            // seek a path to the target until the queue is empty
            while (pQueue.size() != 0) {

                //=dequeue the visited node with the shortest distance to the target.
                current = pQueue.poll();

                // if we have reached the target we are done
                if (current.x == rows -1 && current.y == cols -1) {
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
                Node sibling = null;
                for (int i = 1; i <= 8; i *= 2) {
                	DIR dir = MazeFactory.DIR.getByBit(i);
                    int tr = current.x + dir.getX();
                    int tc = current.y + dir.getY();
                 
                    if (tr >= 0 && tr < rows && tc >= 0 && tc < cols && MazeFactory.canMove(current.x,  current.y, dir , maze)) {
                    	sibling = new Node(tr, tc, maze[tr][tc]);
                    	double newCost = costSoFar.get(current) + 1;
                    	
                    	if((!costSoFar.containsKey(sibling) || newCost < costSoFar.get(sibling))) {
                    		costSoFar.put(sibling, newCost);
                    		sibling.priority = newCost + MazeFactory.getDistanceToEnd(tr, tc, rows -1, cols -1);
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