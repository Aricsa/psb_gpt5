package slidepuzzleplus;
import java.util.Random;

/** SlidePuzzleBoard models a slide puzzle. */
public class SlidePuzzleBoard {
	private PuzzlePiece[][] board;

	private int empty_row;
	private int empty_col;
	private boolean game_on = false;

	public SlidePuzzleBoard(int puzzleSize) {
		board = new PuzzlePiece[puzzleSize][puzzleSize];
		initializeBoard(puzzleSize);
	}

	public int getPuzzleSize() {
		return board.length;
	}

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