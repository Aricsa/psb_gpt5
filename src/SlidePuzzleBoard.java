import java.util.*;

public class SlidePuzzleBoard {

    private PuzzlePiece[][] board;
    // 빈칸의 좌표
    private int empty_row;
    private int empty_col;
    private boolean on = false;
    // representation invariant: board[empty_row][empty_col] == null

    /** Constructor - SlidePuzzleBoard 초기 퍼즐 보드 설정 - 감소하는 순으로 나열
     *  */
    public SlidePuzzleBoard() {
        // 4 x 4 보드 만들기
        board = new PuzzlePiece[4][4];
        // 퍼즐 조각 1~15를 보드에 역순으로 끼우기
        int number = 1;
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++) {
                board[row][col] = new PuzzlePiece(number);
                number += 1;
            }
        board[3][3] = null;
        empty_row = 3;
        empty_col = 3;
    }

    /** getPuzzlePiece - 퍼즐 조각을 리턴
     * @param row - 가로줄 인덱스
     * @param col - 세로줄 인덱스
     * @return 퍼즐 조각  */
    public PuzzlePiece getPuzzlePiece(int row, int col) {
        return board[row][col];
    }

    /** 이동이 가능하면, 퍼즐 조각을 빈칸으로 이동
     * @param w - 이동하기 원하는 퍼즐 조각의 번호
     * @return 이동 성공하면 true를 리턴하고, 이동이 불가능하면 false를 리턴 */
    public boolean move(int w) {
        int row, col; // w의 위치
        // 빈칸에 주변에서 w의 위치를 찾음
        if (found(w, empty_row - 1, empty_col)) {
            row = empty_row - 1;
            col = empty_col;
        }
        else if (found(w, empty_row + 1, empty_col)) {
            row = empty_row + 1;
            col = empty_col;
        }
        else if (found(w, empty_row, empty_col - 1)) {
            row = empty_row;
            col = empty_col - 1;
        }
        else if (found(w, empty_row, empty_col + 1)) {
            row = empty_row;
            col = empty_col + 1;
        }
        else
            return false;
        // w를 빈칸에 복사
        board[empty_row][empty_col] = board[row][col];
        // 빈칸 위치를 새로 설정하고, w를 제거
        empty_row = row;
        empty_col = col;
        board[empty_row][empty_col] = null;
        return true;
    }

    /** found - board[row][col]에 퍼즐 조각 v가 있는지 확인  */
    private boolean found(int v, int row, int col) {
        if (row >= 0 && row <= 3 && col >= 0 && col <= 3)
            return board[row][col].faceValue() == v;
        else
            return false;
    }

    /** createPuzzleBoard - 퍼즐 게임 초기 보드 생성 */
    public void createPuzzleBoard() {
        int[] numbers = generateRandomPermutation(15);
        int i = 0;
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++) {
                if (row != 3 || col != 3) {
                    board[row][col] = new PuzzlePiece(numbers[i]+1);
                    i += 1;
                }
                else {
                    board[row][col] = null;
                    empty_row = 3;
                    empty_col = 3;
                }

            }
        on = true;
    }

    public boolean on() {
        return on;
    }

    public boolean gameOver() {
        if (empty_row != 3 || empty_col != 3)
            return false;
        else {
            int number = 1;
            for (int row = 0; row < 4; row++)
                for (int col = 0; col < 4; col++) {
                    if (row != 3 || col != 3) {
                        if (board[row][col].faceValue() != number)
                            return false;
                        else
                            number += 1;
                    }
                }
            on = false;
            return true;
        }
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
}