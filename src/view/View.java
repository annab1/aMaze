package view;

import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridLayout;

import Model.Node;
import Model.MazeFactory.DIR;

import org.eclipse.swt.widgets.*;

public class View {
	private Maze maze;
	private Player player;
	private List<Node> solution;
	private boolean showSolution;
	private Shell shell;
	private Listener solveListener;
	private Listener applyListener;
	
    public View(int [][] maze) {
    	this.init(maze);
    }
    
    public void draw() {
		
		final Display display = new Display ();
		shell = new Shell(display);
		shell.setSize(900, 600);
		shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		this.drawSettings(shell);
		GridLayout layout = new GridLayout(8, false);    
		shell.setLayout(layout);	
		
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
	    		maze.draw(e.gc);
	    		if (showSolution) {
	        		drawSolution(e.gc);
	        	}
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
    
    public void init(int [][] maze) {
    	this.maze = new Maze(maze);
    	this.player = new Player();
    	this.showSolution = false;
    }
    
    private void drawSolution(GC gc) {

    	int tileSize = GameSettings.tileSize;
    	int margin = GameSettings.margin;
    	for (Node node : solution) {
    		gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
    		gc.fillRectangle((node.x * tileSize) + margin +1, (node.y * tileSize + margin) + 1, tileSize -1, tileSize -1);
    	}
    }
    
    public void onSolve(Listener listener) {
    	solveListener = listener;
    }
    
    public void onApply(Listener listener) {
    	applyListener = listener;
    }
    
    public void showSolution(List<Node> solution) {
    	this.solution = solution;
    	this.showSolution = true;
    	this.shell.redraw();
    }
    
	
	public void drawSettings(final Shell shell) {
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
			        	if (applyListener != null) {
			        		applyListener.handleEvent(e);
			        	}
			            shell.redraw();
		        	}
		        	catch (Exception err) {}
		            break;
		        }
		        }
		      }
		    });
		
		Button solveBtn = new Button(shell, SWT.NONE);
		solveBtn.setText("Solve");
		solveBtn.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		        switch (e.type) {
		        case SWT.Selection: {
		        	try 
		        	{
		        		if (solveListener != null) {
 		        		  solveListener.handleEvent(e);
		        		}
		        	}
		        	catch (Exception err) {}
		            break;
		        }
		        }
		      }
		    });
    }
}
