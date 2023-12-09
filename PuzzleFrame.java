package slidepuzzleplus;
import java.awt.*;
import javax.swing.*;
import java.text.DecimalFormat;


public class PuzzleFrame extends JFrame {

	private SlidePuzzleBoard board;
	private PuzzleButton[][] button_board;
	private JLabel timeLabel;
	private Timer timer;
	private long startTime;
	private JPanel buttonPanel = new JPanel();
	private JComboBox<String> difficultyComboBox;  // 난이도 선택 컴포넌트 추가

	public PuzzleFrame() {
		board = new SlidePuzzleBoard(3);;
		button_board = new PuzzleButton[board.getPuzzleSize()][board.getPuzzleSize()];
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel p1 = new JPanel(new FlowLayout());
		difficultyComboBox = new JComboBox<>(new String[]{"초급", "중급", "고급"});
		difficultyComboBox.addActionListener(e -> updateDifficulty());
		p1.add(difficultyComboBox);

		StartButton startButton = new StartButton(board, this);
		startButton.addActionListener(e -> startGame());
		p1.add(startButton);

		JPanel p2 = new JPanel(new GridLayout(board.getPuzzleSize(), board.getPuzzleSize()));
		for (int row = 0; row < button_board.length; row++) {
			for (int col = 0; col < button_board.length; col++) {
				button_board[row][col] = new PuzzleButton(board, this);
				p2.add(button_board[row][col]);
			}
		}

		cp.add(p1, BorderLayout.NORTH);
		cp.add(p2, BorderLayout.CENTER);

		setTitle("Slide Puzzle");
		setSize(250, 300);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		timeLabel = new JLabel("00:00:00");
		buttonPanel.add(timeLabel);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);


		timer = new Timer(1000, e -> updateTimer());
		timer.setInitialDelay(0);
	}

	/** updateTimer -  게임이 시작된 후 경과한 시간을 표시하고 업데이트 **/
	private void updateTimer() {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = (currentTime - startTime) / 1000; // in seconds

		long hours = elapsedTime / 3600;
		long minutes = (elapsedTime % 3600) / 60;
		long seconds = elapsedTime % 60;

		DecimalFormat formatter = new DecimalFormat("00");

		timeLabel.setText(formatter.format(hours) + ":" + formatter.format(minutes) + ":" + formatter.format(seconds));
	}


	/** updateDifficulty - 선택된 난이도에 따라 게임 보드의 크기를 조절하고, 타이머를 초기화하며 UI를 업데이트 **/
	private void updateDifficulty() {
		String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
		int puzzleSize;

		switch (selectedDifficulty) {
			case "초급":
				puzzleSize = 3;
				break;
			case "중급":
				puzzleSize = 4;
				break;
			case "고급":
				puzzleSize = 5;
				break;
			default:
				puzzleSize = 3;  // 초급으로 기본 설정
		}
		timer.stop();
		board = new SlidePuzzleBoard(puzzleSize);
		startTimer();
		updateUI();
	}

	/** updateUI - timeLabel을 화면 하단에 추가 **/
	private void updateUI() {
		getContentPane().removeAll();

		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(difficultyComboBox);
		p1.add(new StartButton(board, this));

		JPanel p2 = new JPanel(new GridLayout(board.getPuzzleSize(), board.getPuzzleSize()));
		button_board = new PuzzleButton[board.getPuzzleSize()][board.getPuzzleSize()];
		for (int row = 0; row < button_board.length; row++) {
			for (int col = 0; col < button_board.length; col++) {
				button_board[row][col] = new PuzzleButton(board, this);
				p2.add(button_board[row][col]);
			}
		}

		getContentPane().add(p1, BorderLayout.NORTH);
		getContentPane().add(p2, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		revalidate();
		repaint();
	}

	/** update - 보드 프레임을 갱신함 **/
	public void update() {
		PuzzlePiece pp;
		for (int row = 0; row < button_board.length; row++)
			for (int col = 0; col < button_board.length; col++) {
				pp = board.getPuzzlePiece(row, col);
				if (pp != null)
					button_board[row][col].setText(Integer.toString(pp.faceValue()));
				else
					button_board[row][col].setText("");
			}
		if (!board.gameOn()) {
			finish();
		}
	}
	public void startTimer() {
		startTime = System.currentTimeMillis();
		timer.start();
	}


	/** startGame - 게임을 시작하고, 게임이 이미 시작 중이면 게임을 다시 시작 **/
	public void startGame() {
		timer.stop();
		timeLabel.setText("00:00:00");

		if (timer.isRunning()) {
			timer.restart();
		} else {
			startTimer();
		}
		board.createPuzzleBoard();
		updateUI();
	}

	/** finish - 퍼즐이 완료되었을 때 마지막 피스에 "Done" 텍스트를 표시하고, 타이머를 정지하며 소요 시간을 표시 **/
	public void finish() {
		int puzzleSize = board.getPuzzleSize();

		if (puzzleSize == 3) {
			button_board[2][2].setText("Done");
		} else {
			button_board[puzzleSize - 1][puzzleSize - 1].setText("Done");
		}
		timer.stop();
		updateTimer();

		String message = "소요 시간 : " + timeLabel.getText();
		JOptionPane.showMessageDialog(this, message, "게임 종료", JOptionPane.INFORMATION_MESSAGE);
		startGame();

	}

}