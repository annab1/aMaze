package presenter;

import java.awt.Point;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import Common.MazeFactory.DIR;
import model.MazeLogics;
import model.ServiceConnector;
import view.GameSettings;
import view.PlayerMoveEvent;
import view.PlayerMoveListener;
import view.SolveEvent;
import view.SolveListener;
import view.View;

public class Main {
	private static View view;
	private static int [][] maze;
	public static void main(String[] args){
		
		maze = MazeLogics.createMaze(GameSettings.rows, GameSettings.cols);
		view = new View(maze);
		
		view.onSolve(new SolveListener() {
			public void handleEvent(final SolveEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
					final List<Point> solution = MazeLogics.solve(maze, e.currLoc, e.steps);
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
				maze = MazeLogics.createMaze(GameSettings.rows, GameSettings.cols);
				view.init(maze);
			}
		});
		
		view.onPlayerMove(new PlayerMoveListener() {
			public void OnMoveDown(PlayerMoveEvent e) {
				if (DIR.S.canMoveTo(e.currentTile)) {
					e.player.moveDown();
				}
			}
			public void OnMoveUp(PlayerMoveEvent e) {
				if (DIR.N.canMoveTo(e.currentTile)) {
					e.player.moveUp();
				}
			}
			public void OnMoveLeft(PlayerMoveEvent e) {
				if (DIR.W.canMoveTo(e.currentTile)) {
					e.player.moveLeft();
				}
			}
			public void OnMoveRight(PlayerMoveEvent e) {
				if (DIR.E.canMoveTo(e.currentTile)) {
					e.player.moveRight();
				}
			}
		}); 
		view.draw();
	
	}
}