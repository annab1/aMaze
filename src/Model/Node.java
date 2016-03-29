package Model;

public class Node{
        public boolean visited;
        public Node parent;
        public double priority;
        public int x;
        public int y;
        public int val;
        
        public Node(int x, int y, int val) { 
        	this.x = x;
        	this.y = y;
        	this.val = val;
        }
       
}
