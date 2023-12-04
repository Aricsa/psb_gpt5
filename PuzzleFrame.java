import java.awt.*;
import javax.swing.*;

public class PuzzleFrame extends JFrame {

	
	private SlidePuzzleBoard board;
	private PuzzleButton[][] button_board;
	private JLabel scoreLabel;

	public PuzzleFrame(SlidePuzzleBoard b) {
		board = b;
		button_board = new PuzzleButton[4][4];
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel p1 = new JPanel(new FlowLayout());
		
		// 점수 라벨 추가
		scoreLabel = new JLabel("Score: " + calculateScore());
		
		
		p1.add(new StartButton(board, this));
		p1.add(scoreLabel, BorderLayout.NORTH);
		
		JPanel p2 = new JPanel(new GridLayout(4,4));
		
		for (int row = 0; row < button_board.length; row++)
			for (int col = 0; col < button_board.length; col++) {
				button_board[row][col] = new PuzzleButton(board, this);
				p2.add(button_board[row][col]);
			}
		cp.add(p1, BorderLayout.NORTH);
		cp.add(p2, BorderLayout.CENTER);
		update();
		setTitle("Slide Puzzle");
		setSize(250,300);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	/** update - 보드 프레임을 갱신함 */
	public void update() {
		PuzzlePiece pp;
		
		// 점수 업데이트
		scoreLabel.setText("Score: " + calculateScore());
		
		for (int row = 0; row < button_board.length; row++)
			for (int col = 0; col < button_board.length; col++) {
				pp = board.getPuzzlePiece(row, col);
				if (pp != null)
					button_board[row][col].setText(Integer.toString(pp.faceValue()));
				else
					button_board[row][col].setText("");
			}
			
			
			
			
	}
	
	/** finish - 퍼즐 게임 종료를 표시함 */
	public void finish() {
		button_board[3][3].setText("Done");
	}

	/** fail - 퍼즐 게임 실패를 표시함 */
	public void fail() {
		button_board[3][3].setText("fail");
	}



	/** calculateScore - 움직인 횟수에 따라 점수를 부여 */
	private int calculateScore() {
		return 1000 - (board.getMoveCount()*10);
	}

}