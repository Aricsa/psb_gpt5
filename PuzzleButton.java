package slidepuzzleplus;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PuzzleButton extends JButton implements ActionListener {

	private SlidePuzzleBoard board;
	private PuzzleFrame frame;
	private int row;
	private int col;


	public PuzzleButton(SlidePuzzleBoard b, PuzzleFrame f, int r, int c) {
		board = b;
		frame = f;
		row = r;
		col = c;
		addActionListener(this);
	}

	public void setButtonImage(Image image) {
		if (image != null) {
			setIcon(new ImageIcon(image));
		} else {
			setIcon(null);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (board.gameOn()) {
			String s = getText();
			if (board.move(row, col)) {
				board.moveCount();
				frame.update();
				if (board.gameOver()){
					frame.finish();
					board.saveRank();
				} else if(board.gameFail()){
					frame.fail();
					board.saveRank();
				}

			}
		}
	}
}