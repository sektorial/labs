package lab1;

import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Task1_1_6_2 extends JFrame {

    private final DefaultListModel<String> listModel;
    private MyThread mt;
    private Thread[] newThrd;

    public Task1_1_6_2() {
        super("Thread Demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton button1 = new JButton("Start");
        listModel = new DefaultListModel<>();
        JList<String> listBox1 = new JList<>(listModel);
        setLayout(new BorderLayout());
        add(button1, BorderLayout.NORTH);
        add(new JScrollPane(listBox1), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        button1.addActionListener(e -> {
            listModel.clear();
            mt = new MyThread(this);
            newThrd = new Thread[3];
            for (int i = 0; i < 3; i++) {
                final int idx = i;
                newThrd[i] = new Thread(() -> mt.run(idx));
            }
            listModel.addElement("main Count = " + mt.Count);
            for (Thread th : newThrd) {
                th.start();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
            }
            for (int i = 0; i < 3; i++) {
                Thread t = newThrd[i];
                if (!t.isAlive()) {
                    listModel.addElement("main Count=" + mt.Count);
                } else {
                    listModel.addElement("potok " + i + " - " + t.getState());
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Task1_1_6_2::new);
    }

    void addListItem(String item) {
        listModel.addElement(item);
    }

    static class MyThread {
        private final Task1_1_6_2 ff;
        public int Count;

        public MyThread(Task1_1_6_2 f1) {
            ff = f1;
        }

        public void run(int n) {
            SwingUtilities.invokeLater(() -> ff.addListItem("Thread " + n + " is started."));
            do {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ignored) {
                }
                int value = Count + n * 100;
                SwingUtilities.invokeLater(() -> ff.addListItem(String.valueOf(value)));
                Count++;
            } while (Count <= 10);
            SwingUtilities.invokeLater(() -> ff.addListItem("Thread " + n + " is finished."));
        }
    }
}
