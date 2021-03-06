import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class StartScreen extends JFrame{
	
	private JPanel startContent;
	private JSlider boardSizer;
	private JLabel fieldLabel;
	
	//Start Screen Constructor
	public StartScreen() {
		super();
		this.setTitle("Conway's Game of Life");
		this.setSize(640, 640);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
		createPanel();
		createRuleLabels();
		createStart();
		
		this.setVisible(true);
	}
	
	//Create the Main Start Screen Content Panel
	private void createPanel() {
		startContent = new JPanel();
		startContent.setLayout(new BoxLayout(startContent, BoxLayout.Y_AXIS));
		startContent.setBackground(Color.LIGHT_GRAY);
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("src/images/logo.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			startContent.add(picLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.add(startContent, BorderLayout.CENTER);
	
	}
	
	//Create Components to populate the Main Content Panel
	private void createRuleLabels() {		
		
		JLabel welcome = new JLabel("Welcome to Conway's Game of Life!", SwingConstants.CENTER);
		welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
		welcome.setFont(new Font("Monospaced", Font.BOLD,28));
		startContent.add(welcome);
		
		startContent.add(Box.createVerticalStrut(25));
		
		String ruleText = "<html>Rules: <br>"
							+"1. Underpopulation: a living cell dies with 1 or fewer living neighbors. <br>"
							+"2. Overpopulation: a living cell dies with 4 or more living neighbors.<br>" 
							+"3. Persistence: A living cell with 2 or 3 living neighbors survives.<br>" 
							+"4. Reproduction: A dead cell with exactly 3 live neighbors becomes a live cell. </html>";
					
		JLabel rules = new JLabel(ruleText, SwingConstants.CENTER);
		rules.setFont(new Font("SansSerif", Font.BOLD, 14));
		rules.setAlignmentX(Component.CENTER_ALIGNMENT);
		startContent.add(rules);
		
		startContent.add(Box.createVerticalStrut(90));
		
		JLabel credits = new JLabel("Written by Harrison Li. Inspired by John Conway, Zachary Irving, Dylan Beattie.", SwingConstants.CENTER);
		credits.setFont(new Font("Serif", Font.PLAIN, 10));
		credits.setAlignmentX(Component.CENTER_ALIGNMENT);
		startContent.add(credits);
		
		
	}
	
	//Create and populate the Game Start Panel 
	private void createStart() {
		JPanel startPanel = new JPanel();
		startContent.add(Box.createVerticalStrut(25));
		
		
		
		boardSizer = new JSlider(25, 100);
		boardSizer.setMajorTickSpacing(75);
		boardSizer.addChangeListener(new SliderHandler());
		boardSizer.setPaintLabels(true);
		
		//initialize and configure board size slider
				fieldLabel = new JLabel("Selected board size: " + boardSizer.getValue(), SwingConstants.CENTER);
				fieldLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				startPanel.add(fieldLabel);
		startPanel.add(boardSizer);

		//initialize and configure start button
		JButton startButton = new JButton("Play Game");
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(new StartHandler());
		startContent.add(Box.createVerticalStrut(50));
		startPanel.add(startButton);
		this.add(startPanel, BorderLayout.SOUTH);
	}
	
	//Attempt to launch a game instance on button click
	private class StartHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int boardSize = boardSizer.getValue();
				TheGameOfLife game = new TheGameOfLife(boardSize);
				setContentPane(game.getContentPane());
				setSize(1280,960);
	
			}
			catch(NumberFormatException e1) {
				System.out.println("Something went wrong...");
			}
			
		}
	}
	
	//On slider change, update a label to reflect the new slider value
	private class SliderHandler implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			fieldLabel.setText("Selected board size: "+ boardSizer.getValue());
		}
	}
	
	//main 
	public static void main(String[]args) {
		JFrame screen = new StartScreen();
	}
}

