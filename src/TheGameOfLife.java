import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Components.JGameGrid;
import Components.JGridButton;

import java.util.Timer;
import java.util.TimerTask;

/*
 * The Game Of Life Game Window Lives Here
 */

public class TheGameOfLife extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//game instance
	private GameEngine game;
	//main jpanels
	private JPanel gridPanel, controls;
	//custom grid of JGridButtons
	private JGameGrid grid;
	
	//components for control panel
	private JLabel directions, generationCount;
	private	JComboBox<String> selections;
	private JButton submit, reset, nextGeneration, toggleAutoMode;
	
	
	//auto generation mode
	private boolean automode = false;
	
	public TheGameOfLife(int boardSize) {
		super();
		this.setTitle("Conway's Game of Life");
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(1280, 960);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
		//instantiate the game of life Game logic
		game = new GameEngine(boardSize);
		//instantiate the grid for the game board
		gridPanel = new JPanel(new GridLayout(boardSize, boardSize));
		gridPanel.setBackground(Color.BLACK);
		grid = new JGameGrid(boardSize, gridPanel);
		
		addGridButtonListeners();
		
		//populate the game control panel
		this.createControlPanel();
		//populate the grid for the board
		this.updateGrid();
		
		//add main jpanels to jframe
		this.getContentPane().add(controls, BorderLayout.NORTH);
		this.getContentPane().add(gridPanel, BorderLayout.CENTER);
		//this.setVisible(true);
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
	
	//instantiate and add components to control panel
	private void createControlPanel() {
		//instantiate the game control panel
		controls = new JPanel(new BorderLayout());
		controls.setBackground(Color.BLACK);
		
		//instruction label
		directions = new JLabel("Click around or create a pattern, and observe the beauty of chaos!", SwingConstants.CENTER);
		directions.setAlignmentX(Component.CENTER_ALIGNMENT);
		directions.setFont(new Font("Monospaced", Font.BOLD, 20));
		directions.setForeground(Color.GREEN);
		controls.add(directions, BorderLayout.NORTH);
		
		
		//Flowlayout JPanel For buttons and I/O
		JPanel interactives = new JPanel();
		interactives.setBackground(Color.BLACK);
		//User options to add into game: can be added upon later
		String[] selectionOptions = {
				"Select Pattern", 
				"Block",
				"Beehive",
				"Blinker",
				"Pulsar",
				"Pentadecathlon",
				"Glider", 
				"Light Spaceship", 
				"Heavy Spaceship", 
				"Gosper's Glider Gun",
		};
		
		selections = new JComboBox<String>(selectionOptions);
		interactives.add(selections);
		
		submit = new JButton("Create Pattern");
		submit.addActionListener(new SubmitListener());
		interactives.add(submit);
		
		reset = new JButton("Reset Game");
		reset.addActionListener(new ResetListener());
		interactives.add(reset);
		
		nextGeneration = new JButton("Next Generation");
		nextGeneration.addActionListener(new NextGenListener());
		interactives.add(nextGeneration);
		
		toggleAutoMode = new JButton("Toggle Auto Mode");
		toggleAutoMode.addActionListener(new ToggleAutoModeListener());
		interactives.add(toggleAutoMode);
		
		generationCount = new JLabel();
		generationCount.setForeground(Color.GREEN);
		interactives.add(generationCount);
		
		controls.add(interactives, BorderLayout.CENTER);
		
	}
	
	//loop through the game board 2D array and render a corresponding cell for each entry
	public void updateGrid() {
		grid.updateGrid(game.getBoard());
		generationCount.setText("Generation: " + game.getGeneration());
		this.revalidate();
	}
	
	/**************************************
	 Begin ActionListener event handlers
	 **************************************/
	
	//Pattern Generation Handler
	private class SubmitListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			game.insertPattern((String)selections.getSelectedItem());
			updateGrid();
		}
	}
	
	//Game Reset Handler
	private class ResetListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			game = new GameEngine(game.getBoard().length);
			updateGrid();
			t.cancel();
			automode = false;
		}
	}
	
	//Next Generation Event Handler
	private class NextGenListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			game.updateBoard();
			updateGrid();
		}
	}
	
	//AutoGeneration Event Handler
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
						game.updateBoard();
						updateGrid();
					}
				}, 0, 50);
				
			}
		}
	}

	//Grid Button Click Event Handler
	private class GridButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JGridButton) {
				JGridButton button = (JGridButton) e.getSource();
				
				if(button.getBackground() == Color.BLACK) {
					game.setBoardEntry(button.getRow(), button.getCol(), 1);
					//System.out.println("board["+button.getRow() + "]["+button.getCol() +"] = 1;");
					button.setBackground(Color.GREEN);
				}
				else {
					game.setBoardEntry(button.getRow(), button.getCol(), 0);
					button.setBackground(Color.BLACK);
				}
				revalidate();
			}
		}
		
	}
	//Make grid buttons toggle when clicked
	private void addGridButtonListeners() {
		JGridButton[][] gameGrid = grid.getGrid();
		for(int row = 0; row < grid.getGridSize(); row++) {
			for(int col = 0; col < grid.getGridSize(); col++) {
				gameGrid[row][col].addActionListener(new GridButtonListener());
			}
		}
	}
}
