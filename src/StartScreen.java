import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
	
	JPanel startContent;
	JSlider boardSizer;
	JLabel currentSelection;
	
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
	
	private void createRuleLabels() {
		JLabel welcome = new JLabel("Welcome to Conway's Game of Life!", SwingConstants.CENTER);
		welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
		startContent.add(welcome);
		
		startContent.add(Box.createVerticalStrut(25));
		
		JLabel overview = new JLabel("One Board of Cells. Zero Players. Living cells are green. Dead cells are black.", SwingConstants.CENTER);
		overview.setAlignmentX(Component.CENTER_ALIGNMENT);
		startContent.add(overview);
		
		startContent.add(Box.createVerticalStrut(25));
		
		String ruleText = "<html>Rules: <br>"
							+ "1. Underpopulation: a cell dies with 1 or fewer neighbors. <br>"+ 
							"2. Overpopulation: A living cell with 4 or more neighbors becomes a dead cell.<br>" + 
							"3. Persistence: A living cell with 2 or 3 living neighbors lives another generation.<br>" + 
							"4. Reproduction: A dead cell with exactly 3 live neighbors becomes a live cell. </html>";
					
		JLabel rules = new JLabel(ruleText, SwingConstants.CENTER);
		rules.setAlignmentX(Component.CENTER_ALIGNMENT);
		startContent.add(rules);
	}
	
	private void createStart() {
		JPanel panel = new JPanel();
		startContent.add(Box.createVerticalStrut(25));
		JLabel fieldLabel = new JLabel("Select board size: ", SwingConstants.CENTER);
		fieldLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(fieldLabel);
		
		currentSelection = new JLabel();
		panel.add(currentSelection);
		
		boardSizer = new JSlider(1, 100);
		boardSizer.setMajorTickSpacing(99);
		boardSizer.addChangeListener(new SliderHandler());
		boardSizer.setPaintLabels(true);
		
		panel.add(boardSizer);

		JButton startButton = new JButton("Play Game");
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(new StartHandler());
		startContent.add(Box.createVerticalStrut(50));
		panel.add(startButton);
		this.add(panel, BorderLayout.SOUTH);
	}
	
	
	private class StartHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int boardSize = boardSizer.getValue();
				TheGameOfLife game = new TheGameOfLife(boardSize);
				dispose();
			}
			catch(NumberFormatException e1) {
				System.out.println("Enter an integer please");
			}
			
		}
	}
	
	private class SliderHandler implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			currentSelection.setText(""+ boardSizer.getValue());
		}
	}
	
	public static void main(String[]args) {
		StartScreen screen = new StartScreen();
	}

}

