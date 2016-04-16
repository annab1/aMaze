package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import Model.MazeFactory.DIR;

public class Player {
	private int x;
	private int y;
	public Player() {
		x = 0;
		y = 0;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void move(DIR dir) {
		y = y + dir.getY();
		x = x + dir.getX();
	}
	
	public void draw(GC gc) {
		int margin = GameSettings.margin;
		int tileSize = GameSettings.tileSize;
		int playerSize = GameSettings.playerSize;
		Display display = Display.getCurrent();
		gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		gc.fillOval(margin + 2 + (x * tileSize),  margin + 2 + (y * tileSize), playerSize, playerSize);
	}
	
}
