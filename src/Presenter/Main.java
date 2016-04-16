package Presenter;

import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import Model.AStar;
import Model.MazeFactory;
import Model.Node;
import view.GameSettings;
import view.View;

public class Main {
	private static View view;
	private static int [][] maze;
	public static void main(String[] args){
		maze = MazeFactory.createMaze(GameSettings.rows, GameSettings.cols);
		view = new View(maze);
		view.onSolve(new Listener() {
			public void handleEvent(Event e) {
				List<Node> solution = AStar.solve(maze);
				view.showSolution(solution);
			}
		});
		
		view.onApply(new Listener() {
			public void handleEvent(Event e) {
				maze = MazeFactory.createMaze(GameSettings.rows, GameSettings.cols);
				view.init(maze);
			}
		});
		view.draw();
	
	}
}
