package lab1;

import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Task1_1_5_2 extends JFrame {
    private final JLabel label1;
    private final DefaultListModel<Integer> listModel;
    private final JList<Integer> listBox1;
    private final JButton button1;

    public Task1_1_5_2() {
        super("Forms Thread Demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        button1 = new JButton("Start Threads");
        label1 = new JLabel("0");
        listModel = new DefaultListModel<>();
        listBox1 = new JList<>(listModel);

        JPanel top = new JPanel();
        top.add(button1);
        top.add(label1);

        getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(listBox1), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        button1.addActionListener(e -> {
            int CountThreads = 4;
            Thread[] threads = new Thread[CountThreads];
            for (int p = 1; p < CountThreads; p++) {
                final int data = p * 100;
                threads[p] = new Thread(() -> doSomething(data));
                threads[p].start();
            }
        });

        for (int i = 0; i < 2; i++) {
            final int data = i * 100;
            new Thread(() -> doSomething(data)).start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Task1_1_5_2::new);
    }

    private void doSomething(int data) {
        int i = data;
        for (int j = 0; j < 3; j++) {
            final int value = i;
            SwingUtilities.invokeLater(() -> label1.setText(Integer.toString(value)));
            SwingUtilities.invokeLater(() -> listModel.addElement(value));
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            i++;
        }
    }
}
