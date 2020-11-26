
public class GameEngine {
	//int 2d array to store game state
	private int[][] board;
	private int generation;
	
	//Nondefault: normal bootup
	public GameEngine(int boardsize) {
		board = new int[boardsize][boardsize];
		generation = 0;
	}
	
	//Nondefault: Manual bootup
	public GameEngine(int[][] starting) {
		board = starting;
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
		
		//Corner Cells
		
		//Edge Cells
		
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
	
	//if board size allows, insert pattern to top left of board
	public void insertPattern(String pattern) {
		switch(pattern){
			case "Glider":
				if(board.length > 6) {
					board[1][2] = 1;
					board[2][3] = 1;
					board[3][1] = 1;
					board[3][2] = 1;
					board[3][3] = 1;
				}
				
			default:
		}
	}
}
