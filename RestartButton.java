package slidepuzzleplus;

import java.awt.event.*;
import javax.swing.*;

public class RestartButton extends JButton implements ActionListener {
	
	private SlidePuzzleBoard board;
	private PuzzleFrame frame;
	
	public RestartButton(SlidePuzzleBoard b, PuzzleFrame f) {
		super("Restart");
		board = b;
		frame = f;
		addActionListener(this);
	}
	
    // 게임 랭킹을 저장하고 신규 랭킹 저장공간을 불러와 게임을 재시작 할 수 있도록 구성
    
	public void actionPerformed(ActionEvent e) {
        
        board.saveRank();
        frame.importRank();
        board.restart();
        board.createPuzzleBoard();
		frame.update();

	}

}