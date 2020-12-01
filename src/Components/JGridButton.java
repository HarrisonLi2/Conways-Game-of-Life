package Components;
import javax.swing.JButton;

public class JGridButton extends JButton {
	//associating a row,col coordinate to each Grid button
	private static final long serialVersionUID = 1L;
	int row,col;
	
	public JGridButton(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
