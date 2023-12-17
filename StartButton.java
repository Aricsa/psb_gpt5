package slidepuzzleplus;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartButton extends JButton implements ActionListener {

	private SlidePuzzleBoard board;
	private PuzzleFrame frame;

	private JComboBox<String> difficultyComboBox;



	public StartButton(SlidePuzzleBoard b, PuzzleFrame f,  JComboBox<String> difficultyComboBox) {
		super("Start");
		board = b;
		frame = f;
		this.difficultyComboBox = difficultyComboBox;

		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
		board.createPuzzleBoardWithPhotos(selectedDifficulty);
		frame.update();
	}
	public void setButtonImage(Image image) {
		if (image != null) {
			setIcon(new ImageIcon(image));
		} else {
			setIcon(null);
		}
	}
}