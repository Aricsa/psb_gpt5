import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class PuzzleFrame extends JFrame {

    private SlidePuzzleBoard board;
    private PuzzleButton[][] button_board;

    private JLabel l;
    public PuzzleFrame(SlidePuzzleBoard b) {
        board = b;
        button_board = new PuzzleButton[4][4];
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        JPanel p1 = new JPanel(new FlowLayout());
        l = new JLabel("00:00:00");
        p1.add(new StartButton(board,this, l));
        p1.add(l);
        JPanel p2 = new JPanel(new GridLayout(4,4));
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++) {
                button_board[row][col] = new PuzzleButton(board,this);
                p2.add(button_board[row][col]);
            }
        cp.add(p1,BorderLayout.NORTH);
        cp.add(p2,BorderLayout.CENTER);
        update();
        setTitle("Slide Puzzle");
        setSize(250,300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void update() {
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++) {
                PuzzlePiece pp = board.getPuzzlePiece(row, col);
                if (pp != null) {
                    String n = Integer.toString(pp.faceValue());
                    button_board[row][col].setText(n);
                }
                else
                    button_board[row][col].setText("");
            }
    }

    public void finish() {
        button_board[3][3].setText("Done");
        JOptionPane.showMessageDialog(null, "소요시간 : " + l.getText());
    }
}