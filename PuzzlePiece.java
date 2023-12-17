package slidepuzzleplus;

import java.awt.*;

/** PuzzlePiece - 슬라이드 게임 퍼즐 조각  */
public class PuzzlePiece {
	private int face_value;
	private Image image;
	public PuzzlePiece(int value) {
		face_value = value;
	}
	public int faceValue() {
		return face_value;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
}
