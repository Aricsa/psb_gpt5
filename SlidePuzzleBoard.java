package slidepuzzleplus;
import java.util.Random;

public class SlidePuzzleBoard {
	private PuzzlePiece[][] board;

	// 빈칸의 좌표
	private int empty_row;
	private int empty_col;
	private boolean game_on = false;

	public SlidePuzzleBoard(int puzzleSize) {
		board = new PuzzlePiece[puzzleSize][puzzleSize];
		initializeBoard(puzzleSize);
	}

	/** getPuzzlePiece - 현재 퍼즐의 크기를 확인 **/
	 public int getPuzzleSize() {
		return board.length;
	}

	/** getPuzzlePiece - 퍼즐 조각을 리턴
	 * @param row - 가로줄 인덱스
	 * @param col - 세로줄 인덱스
	 * @return 퍼즐 조각  */
	public PuzzlePiece getPuzzlePiece(int row, int col) {
		return board[row][col];
	}

	public boolean gameOn() {
		return game_on;
	}

	public boolean move(int w) {
		int row, col;

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

		board[empty_row][empty_col] = board[row][col];
		empty_row = row;
		empty_col = col;
		board[empty_row][empty_col] = null;
		return true;
	}

	private boolean found(int v, int row, int col) {
		if (row >= 0 && row < board.length && col >= 0 && col < board[0].length)
			return board[row][col].faceValue() == v;
		else
			return false;
	}

	/** createPuzzleBoard -  퍼즐을 생성하고 셔플, 보드의 크기에 따라 1부터 n까지의 숫자로 초기화되며, 마지막 피스는 null로 설정 **/
	public void createPuzzleBoard() {
		int[] numbers = generateRandomPermutation((board.length * board[0].length) - 1);
		int i = 0;
		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board[0].length; col++) {
				if (col != board[0].length - 1 || row != board.length - 1) {
					board[row][col] = new PuzzlePiece(numbers[i] + 1);
					i += 1;
				} else {
					board[board.length - 1][board[0].length - 1] = null;
					empty_row = board.length - 1;
					empty_col = board[0].length - 1;
				}
			}
		game_on = true;
	}

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

	public boolean gameOver() {
		int number = 1;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col] != null && board[row][col].faceValue() != number) {
					return false;
				}
				number++;
			}
		}

		game_on = false;
		return true;
	}

	/** initializeBoard -  퍼즐을 초기화하고 게임을 시작할 때 호출 **/
	private void initializeBoard(int puzzleSize) {
		int number = 1;
		for (int row = 0; row < puzzleSize; row++) {
			for (int col = 0; col < puzzleSize; col++) {
				if (row != puzzleSize - 1 || col != puzzleSize - 1) {
					board[row][col] = new PuzzlePiece(number);
					number++;
				} else {
					board[row][col] = null;
					empty_row = row;
					empty_col = col;
				}
			}
		}
	}
}