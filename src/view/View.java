package view;
import org.eclipse.swt.events.PaintEvent;

import java.util.List;
import java.awt.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridLayout;

import Model.AStar;
import Model.MazeFactory;
import Model.MazeFactory.DIR;
import Model.Node;

import org.eclipse.swt.widgets.*;


public class View {
	
    public static void main(String[] args){
    int x = 6;
	int y = 6;
	final int tileSize = 30;
	final int[][] maze = MazeFactory.createMaze(x, y);
	final List<Node> solution = AStar.solve(maze);
	final Display display = new Display ();

	final Shell shell = new Shell(display);
	shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	GridLayout layout = new GridLayout();    
    layout.marginWidth = 500;
    layout.marginHeight =500;
	layout.numColumns = 3;
	final int margin = 50;
	final Point figureLoc = new Point(0,0);
	shell.setLayout(layout);	

	display.addFilter(SWT.KeyDown, new Listener(){

		@Override
		public void handleEvent(Event event) {
			switch(event.keyCode) { 
			case SWT.ARROW_DOWN: {
				View.tryMove(DIR.S, figureLoc, maze);
				break; 
				}
			case SWT.ARROW_LEFT: {	
				View.tryMove(DIR.W, figureLoc, maze);
				break; 
				}
			case SWT.ARROW_RIGHT: {	
				View.tryMove(DIR.E, figureLoc, maze);
				break; 
				}
			case SWT.ARROW_UP: {	
				View.tryMove(DIR.N, figureLoc, maze);
				break; 
			 } 
			}
			shell.redraw();
			
		} 
	
	});
	shell.addPaintListener(new PaintListener(){ 
        public void paintControl(PaintEvent e){
    		View.drawSolution(solution, e.gc, tileSize, margin);
    		View.drawMaze(maze, e.gc, margin, tileSize);
    		View.drawFigure(margin + 2 + (figureLoc.x * tileSize), margin + 2 + (figureLoc.y * tileSize), 26, e.gc);
        }
    });
	
	shell.pack();
    shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
    }
    
    private static void drawSolution(List<Node> solution, GC gc, int tileSize, int margin) {
    	for (Node node : solution) {
    		gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
    		gc.fillRectangle((node.x * tileSize) + margin +1, (node.y * tileSize + margin) + 1, tileSize -1, tileSize -1);
    	}
    }
    
    private static void tryMove(DIR dir, Point currentLoc, int[][] maze) {
    	boolean canMove = MazeFactory.canMove((int) currentLoc.getX(), (int) currentLoc.getY(), dir, maze);
		if (canMove) {
			currentLoc.y = currentLoc.y + dir.getY();
			currentLoc.x = currentLoc.x + dir.getX();
		}
    }

	public static void drawMaze(int[][] maze, GC gc, int margin, int tileSize) {
		int cols = maze.length;
		int rows = maze[0].length;
		for (int y= 0; y < rows; y++) {
			int newY = (y * tileSize) + margin;
			// draw the north edge
			for (int x= 0; x < cols; x++) {
				int newX = (x * tileSize) + margin;
				int a = maze[x][y];
				boolean isNorth = (a & 1) == 0;
				if (isNorth) {
					gc.drawLine(newX,newY ,newX + tileSize,newY);
				}
			}
			// draw the west edge
			for (int x = 0; x < cols; x++) {
				int newX = (x * tileSize) + margin;
				boolean isWest = (maze[x][y] & 8) == 0;
				if (isWest) {
					gc.drawLine(newX,newY,newX, newY + tileSize);
				}
			}
			int lastX = (cols * tileSize) + margin;
			gc.drawLine(lastX, newY, lastX, newY + tileSize);
		}
		
		int lastY = (rows * tileSize) + margin;
		int lastX = (cols * tileSize) + margin;
		gc.drawLine(margin,lastY,lastX, lastY);
	}
		
	public static void drawFigure(int x, int y, int size, GC gc) {
		Display display = Display.getCurrent();
		gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		gc.fillOval(x, y, size, size);
	}
	
	
}
