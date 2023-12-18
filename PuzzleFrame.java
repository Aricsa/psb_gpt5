

import javax.swing.*;
import java.awt.*;

public class PuzzleFrame extends JFrame  {

	
	private SlidePuzzleBoard board;
	private PuzzleButton[][] button_board;
	private JLabel scoreLabel;
	Container cp = getContentPane();
	File_IO fi = new File_IO();
    JTextArea rank = new JTextArea();
    JTextArea ranknum = new JTextArea();
    JTextArea scoresign = new JTextArea();

	public PuzzleFrame(SlidePuzzleBoard b) {
		board = b;
		button_board = new PuzzleButton[4][4];
		cp.setLayout(new BorderLayout());
		JPanel p1 = new JPanel(new FlowLayout());
		
		// 점수 라벨 추가
		scoreLabel = new JLabel("Score: " + calculateScore());
		
		p1.add(new RestartButton(board, this));
		p1.add(new StartButton(board, this));
		p1.add(scoreLabel, BorderLayout.NORTH);
		
		JPanel p2 = new JPanel(new GridLayout(4,4));
		
		for (int row = 0; row < button_board.length; row++)
			for (int col = 0; col < button_board.length; col++) {
				button_board[row][col] = new PuzzleButton(board, this);
				p2.add(button_board[row][col]);
			}
		
		//왼쪽 서브 패널
        JPanel rankinglist = new JPanel();
        rankinglist.setLayout(new BorderLayout());

        //상단 랭크 텍스트
        JPanel textPanel = new JPanel();
        JLabel westText = new JLabel("랭킹");
        textPanel.add(westText);
        westText.setFont(new Font("나눔고딕", Font.BOLD, 15));
        textPanel.setBackground(new Color(145,181,255));
        rankinglist.add(textPanel,BorderLayout.NORTH);


        //순위, 점수 패널
        JPanel rankpanel = new JPanel();
        rankpanel.setLayout(new BorderLayout());
        ranknum.setEditable(false);
        rank.setEditable(false);
        rankpanel.add(ranknum,BorderLayout.WEST);
        rankpanel.add(rank,BorderLayout.CENTER);
        ranknum.setBackground(new Color(198,217,255));
        rank.setBackground(new Color(219,231,255));
        ranknum.setFont(new Font("나눔고딕", Font.BOLD, 10));
        rank.setFont(new Font("나눔고딕", Font.BOLD, 10));

        //점수 "점"
        rankpanel.add(scoresign,BorderLayout.EAST);
        scoresign.setEditable(false);
        scoresign.setBackground(new Color(219,231,255));
        scoresign.setFont(new Font("나눔고딕", Font.BOLD, 10));

        rankinglist.add(rankpanel,BorderLayout.CENTER);

        importRank();

		JPanel p3 = new JPanel(new GridLayout(4, 2, 20, 10));
		p3.add(new MoveButton(board, this, 1, "↑"));
		p3.add(new MoveButton(board, this, 2, "↓"));
		p3.add(new MoveButton(board, this, 3, "←"));
		p3.add(new MoveButton(board, this, 4, "→"));
		p3.add(new LineMoveButton(board, this , 1 , "↑↑"));
		p3.add(new LineMoveButton(board, this , 2 , "↓↓"));
		p3.add(new LineMoveButton(board, this , 3 , "←←"));
		p3.add(new LineMoveButton(board, this , 4 , "→→"));
		cp.add(p1, BorderLayout.NORTH);
		cp.add(p2, BorderLayout.CENTER);
		cp.add(p3, BorderLayout.SOUTH);
    cp.add(rankinglist,BorderLayout.WEST);
		update();
		setTitle("Slide Puzzle");
		setSize(300,400);
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
		board.saveRank();
	}

	/** fail - 퍼즐 게임 실패를 표시함 */
	public void fail() {
		button_board[3][3].setText("fail");
	}



	/** calculateScore - 움직인 횟수에 따라 점수를 부여 */
	private int calculateScore() {
		return 10000 - (board.getMoveCount()*10);
	}

	public void importRank()
    {
        ranknum.setText("");
        rank.setText("");
        scoresign.setText("");

        String ranklist = fi.read_word();
        if(ranklist.equals(""))
        {
            ranknum.append("1위 ");
            rank.append(" 0 ");
            scoresign.append(" 점 ");
            return;
        }

        String[] str = ranklist.split("\n");
        int[] temp = new int[str.length];

        for(int i = 0; i<str.length; i++)
            temp[i] = Integer.parseInt(str[i]);

        temp = sort(temp);

        for(int i = 0; i<str.length; i++)
        {
            ranknum.append(" "+String.valueOf(i+1)+"위 \n");
            rank.append(" "+temp[i]+"\n");
            scoresign.append(" 점 \n");
        }
    }

	public int[] sort(int[] arr)
    {
        for(int i = arr.length - 1; i>0; i-- )
            for(int j = 0; j<i; j++)
                if(arr[j] < arr[j + 1])
                {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
        return arr;
    }

	

}