

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
	
	public void actionPerformed(ActionEvent e) {
		if (board.gameOn()) {
			String s = getText();
			if (! s.equals("") && board.move(Integer.parseInt(s))) {
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