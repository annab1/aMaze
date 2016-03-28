package view;
import org.eclipse.swt.events.PaintEvent;

import java.awt.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridLayout;
import Model.MazeFactory;

import org.eclipse.swt.widgets.*;


public class View {
	
    public static void main(String[] args){
    int x = 20;
	int y = 20;
	final int[][] maze = MazeFactory.createMaze(x, y);
	final Display display = new Display ();

	final Shell shell = new Shell(display);
	shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	GridLayout layout = new GridLayout();    
    layout.marginWidth = 500;
    layout.marginHeight =500;
	layout.numColumns = 3;
	final int margin = 50;
	final int tileSize = 30;
	final Point figureLoc = new Point(0,0);
	shell.setLayout(layout);	

	display.addFilter(SWT.KeyDown, new Listener(){

		@Override
		public void handleEvent(Event event) {
			switch(event.keyCode) { 
			case SWT.ARROW_DOWN: {
				boolean canMove = View.canMove(figureLoc, 1, maze);
				if (canMove) {
					figureLoc.y = figureLoc.y + 1;
				}
				shell.redraw();
				break; 
				}
			case SWT.ARROW_LEFT: {	
				boolean canMove = View.canMove(figureLoc, 3, maze);
				if (canMove) {
					figureLoc.x = figureLoc.x - 1;
				}
				shell.redraw(); 
				break; 
				}
			case SWT.ARROW_RIGHT: {	
				boolean canMove = View.canMove(figureLoc, 2, maze);
				if (canMove) {
					figureLoc.x = figureLoc.x + 1;
				}
				shell.redraw(); 
				break; 
				}
			case SWT.ARROW_UP: {	
				boolean canMove = View.canMove(figureLoc, 4, maze);
				if (canMove) {
					figureLoc.y = figureLoc.y - 1;
				}
				shell.redraw(); 
				break; 
				}
			//other cases here ... 
			}
			
		} 
	
	});
	shell.addPaintListener(new PaintListener(){ 
        public void paintControl(PaintEvent e){
    		View.drawMaze(maze, e.gc, margin);
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

	public static void drawMaze(int[][] maze, GC gc, int margin) {
		int cols = maze.length;
		int rows = maze[0].length;
		int multiplier = 30;
		for (int y= 0; y < rows; y++) {
			int newY = (y * multiplier) + margin;
			// draw the north edge
			for (int x= 0; x < cols; x++) {
				int newX = (x * multiplier) + margin;
				int a = maze[x][y];
				boolean isNorth = (a & 1) == 0;
				if (isNorth) {
					gc.drawLine(newX,newY ,newX + multiplier,newY);
				}
			}
			// draw the west edge
			for (int x = 0; x < cols; x++) {
				int newX = (x * multiplier) + margin;
				boolean isWest = (maze[x][y] & 8) == 0;
				if (isWest) {
					gc.drawLine(newX,newY,newX, newY + multiplier);
				}
			}
			int lastX = (cols * multiplier) + margin;
			gc.drawLine(lastX,newY,lastX, newY + multiplier);
		}
		
		int lastY = (rows * multiplier) + margin;
		int lastX = (cols * multiplier) + margin;
		gc.drawLine(margin,lastY,lastX, lastY);
	}
		
	public static void drawFigure(int x, int y, int size, GC gc) {

		Display display = Display.getCurrent();
		gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		gc.fillOval(x, y, size, size);
	}
	
	public static boolean canMove(Point currLoc,int direction, int[][] maze) {
		switch(direction) {
		case 1: {
			//down
			int a = maze[(int) currLoc.getX()][(int) currLoc.getY()];
			return (a & 2) != 0;
		}
		case 2: {
			//right
			int a = maze[(int) currLoc.getX()][(int) currLoc.getY()];
			return (a & 4) != 0;
		}
		case 3: {
			//left
			int a = maze[(int) currLoc.getX()][(int) currLoc.getY()];
			return (a & 8) != 0;
		}
		case 4: {
			//up
			int a = maze[(int) currLoc.getX()][(int) currLoc.getY()];
			return (a & 1) != 0;
		}
		}
		return false;
	}
}
