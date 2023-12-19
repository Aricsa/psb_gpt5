package slidepuzzleplus;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class PuzzleFrame extends JFrame {

	private SlidePuzzleBoard board;
	private PuzzleButton[][] button_board;
	private JLabel scoreLabel;
	Container cp = getContentPane();
	File_IO fi = new File_IO();
	JTextArea rank = new JTextArea();
	JTextArea ranknum = new JTextArea();
	JTextArea scoresign = new JTextArea();
	private JLabel timeLabel;
	private Timer timer;
	private long startTime;
	private JPanel buttonPanel = new JPanel();
	private JComboBox<String> difficultyComboBox;  // 난이도 선택 컴포넌트 추가


	public PuzzleFrame() {
		board = new SlidePuzzleBoard(3);
		button_board = new PuzzleButton[board.getPuzzleSize()][board.getPuzzleSize()];
		cp.setLayout(new BorderLayout(20, 20));

		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		// 점수 라벨 추가
		scoreLabel = new JLabel("Remaining moves: " + LeftMovement());

		difficultyComboBox = new JComboBox<>(new String[]{"초급", "중급", "고급"});
		difficultyComboBox.addActionListener(e -> updateDifficulty());
		p1.add(difficultyComboBox);
		p1.add(new StartButton(board, this, difficultyComboBox));
		p1.add(scoreLabel);

		timeLabel = new JLabel("00:00:00");
		buttonPanel.add(timeLabel);
		p1.add(buttonPanel);

		timer = new Timer(1000, e -> updateTimer());
		timer.setInitialDelay(0);


		JPanel p2 = new JPanel(new GridLayout(board.getPuzzleSize(), board.getPuzzleSize()));
		for (int row = 0; row < button_board.length; row++) {
			for (int col = 0; col < button_board.length; col++) {
				button_board[row][col] = new PuzzleButton(board, this);
				p2.add(button_board[row][col]);
			}
		}

		//왼쪽 서브 패널
		JPanel rankinglist = new JPanel();
		rankinglist.setLayout(new BorderLayout());

		//상단 랭크 텍스트
		JPanel textPanel = new JPanel();
		JLabel westText = new JLabel("랭킹");
		textPanel.add(westText);
		westText.setFont(new Font("나눔고딕", Font.BOLD, 15));
		textPanel.setBackground(new Color(145, 181, 255));
		rankinglist.add(textPanel, BorderLayout.NORTH);

		//순위, 점수 패널
		JPanel rankpanel = new JPanel();
		rankpanel.setLayout(new BorderLayout());
		ranknum.setEditable(false);
		rank.setEditable(false);
		rankpanel.add(ranknum, BorderLayout.WEST);
		rankpanel.add(rank, BorderLayout.CENTER);
		ranknum.setBackground(new Color(198, 217, 255));
		rank.setBackground(new Color(219, 231, 255));
		ranknum.setFont(new Font("나눔고딕", Font.BOLD, 10));
		rank.setFont(new Font("나눔고딕", Font.BOLD, 10));

		//점수 "점"
		rankpanel.add(scoresign, BorderLayout.EAST);
		scoresign.setEditable(false);
		scoresign.setBackground(new Color(219, 231, 255));
		scoresign.setFont(new Font("나눔고딕", Font.BOLD, 10));

		rankinglist.add(rankpanel, BorderLayout.CENTER);

		importRank();

		JPanel p3 = new JPanel(new GridLayout(4, 2, 20, 10));
		p3.add(new MoveButton(board, this, 1, "↑"));
		p3.add(new MoveButton(board, this, 2, "↓"));
		p3.add(new MoveButton(board, this, 3, "←"));
		p3.add(new MoveButton(board, this, 4, "→"));
		p3.add(new LineMoveButton(board, this, 1, "↑↑"));
		p3.add(new LineMoveButton(board, this, 2, "↓↓"));
		p3.add(new LineMoveButton(board, this, 3, "←←"));
		p3.add(new LineMoveButton(board, this, 4, "→→"));
		cp.add(p1, BorderLayout.NORTH);
		cp.add(p2, BorderLayout.CENTER);
		cp.add(p3, BorderLayout.SOUTH);
		cp.add(rankinglist, BorderLayout.WEST);
		setTitle("Slide Puzzle");
		setSize(600, 700);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

		/** updateTimer -  게임이 시작된 후 경과한 시간을 표시하고 업데이트 **/
	private void updateTimer () {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = (currentTime - startTime) / 1000; // in seconds

		long hours = elapsedTime / 3600;
		long minutes = (elapsedTime % 3600) / 60;
		long seconds = elapsedTime % 60;

		DecimalFormat formatter = new DecimalFormat("00");

		timeLabel.setText(formatter.format(hours) + ":" + formatter.format(minutes) + ":" + formatter.format(seconds));
	}


	/** updateDifficulty - 선택된 난이도에 따라 게임 보드의 크기를 조절하고, 타이머를 초기화하며 UI를 업데이트 **/
	private void updateDifficulty () {
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
		updateUI();
	}

	/** updateUI - timeLabel을 화면 하단에 추가 **/
	private void updateUI () {
		cp.removeAll();
		JPanel p1 = new JPanel(new FlowLayout());
		// 점수 라벨 추가
		scoreLabel = new JLabel("Remaining moves: " + LeftMovement());

		p1.add(difficultyComboBox);
		p1.add(new StartButton(board, this, difficultyComboBox));
		p1.add(scoreLabel);
		p1.add(buttonPanel);

		JPanel p2 = new JPanel(new GridLayout(board.getPuzzleSize(), board.getPuzzleSize()));
		button_board = new PuzzleButton[board.getPuzzleSize()][board.getPuzzleSize()];
		for (int row = 0; row < button_board.length; row++) {
			for (int col = 0; col < button_board.length; col++) {
				button_board[row][col] = new PuzzleButton(board, this);
				p2.add(button_board[row][col]);
			}
		}

		//왼쪽 서브 패널
		JPanel rankinglist = new JPanel();
		rankinglist.setLayout(new BorderLayout());

		//상단 랭크 텍스트
		JPanel textPanel = new JPanel();
		JLabel westText = new JLabel("랭킹");
		textPanel.add(westText);
		westText.setFont(new Font("나눔고딕", Font.BOLD, 15));
		textPanel.setBackground(new Color(145, 181, 255));
		rankinglist.add(textPanel, BorderLayout.NORTH);


		//순위, 점수 패널
		JPanel rankpanel = new JPanel();
		rankpanel.setLayout(new BorderLayout());
		ranknum.setEditable(false);
		rank.setEditable(false);
		rankpanel.add(ranknum, BorderLayout.WEST);
		rankpanel.add(rank, BorderLayout.CENTER);
		ranknum.setBackground(new Color(198, 217, 255));
		rank.setBackground(new Color(219, 231, 255));
		ranknum.setFont(new Font("나눔고딕", Font.BOLD, 10));
		rank.setFont(new Font("나눔고딕", Font.BOLD, 10));

		//점수 "점"
		rankpanel.add(scoresign, BorderLayout.EAST);
		scoresign.setEditable(false);
		scoresign.setBackground(new Color(219, 231, 255));
		scoresign.setFont(new Font("나눔고딕", Font.BOLD, 10));

		rankinglist.add(rankpanel, BorderLayout.CENTER);

		importRank();

		JPanel p3 = new JPanel(new GridLayout(4, 2, 20, 10));
		p3.add(new MoveButton(board, this, 1, "↑"));
		p3.add(new MoveButton(board, this, 2, "↓"));
		p3.add(new MoveButton(board, this, 3, "←"));
		p3.add(new MoveButton(board, this, 4, "→"));
		p3.add(new LineMoveButton(board, this, 1, "↑↑"));
		p3.add(new LineMoveButton(board, this, 2, "↓↓"));
		p3.add(new LineMoveButton(board, this, 3, "←←"));
		p3.add(new LineMoveButton(board, this, 4, "→→"));
		cp.add(p1, BorderLayout.NORTH);
		cp.add(p2, BorderLayout.CENTER);
		cp.add(p3, BorderLayout.SOUTH);
		cp.add(rankinglist, BorderLayout.WEST);

		revalidate();
		repaint();
	}

	/** update - 보드 프레임을 갱신함 **/
	public void update () {
		PuzzlePiece pp;

		// 점수 업데이트
		scoreLabel.setText("Remaining moves: " + LeftMovement());

		for (int row = 0; row < button_board.length; row++)
			for (int col = 0; col < button_board.length; col++) {
				pp = board.getPuzzlePiece(row, col);
				if (pp != null) {
					button_board[row][col].setName(Integer.toString(pp.faceValue()));
					Image image = pp.getImage();
					button_board[row][col].setButtonImage(image);
				}
				else {
					button_board[row][col].setButtonImage(null);
					button_board[row][col].setName("");
				}
			}
		if (!board.gameOn()) {
			finish();
		}
	}
	public void startTimer () {
		startTime = System.currentTimeMillis();
		timer.start();
	}

	/** setGame - 게임을 시작하고, 게임이 이미 시작 중이면 게임을 다시 시작 **/
	public void setGame () {
		timer.stop();
		timeLabel.setText("00:00:00");
		updateUI();
	}

	/** finish - 퍼즐이 완료되었을 때 마지막 피스에 "Done" 텍스트를 표시하고, 타이머를 정지하며 소요 시간을 표시 **/
	public void finish () {
		int puzzleSize = board.getPuzzleSize();
		String image_path = "";
		if (puzzleSize == 4)
			image_path += "images/Hanyangi_4x4/";
		else if (puzzleSize == 5)
			image_path += "images/Hanyangi_5x5/";
		else
			image_path += "images/Hanyangi_3x3/";
		image_path += "Hanyangi_" + String.format("%02d",puzzleSize * puzzleSize) + ".png";
		Image image = new ImageIcon(image_path).getImage();
		button_board[puzzleSize - 1][puzzleSize - 1].setButtonImage(image);
		timer.stop();
		updateTimer();

		String message = "소요 시간 : " + timeLabel.getText();
		JOptionPane.showMessageDialog(this, message, "게임 종료", JOptionPane.INFORMATION_MESSAGE);
		setGame();
		board.saveRank();
		importRank();
	}

	/** fail - 퍼즐 게임 실패를 표시함 */
	public void fail () {
		button_board[button_board.length - 1][button_board.length - 1].setText("fail");
	}


	/** LeftMovement - 움직인 횟수에 따라 점수를 부여 */
	private int LeftMovement () {
		return 1000 - (board.getMoveCount());
	}

	public void importRank ()
	{
		ranknum.setText("");
		rank.setText("");
		scoresign.setText("");

		String ranklist = fi.read_word();
		if (ranklist.equals("")) {
			ranknum.append("1위 ");
			rank.append(" 0 ");
			scoresign.append(" 점 ");
			return;
		}

		String[] str = ranklist.split("\n");
		int[] temp = new int[str.length];

		for (int i = 0; i < str.length; i++)
			temp[i] = Integer.parseInt(str[i]);

		temp = sort(temp);

		for (int i = 0; i < str.length; i++) {
			ranknum.append(" " + String.valueOf(i + 1) + "위 \n");
			rank.append(" " + temp[i] + "\n");
			scoresign.append(" 점 \n");
		}
	}

	public int[] sort ( int[] arr)
	{
		for (int i = arr.length - 1; i > 0; i--)
			for (int j = 0; j < i; j++)
				if (arr[j] < arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
		return arr;
	}
}