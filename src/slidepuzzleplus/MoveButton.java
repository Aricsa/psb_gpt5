package slidepuzzleplus;

import java.awt.event.*;
import javax.swing.*;

//↑↓←→
public class MoveButton extends JButton implements ActionListener {
    private SlidePuzzleBoard board;
    private PuzzleFrame frame;
    private int type;
    public MoveButton(SlidePuzzleBoard b, PuzzleFrame f, int t, String dir) {
        super(dir);
        board = b;
        frame = f;
        type = t;
        addActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {
        if (board.gameOn()) {
            if (board.newmove(type)) {
                frame.update();
                if (board.gameOver())
                    frame.finish();
                else if (board.gameFail()){
                    frame.fail();
                }
            }
        }
    }
}
