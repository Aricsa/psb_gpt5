package slidepuzzleplus;
import java.awt.event.*;
import javax.swing.*;

public class StartButton extends JButton implements ActionListener {
	
	private SlidePuzzleBoard board;
	private PuzzleFrame frame;
	private JComboBox<String> Box;
	
	public StartButton(SlidePuzzleBoard b, PuzzleFrame f, JComboBox<String> difficultyComboBox) {
		super("Start");
		board = b;
		frame = f;
		Box = difficultyComboBox;
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		board.restart();
		board.createPuzzleBoardWithPhotos((String) Box.getSelectedItem());
		frame.update();
		frame.startTimer();
	}
}