


import java.util.*;

import javax.swing.JTextArea;

/** SlidePuzzleBoard models a slide puzzle. */ 
public class SlidePuzzleBoard {
	private PuzzlePiece[][] board;
	private PuzzleFrame frame;
	File_IO fi = new File_IO();
	JTextArea rank = new JTextArea();
    JTextArea ranknum = new JTextArea();
    JTextArea scoresign = new JTextArea();
	private int score = 0;
	
	
	// 움직이는 횟수에 따라 점수를 카운트
	private int moveCount;

	// 빈칸의 좌표 
	private int empty_row;
	private int empty_col;
	// representation invariant: board[empty_row][empty_col] == null

	private boolean game_on = false;

	public SlidePuzzleBoard() {

		// 점수 관련 생성자 초기화
		moveCount = 0;

		board = new PuzzlePiece[4][4];
		// 퍼즐 조각 1~15를 보드에 순서대로 끼우기 
		int number = 1;
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++) {
				if (col != 3 || row != 3) {
					board[row][col] = new PuzzlePiece(number);
					number += 1;
				} else {
					board[3][3] = null;
					empty_row = 3;
					empty_col = 3;
				}
			}
	}

	/**
	 * getPuzzlePiece - 퍼즐 조각을 리턴
	 *
	 * @param row - 가로줄 인덱스
	 * @param col - 세로줄 인덱스
	 * @return 퍼즐 조각
	 */
	public PuzzlePiece getPuzzlePiece(int row, int col) {
		return board[row][col];
	}

	/**
	 * gameOn - 게임이 진행중인지 점검하는 함수
	 *
	 * @return 게임이 진행중이면 true, 끝났으면 false
	 */
	public boolean gameOn() {
		return game_on;
	}

	/**
	 * 이동이 가능하면, 퍼즐 조각을 빈칸으로 이동
	 *
	 * @param w - 이동하기 원하는 퍼즐 조각
	 * @return 이동 성공하면 true를 리턴하고, 이동이 불가능하면 false를 리턴
	 */
	public boolean move(int w) {
		int row, col; // w의 위치 
		// 빈칸에 주변에서 w의 위치를 찾음 
		if (found(w, empty_row - 1, empty_col)) {
			row = empty_row - 1;
			col = empty_col;
		} else if (found(w, empty_row + 1, empty_col)) {
			row = empty_row + 1;
			col = empty_col;
		} else if (found(w, empty_row, empty_col - 1)) {
			row = empty_row;
			col = empty_col - 1;
		} else if (found(w, empty_row, empty_col + 1)) {
			row = empty_row;
			col = empty_col + 1;
		} else
			return false;

		 // 움직임이 가능한 경우 moveCount를 증가시킨다.
        if (board[empty_row][empty_col] != null) {
            moveCount();
            frame.update();
        }

		// w를 빈칸에 복사
		board[empty_row][empty_col] = board[row][col];
		// 빈칸 위치를 새로 설정하고, w를 제거
		empty_row = row;
		empty_col = col;
		board[empty_row][empty_col] = null;
		return true;
	}

	/**
	 * found - board[row][col]에 퍼즐 조각 v가 있는지 확인
	 *
	 * @param v   - 확인할 수
	 * @param row - 보드의 가로줄 인덱스
	 * @param col - 보드의 세로줄 인덱스
	 * @return 있으면 true, 없으면 false
	 */
	private boolean found(int v, int row, int col) {
		if (row >= 0 && row <= 3 && col >= 0 && col <= 3)
			return board[row][col].faceValue() == v;
		else
			return false;
	}

	/**
	 * createPuzzleBoard - 퍼즐 게임 초기 보드 생성
	 */
	public void createPuzzleBoard() {
		int[] numbers = generateRandomPermutation(15);
		int i = 0;
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++) {
				if (col != 3 || row != 3) {
					board[row][col] = new PuzzlePiece(numbers[i] + 1);
					i += 1;
				} else {
					board[3][3] = null;
					empty_row = 3;
					empty_col = 3;
				}
			}
		game_on = true;
	}

	/**
	 * generateRandomPermutation - 0~n-1 범위의 정수 수열을 무작위로 섞은 배열을 리턴 한다.
	 *
	 * @param n - 수열의 길이
	 * @return 0~n-1 범위의 정수를 무작위로 섞어 만든 배열
	 */
	private int[] generateRandomPermutation(int n) {
		Random random = new Random();
		int[] permutation = new int[n];
		for (int i = 0; i < n; i++) {
			int d = random.nextInt(i + 1);
			permutation[i] = permutation[d];
			permutation[d] = i;
		}
		return permutation;
	}
	
	/** moveCount - 플레이어가 게임 중 퍼즐을 움직이는 수 계산
	 */

	 public void moveCount(){
		moveCount++;
	 }

	 /** getMoveCount - 현재 움직인 횟수를 반환하는 메서드 (점수 계산, 게임 중 출력) 
	  * @return 움직인 횟수
	 */
	public int getMoveCount(){
		return moveCount;
	}
	
	/** gameOver - 퍼즐 게임이 끝났는지를 확인  
	 * @return 목표를 달성했으면 true, 아직 더 진행해야 하면 false 
	 */
	public boolean gameOver() {
		if (empty_row != 3 || empty_col != 3)
			return false;
		else {
			int number = 1;
			for (int row = 0; row < 4; row++)
				for (int col = 0; col < 4; col++) {
					if (col != 3 || row != 3)
						if (board[row][col].faceValue() != number)
							return false;
						else
							number += 1;
				}
			game_on = false;
			return true;

		}
	}

	/** gameFail - 플레이어의 움직임 가능 횟수가 초과했는지 확인 
	 * @return 초과했으면, true, 아직 더 남아있다면 false 
	 */
	public boolean gameFail() {
		if (10000-moveCount*10 > 0)
			return false;
		else {
			game_on = false;
			return true;
			
		}

	}

	

	/** saveRank - 점수 데이터 베이스에 저장 */
    public void saveRank()
    {
		score = 10000 - moveCount * 10; // 수정해야할 부분(김민서)
        if(score != 0)
            fi.saveFile(score+"\n");
    }
	
	/** restart - 점수 결과를 저장하고, 설정값 초기화를 통한 게임 재시작 */
	public void restart()
    {
		moveCount = 0;
		score = 10000;
    
    }

	public boolean newmove(int type)
	{
		int row, col;
		if (type == 1) {
			if (empty_row == 0)
				return false;
			row = empty_row - 1;
			col = empty_col;
		} else if (type == 2) {
			if (empty_row == 3)
				return false;
			row = empty_row + 1;
			col = empty_col;
		} else if (type == 3) {
			if (empty_col == 0)
				return false;
			row = empty_row;
			col = empty_col - 1;
		} else {
			if (empty_col == 3)
				return false;
			row = empty_row;
			col = empty_col + 1;
		}
		board[empty_row][empty_col] = board[row][col];
		empty_row = row;
		empty_col = col;
		board[empty_row][empty_col] = null;
		return true;
	}
}
