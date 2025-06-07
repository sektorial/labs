package lab1;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Task1_1_6_4 extends JFrame {
    private final int n = 3;
    private final JLabel[] lb;

    public Task1_1_6_4() {
        super("Multi Counter");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        lb = new JLabel[n + 1];
        for (int i = 0; i <= n; i++) {
            lb[i] = new JLabel("0");
            lb[i].setBounds(i * 150, 10, 120, 30);
            add(lb[i]);
        }
        for (int i = 0; i < n; i++) {
            int x = i;
            Thread t = new Thread(() -> work(x));
            t.setDaemon(true);
            t.start();
        }
        Thread mm = new Thread(this::mainWork);
        mm.setDaemon(true);
        mm.start();
        setSize((n + 1) * 150, 100);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Task1_1_6_4::new);
    }

    private void work(int x) {
        int counter = 0;
        while (true) {
            counter++;
            try {
                Thread.sleep(1000 + 300 * x);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            final int finalCounter = counter;
            SwingUtilities.invokeLater(() -> lb[x].setText(String.valueOf(finalCounter)));
        }
    }

    private void mainWork() {
        int mainCounter = 0;
        while (true) {
            mainCounter++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            String text = "Main " + mainCounter;
            SwingUtilities.invokeLater(() -> lb[n].setText(text));
        }
    }
}
