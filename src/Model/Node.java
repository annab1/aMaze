package model;

public class Node{
        public Node parent;
        public double priority;
        public int val;
        public int x;
        public int y;
        
        public Node(int x, int y, int val) { 
        	this.x = x;
        	this.y = y;
        	this.val = val;
        }
       
}
