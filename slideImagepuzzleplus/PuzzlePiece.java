package slidepuzzleplus;

import javax.swing.*;
import java.awt.*;

public class PuzzlePiece extends JLabel {
	private int faceValue;
	private Image image;
	private ImageIcon imageIcon;


	// 생성자 메서드를 사용하여 퍼즐 조각의 초기 값을 설정합니다.
	public PuzzlePiece(int value) {
		faceValue = value;
	}
	public int getFaceValue() {
		return faceValue;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
}