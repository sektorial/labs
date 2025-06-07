package lab1;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Task1_1_6_3 extends JFrame {
    private final JLabel lb;

    public Task1_1_6_3() {
        super("Live Counter");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        lb = new JLabel("0", SwingConstants.CENTER);
        add(lb, BorderLayout.CENTER);
        setSize(200, 100);
        setLocationRelativeTo(null);
        setVisible(true);
        Thread thread = new Thread(this::work);
        thread.setDaemon(true);
        thread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Task1_1_6_3::new);
    }

    private void work() {
        int counter = 0;
        while (true) {
            counter++;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            String text = Integer.toString(counter);
            SwingUtilities.invokeLater(() -> lb.setText(text));
        }
    }
}
