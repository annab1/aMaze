package view;
import org.eclipse.swt.events.PaintEvent;

import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridLayout;

import Model.MazeFactory;
import Model.MazeFactory.DIR;
import Model.Node;

import org.eclipse.swt.widgets.*;


public class View {
	private static Maze maze;
	private static Player player;
	private static List<Node> solution;
	
    public static void main(String[] args){
	
	final Display display = new Display ();
	final Shell shell = new Shell(display);
	shell.setSize(900, 600);
	shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	View.drawSettings(shell);
	GridLayout layout = new GridLayout(8, false);    
    //layout.marginWidth = 600;
    //layout.marginHeight =200;
	shell.setLayout(layout);	
	View.createMaze(shell);
	
	Display.getCurrent().addFilter(SWT.KeyDown, new Listener(){

		@Override
		public void handleEvent(Event event) {
			switch(event.keyCode) { 
			case SWT.ARROW_DOWN: {
				maze.movePlayer(DIR.S, player);
				break; 
				}
			case SWT.ARROW_LEFT: {	
				maze.movePlayer(DIR.W, player);
				break; 
				}
			case SWT.ARROW_RIGHT: {	
				maze.movePlayer(DIR.E, player);
				break; 
				}
			case SWT.ARROW_UP: {	
				maze.movePlayer(DIR.N, player);
				break; 
			 } 
			}
			shell.redraw();
			
		} 
	});
	shell.addPaintListener(new PaintListener(){ 
        public void paintControl(PaintEvent e){
    		View.drawSolution(solution, e.gc);
    		maze.draw(e.gc);
    		player.draw(e.gc);
        }
    });
	
	//shell.pack();
    shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
    }

    private static void createMaze(final Shell shell) {
    	maze = MazeFactory.createMaze(GameSettings.rows, GameSettings.cols);
    	solution = maze.solve();
    	player = new Player();
    }
    private static void drawSolution(List<Node> solution, GC gc) {
    	int tileSize = GameSettings.tileSize;
    	int margin = GameSettings.margin;
    	for (Node node : solution) {
    		gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
    		gc.fillRectangle((node.x * tileSize) + margin +1, (node.y * tileSize + margin) + 1, tileSize -1, tileSize -1);
    	}
    }
	
    private static void drawSettings(final Shell shell) {
    	VerifyListener numberVerify = new VerifyListener() {

	        @Override
	        public void verifyText(VerifyEvent e) {

	            Text text = (Text)e.getSource();

	            // get old text and create new text by using the VerifyEvent.text
	            final String oldS = text.getText();
	            String newS = oldS.substring(0, e.start) + e.text + oldS.substring(e.end);

	            boolean isInt = true;
	            try
	            {
	                Integer.parseInt(newS);
	            }
	            catch(NumberFormatException ex)
	            {
	                isInt = false;
	            }

	            if(!isInt && newS != null && !newS.isEmpty())
	                e.doit = false;
	        }
	    };
	    
		Label rowsLabel = new Label(shell, SWT.NONE);
		rowsLabel.setText("Rows:");
		final Text rowsText = new Text(shell, SWT.BORDER);
		rowsText.setText(Integer.toString(GameSettings.rows));
		rowsText.addVerifyListener(numberVerify);
		
		Label colsLabel = new Label(shell, SWT.PUSH);
		colsLabel.setText("Columns:");
		final Text colsText = new Text(shell, SWT.BORDER);
		colsText.setText(Integer.toString(GameSettings.cols));
		colsText.addVerifyListener(numberVerify);
		
		Button applyBtn = new Button(shell, SWT.NONE);
		applyBtn.setText("Apply");
		applyBtn.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		        switch (e.type) {
		        case SWT.Selection: {
		        	try 
		        	{
			        	GameSettings.rows = Integer.parseInt(rowsText.getText());
			        	GameSettings.cols = Integer.parseInt(colsText.getText());
			            View.createMaze(shell);
			            shell.redraw();
		        	}
		        	catch (Exception err) {
		        		
		        	}
		            break;
		        }
		        }
		      }
		    });
    }
}
