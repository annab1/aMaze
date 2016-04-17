package Presenter;

import java.awt.Point;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import Model.AStar;
import Model.BestFirstSearch;
import Model.ISearch;
import Model.MazeFactory;
import view.GameSettings;
import view.SolveEvent;
import view.SolveListener;
import view.View;

public class Main {
	private static View view;
	private static int [][] maze;
	public static void main(String[] args){
		maze = MazeFactory.createMaze(GameSettings.rows, GameSettings.cols);
		view = new View(maze);
		final ISearch search = new BestFirstSearch();
		view.onSolve(new SolveListener() {
			public void handleEvent(final SolveEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
					final List<Point> solution = search.solve(new Model.Maze(maze), e.currLoc, e.steps);
					Display display = view.getDisplay();
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							view.showSolution(solution);
						}
					});
					}
				}).start();
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