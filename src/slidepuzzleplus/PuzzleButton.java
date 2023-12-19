package slidepuzzleplus;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PuzzleButton extends JButton implements ActionListener {

	private SlidePuzzleBoard board;
	private PuzzleFrame frame;
	
	public PuzzleButton(SlidePuzzleBoard b, PuzzleFrame f) {
		board = b;
		frame = f;
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
			String s = getName();
			if (!s.equals("") && board.move(Integer.parseInt(s))) {
                frame.update();
				if (board.gameOver()){
					frame.finish();
			    } else if(board.gameFail()){
					frame.fail();
				}
			}
		}
	}
}