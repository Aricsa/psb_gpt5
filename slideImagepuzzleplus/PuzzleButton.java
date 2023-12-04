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

	public void actionPerformed(ActionEvent e) {
		if (board.gameOn()) {
			if (board.move(row, col)) {
				frame.update();
				if (board.gameOver())
					frame.finish();
			}
		}
	}

	public void setButtonImage(Image image) {
		if (image != null) {
			setIcon(new ImageIcon(image));
		} else {
			setIcon(null);
		}
	}
}