package Model;

class Node{
        public boolean isBlocked;
        public boolean visited;
        public Node parent;
        public double priority;
        
        public Node(boolean isBlocked) {
          this.isBlocked = isBlocked; 
        }
       
}
