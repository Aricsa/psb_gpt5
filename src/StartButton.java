import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartButton extends JButton implements ActionListener {

    private SlidePuzzleBoard board;
    private PuzzleFrame frame;
    private Timer timer;
    private int seconds;
    private JLabel timelabel;
    public StartButton(SlidePuzzleBoard b, PuzzleFrame f, JLabel l)
    {
        super("Start");
        board = b;
        frame = f;
        timelabel = l;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        board.createPuzzleBoard();
        frame.update();
        startTimer();
    }

    private void startTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        seconds = 0;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();
    }

    private void updateTime() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, secs);
        timelabel.setText(formattedTime);
        seconds++;
    }
}
