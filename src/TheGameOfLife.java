import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TheGameOfLife extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//game instance
	private GameEngine game;
	//main jpanels
	private JPanel grid, controls;
	
	//auto generation mode
	private boolean automode = false;
	
	public TheGameOfLife(int boardSize) {
		super();
		this.setTitle("Conway's Game of Life");
		this.setSize(1280, 960);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
		//instantiate the game of life Game logic
		game = new GameEngine(boardSize);
		//instantiate the grid for the game board
		grid = new JPanel(new GridLayout(boardSize, boardSize));
		//instantiate the game control panel
		controls = new JPanel();
		
		//populate the game control panel
		this.createControlPanel();
		//populate the grid for the board
		this.updateGrid();
		
		//add main jpanels to jframe
		this.getContentPane().add(controls, BorderLayout.NORTH);
		this.getContentPane().add(grid, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	//getter for automode toggle status 
	public boolean getAutoMode() {
		return automode;
	}
	//set autoMode
	public void setAutoMode(boolean status) {
		automode = status;
	}
	
	public GameEngine getGameInstance() {
		return game;
	}
	
	//components for control panel
	private JLabel directions, generationCount;
	private	JComboBox<String> selections;
	private JButton submit, reset, nextGeneration, toggleAutoMode;
	
	//instantiate and add components to control panel
	private void createControlPanel() {
		directions = new JLabel("Welcome to the Game of Life! Create a pattern and watch the beauty of simple chaos:");
		
		controls.add(directions, BorderLayout.NORTH);
		
		//User options to add into game: can be added upon later
		String[] selectionOptions = {
				"None", "Glider", "Light Spaceship", "Heavy Spaceship", "Glider Gun"
		};
		
		selections = new JComboBox<String>(selectionOptions);
		controls.add(selections);
		
		submit = new JButton("Create Pattern");
		submit.addActionListener(new SubmitListener());
		controls.add(submit);
		
		reset = new JButton("Reset Game");
		reset.addActionListener(new ResetListener());
		controls.add(reset);
		
		nextGeneration = new JButton("Next Generation");
		nextGeneration.addActionListener(new NextGenListener());
		controls.add(nextGeneration);
		
		toggleAutoMode = new JButton("Enable AutoMode");
		toggleAutoMode.addActionListener(new ToggleAutoModeListener());
		controls.add(toggleAutoMode);
		
		generationCount = new JLabel();
		controls.add(generationCount);
		
	}
	
	//loop through the game board 2D array and render a corresponding cell for each entry
	public void updateGrid() {
		this.remove(grid);
		grid.removeAll();
		for(int row = 0; row < game.getBoard().length; row++) {
			for(int col = 0; col < game.getBoard().length; col++) {
				JLabel gridCell = new JLabel();
				if(game.getBoard()[row][col] == 0) {
					gridCell.setBackground(Color.black);
				}
				else {
					gridCell.setBackground(Color.green);
				}
				gridCell.setOpaque(true);
				grid.add(gridCell);
			}
		}
		generationCount.setText("Generation: " + game.getGeneration());
		this.getContentPane().add(grid, BorderLayout.CENTER);
		this.revalidate();
	}
	
	
	//Main Method
	public static void main(String[]args) {
		TheGameOfLife game = new TheGameOfLife(25);
	}
	
	/**************************************
	 Begin ActionListener event handlers
	 **************************************/
	private class SubmitListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			game.insertPattern((String)selections.getSelectedItem());
			updateGrid();
		}
	}
	
	private class ResetListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			game = new GameEngine(game.getBoard().length);
			updateGrid();
			t.cancel();
			automode = false;
		}
	}
	
	private class NextGenListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			game.updateBoard();
			updateGrid();
		}
	}
	
	private Timer t = new Timer();
	private class ToggleAutoModeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(automode) {
				t.cancel();
				automode = false;
			}
			else {
				automode = true;
				t = new Timer();
				t.schedule(new TimerTask() {
					public void run() {
						nextGeneration.doClick();
					}
				}, 0, 500);
				
			}
		}
	}
}
