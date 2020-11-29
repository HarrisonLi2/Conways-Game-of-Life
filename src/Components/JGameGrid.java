package Components;
import java.awt.Color;

import javax.swing.*;

/*
 * The Game of Life Board Display Data Lives Here
 */

public class JGameGrid{
	JGridButton[][] grid;
	int gridSize;
	
	public JGameGrid(int gridSize, JPanel panel){
		this.gridSize = gridSize;
		grid = new JGridButton[gridSize][gridSize];
		for(int row = 0; row < gridSize; row++) {
			for(int col = 0; col < gridSize; col++) {
				grid[row][col] = new JGridButton(row, col);
				grid[row][col].setBackground(Color.BLACK);
				grid[row][col].setBorder(BorderFactory.createLineBorder(Color.black, 1));
				panel.add(grid[row][col]);
			}
		}
	}
	
	public int getGridSize() {
		return gridSize;
	}
	
	//get the entire JLabel grid
	public JGridButton[][] getGrid(){
		return grid;
	}
	
	//get a single specified grid entry
	public JGridButton getGridEntry(int row, int col) {
		if(row >= 0 && row < grid.length && col >= 0 && col < grid.length) {
			return grid[row][col];
		}
		return null;
	}
	
	//update grid entries to reflect board status
	public void updateGrid(int [][] board) {
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board.length; col++) {
				if(board[row][col] == 0) {
					grid[row][col].setBackground(Color.black);
				}
				else {
					grid[row][col].setBackground(Color.green);
				}
				grid[row][col].setOpaque(true);
			}
		}
	}
	
}
