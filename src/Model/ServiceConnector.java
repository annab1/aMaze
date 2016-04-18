package model;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import Common.Maze;
import Common.SolveData;

public class ServiceConnector {
 public ServiceConnector() { 
 }
 Socket serverSocket;
 ObjectInputStream in;
 ObjectOutputStream out;
 
 
public List<Point> solve(int[][] matrix, Point currLoc, int steps) throws IOException, ClassNotFoundException {
	Maze maze = new Maze(matrix);

	 try {
		 serverSocket = new Socket(ServerSettings.hostname, ServerSettings.port);
	      out = new ObjectOutputStream(serverSocket.getOutputStream());
	      in =  new ObjectInputStream(serverSocket.getInputStream());
	      
		 SolveData solveData = new SolveData(maze, currLoc, steps);
		 out.writeObject(solveData);
		 List<Point> solution;
		while ((solution = (List<Point>) in.readObject()) != null) {
			 System.out.print(solution);
			 return solution;
		 }
		
	 } 
	 finally {
		 if (serverSocket != null && !serverSocket.isClosed()) {
			 serverSocket.close();
			 }
			 if (in != null) {
				 in.close();
		     }
		     if (out != null) {
		         out.close();
		     }
	 }
	 return null;
}
}
