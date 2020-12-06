import java.awt.Color;

/*
 * Game Of Life Logic Lives Here
*/
public class GameEngine {
	//int 2d array to store game state
	private int[][] board;
	private int generation;
	
	//Nondefault: normal bootup
	public GameEngine(int boardsize) {
		board = new int[boardsize][boardsize];
		generation = 0;
	}

	//Board accesser method
	public int[][] getBoard(){
		return board;
	}
	
	//Manual board override
	public void setBoardStatus(int[][] newBoard) {
		board = newBoard;
	}
	
	//sets individual grid status
	public void setBoardEntry(int row, int col, int status) {
		if(row >= 0 && row < board.length && col >=0 && col < board.length) {
			board[row][col] = status;
		}
	}
	
	//returns the generation count
	public int getGeneration() {
		return generation;
	}
	
	//Stringified board status
	public String toString() {
		String boardStatus = "";
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				boardStatus += " | " + board[row][col]; 
			}
			boardStatus += "\n";
		}
		return boardStatus;
	}
	
	//Switch a chosen cell from dead to live or from live to dead
	public void toggleBoardSquare(int row, int col) {
		if(row < board.length && col < board[0].length) {
			if(board[row][col] == 0) {
				board[row][col] = 1;
			}
			else {
				board[row][col] = 0;
			}
		}
		else {
			System.out.println("Specified square to toggle is out of bounds");
		}
	}
	
	/*Conway's Game of Life
	 Assumptions: 
	 A Cell is a (row,col) entry in a 2D int array
	 Dead Cell = 0 | Alive Cell = 1
	 Center cell: cell with 8 neighboring cell
	 
	 Rules:
	1. Underpopulation/Loneliness: A cell dies if less than 2 live neighbors
	2. Overpopulation: A cell dies if 4 or more live neighbors
	3. Survival: A cell persists if 2 or 3 live neighbors
	4. Reproduction: A dead cell comes alive with 3 live neighbors
	*/
	public void updateBoard() {
		if(board == null || board.length == 0 || board[0].length == 0) {
			System.out.println("Bad Board");
			return;
		}
		
		//next game state
		int[][] nextBoard = new int[board.length][board.length];
		
		//temp variable to count number of neighbors alive
		int numNeighborsAlive = 0;
		
		//populating next game state
		//Center Cells
		for(int row = 1; row < board.length-1; row++) {
			for(int col = 1; col < board[0].length-1; col++) {
				//get total Neighbor cell status
				numNeighborsAlive = board[row-1][col-1] + 
									board[row-1][col] +
									board[row-1][col+1] +
									board[row][col-1] +
									board[row][col+1] +
									board[row+1][col-1] +
									board[row+1][col] +
									board[row+1][col+1];
				
				//Dead Cell Case
				if(board[row][col] == 0) {
					//Cell Reproduction Check
					if(numNeighborsAlive == 3) {
						//cell resurrects
						nextBoard[row][col] = 1;
					}
					else {
						//default next gamestate to dead cell
						nextBoard[row][col] = 0;
					}
					continue; 
				}

				//Live Cell Cases
				//Cell Survival Check
				if(numNeighborsAlive == 2 || numNeighborsAlive == 3) {
					//cell survives
					nextBoard[row][col] = 1;
				}
				
				//Cell Underpopulation Check
				else if(numNeighborsAlive < 2) {
					//cell dies from underpopulation
					nextBoard[row][col] = 0;
				}
				
				//Cell Overpopulation Check
				else if(numNeighborsAlive >= 4) {
					//cell dies from overpopulation
					nextBoard[row][col] = 0;
				}
			}
		}
		//set next game state
		board = nextBoard;
		generation++;
	}
	
	//if board size allows, insert pattern to set location at top left of board
	public void insertPattern(String pattern) {
		switch(pattern){
		case "Block":
			if (board.length > 5) {
				board[1][1] = 1;
				board[1][2] = 1;
				board[2][1] = 1;
				board[2][2] = 1;
			}
			break;
		case "Beehive":
			if(board.length > 5) {
				board[2][1] = 1;
				board[1][2] = 1;
				board[1][3] = 1;
				board[3][2] = 1;
				board[3][3] = 1;
				board[2][4] = 1;
			}
			break;
		case "Blinker":
			if(board.length > 5) {
				board[1][2] = 1;
				board[2][2] = 1;
				board[3][2] = 1;
			}
			break;
		case "Pulsar":
			if(board.length > 25) {
				board[3][4] = 1;
				board[3][5] = 1;
				board[3][6] = 1;
				board[3][10] = 1;
				board[3][11] = 1;
				board[3][12] = 1;
				board[5][9] = 1;
				board[6][9] = 1;
				board[7][9] = 1;
				board[5][7] = 1;
				board[6][7] = 1;
				board[7][7] = 1;
				board[8][6] = 1;
				board[8][4] = 1;
				board[8][5] = 1;
				board[8][10] = 1;
				board[8][11] = 1;
				board[8][12] = 1;
				board[7][14] = 1;
				board[6][14] = 1;
				board[5][14] = 1;
				board[7][2] = 1;
				board[5][2] = 1;
				board[6][2] = 1;
				board[10][4] = 1;
				board[10][5] = 1;
				board[10][6] = 1;
				board[10][10] = 1;
				board[10][11] = 1;
				board[10][12] = 1;
				board[11][9] = 1;
				board[11][7] = 1;
				board[12][9] = 1;
				board[12][7] = 1;
				board[13][7] = 1;
				board[13][9] = 1;
				board[11][14] = 1;
				board[12][14] = 1;
				board[13][14] = 1;
				board[11][2] = 1;
				board[12][2] = 1;
				board[13][2] = 1;
				board[15][6] = 1;
				board[15][5] = 1;
				board[15][4] = 1;
				board[15][10] = 1;
				board[15][11] = 1;
				board[15][12] = 1;
			}
			break;
		case "Pentadecathlon":
			if(board.length > 20) {
				board[2][5] = 1;
				board[3][5] = 1;
				board[4][5] = 1;
				board[4][4] = 1;
				board[4][6] = 1;
				board[7][4] = 1;
				board[7][5] = 1;
				board[7][6] = 1;
				board[8][5] = 1;
				board[9][5] = 1;
				board[10][5] = 1;
				board[11][5] = 1;
				board[12][4] = 1;
				board[12][5] = 1;
				board[12][6] = 1;
				board[15][4] = 1;
				board[15][5] = 1;
				board[15][6] = 1;
				board[16][5] = 1;
				board[17][5] = 1;
			}
			break;
		case "Glider":
			if(board.length > 6) {
				board[1][2] = 1;
				board[2][3] = 1;
				board[3][1] = 1;
				board[3][2] = 1;
				board[3][3] = 1;
			}
			break;
		case "Light Spaceship":
			if(board.length > 20) {
				board[2][2] = 1;
				board[4][2] = 1;
				board[5][3] = 1;
				board[5][4] = 1;
				board[5][5] = 1;
				board[5][6] = 1;
				board[4][6] = 1;
				board[3][6] = 1;
				board[2][5] = 1;

			}
			break;
		case "Heavy Spaceship":
			if(board.length > 20) {
				board[2][2] = 1;
				board[1][4] = 1;
				board[1][5] = 1;
				board[2][7] = 1;
				board[4][2] = 1;
				board[5][3] = 1;
				board[5][4] = 1;
				board[5][5] = 1;
				board[5][6] = 1;
				board[5][7] = 1;
				board[5][8] = 1;
				board[4][8] = 1;
				board[3][8] = 1;
			}
			break;
		case "Gosper's Glider Gun":
			if(board.length > 40) {
				board[5][1] = 1;
				board[5][2] = 1;
				board[6][1] = 1;
				board[6][2] = 1;
				board[5][11] = 1;
				board[6][11] = 1;
				board[7][11] = 1;
				board[4][12] = 1;
				board[8][12] = 1;
				board[3][13] = 1;
				board[9][13] = 1;
				board[3][14] = 1;
				board[9][14] = 1;
				board[6][15] = 1;
				board[4][16] = 1;
				board[8][16] = 1;
				board[5][17] = 1;
				board[6][17] = 1;
				board[7][17] = 1;
				board[6][18] = 1;
				board[3][21] = 1;
				board[4][21] = 1;
				board[5][21] = 1;
				board[3][22] = 1;
				board[4][22] = 1;
				board[5][22] = 1;
				board[2][23] = 1;
				board[6][23] = 1;
				board[1][25] = 1;
				board[2][25] = 1;
				board[6][25] = 1;
				board[7][25] = 1;
				board[3][35] = 1;
				board[4][35] = 1;
				board[3][36] = 1;
				board[4][36] = 1;
			}
			break;
			default:
		}
	}
}
