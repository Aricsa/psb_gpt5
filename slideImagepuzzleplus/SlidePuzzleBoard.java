package slidepuzzleplus;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/** SlidePuzzleBoard models a slide puzzle. */
public class SlidePuzzleBoard {
	private PuzzlePiece[][] board;
	
	private int empty_row;
	private int empty_col;
	private boolean game_on;

	public SlidePuzzleBoard() {
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
	public void setPhoto(int row, int col, String s) {
		PuzzlePiece piece = board[row][col];
		if (piece != null) {
			int faceValue = piece.getFaceValue();
			String directoryPath = "images/Hanyangi_4x4/";
			String fileName = String.format("Hanyangi_%02d.png", faceValue);
			String photoPath = directoryPath + fileName;

			ImageIcon icon = new ImageIcon(photoPath);
			Image image = icon.getImage();

			piece.setImage(image);
		} else {
			System.out.println("Warning: PuzzlePiece is null at position (" + row + ", " + col + ")");
		}
	}


	public PuzzlePiece getPuzzlePiece(int row, int col) {
		return board[row][col];
	}


	public boolean gameOn() {
		return game_on;
	}


	public boolean move(int row, int col) {
		// Check if the selected piece is adjacent to the empty space
		if ((row == empty_row && Math.abs(col - empty_col) == 1) ||
				(col == empty_col && Math.abs(row - empty_row) == 1)) {

			// Swap the selected piece with the empty space
			board[empty_row][empty_col] = board[row][col];
			board[row][col] = null;

			// Update the empty space coordinates
			empty_row = row;
			empty_col = col;

			return true;
		}

		return false; // Movement is not possible
	}

	/** found - board[row][col]에 퍼즐 조각 v가 있는지 확인
	 * @param v - 확인할 수
	 * @param row - 보드의 가로줄 인덱스
	 * @param col - 보드의 세로줄 인덱스
	 * @return 있으면 true, 없으면 false */
	private boolean found(int v, int row, int col) {
		if (row >= 0 && row <= 3 && col >= 0 && col <= 3)
			return board[row][col].getFaceValue() == v;
	    else
	    	return false;
	}

	/** createPuzzleBoard - 퍼즐 게임 초기 보드 생성 */
	public void createPuzzleBoardWithPhotos() {
		int[] numbers = generateRandomPermutation(15);
		int i = 0;
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (col != 3 || row != 3) {
					board[row][col] = new PuzzlePiece(numbers[i] + 1);
					setPhoto(row, col, "images/Hanyangi_4x4/" + numbers[i] + ".png");
					i += 1;
				} else {
					board[3][3] = null;
					empty_row = 3;
					empty_col = 3;
				}
			}
		}
		game_on = true;
	}

	/** generateRandomPermutation - 0~n-1 범위의 정수 수열을 무작위로 섞은 배열을 리턴 한다.
	 * @param n - 수열의 길이
	 * @return 0~n-1 범위의 정수를 무작위로 섞어 만든 배열
	 */
	private int[] generateRandomPermutation(int n) {
		Random random = new Random();
	    int[] permutation = new int[n];
	    for (int i = 0; i < n; i++) {
	        int d = random.nextInt(i+1);
	        permutation[i] = permutation[d];
	        permutation[d] = i;
	    }
	    return permutation;
	}

	/** gameOver - 퍼즐 게임이 끝났는지를 확인
	 * @return 목표를 달성했으면 true, 아직 더 진행해야 하면 false
	 */
	public boolean gameOver() {
		if (empty_row != 3 || empty_col != 3) {
			return false;
		} else {
			int number = 1;
			for (int row = 0; row < 4; row++) {
				for (int col = 0; col < 4; col++) {
					if (col != 3 || row != 3) {
						if (board[row][col].getFaceValue() != number) {
							return false;
						} else {
							number += 1;
						}
					}
				}
			}
			game_on = false;
			return true;
		}
	}

}



