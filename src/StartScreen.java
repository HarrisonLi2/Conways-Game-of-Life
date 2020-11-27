import java.io.FileNotFoundException;

import javax.swing.*;

public class StartScreen extends JFrame{
	
	JPanel startContent;
	ImageIcon logo;
	
	public StartScreen() {
		super();
		this.setTitle("Conway's Game of Life");
		this.setSize(1280, 960);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		createLogo();
		this.setIconImage(logo.getImage());
		
		
		this.setVisible(true);
	}
	
	private void createPanel() {
		startContent = new JPanel();
		createLogo();
		
	
	}
	
	public void createLogo() {
		logo = new ImageIcon("src/images/logo.png");
	}
	
	
	
	public static void main(String[]args) {
		StartScreen screen = new StartScreen();
	}
}

