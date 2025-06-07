package lab1;

import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Task1_1_6_1 extends JFrame {

    private final DefaultListModel<String> listModel;
    private final JList<String> listBox1;
    private AddListItem myDelegate;

    public Task1_1_6_1() {
        super("Threaded List Adder");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton button1 = new JButton("Start");
        listModel = new DefaultListModel<>();
        listBox1 = new JList<>(listModel);
        setLayout(new BorderLayout());
        add(button1, BorderLayout.NORTH);
        add(new JScrollPane(listBox1), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        button1.addActionListener(e -> {
            Class1 cc = new Class1(this);
            myDelegate = this::addListItemMethod;
            int CountThreads = 4;
            Thread[] Threads = new Thread[CountThreads + 1];
            for (int p = 1; p <= CountThreads; p++) {
                int data = p * 100;
                Threads[p] = new Thread(() -> cc.doSomething(data));
                Threads[p].start();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Task1_1_6_1::new);
    }

    private void addListItemMethod(int x) {
        try {
            Thread.sleep(600 - x);
        } catch (InterruptedException ignored) {
        }
        for (int i = 1; i < 6; i++) {
            String myItem = "MyListItem" + (x + i);
            listModel.addElement(myItem);
            listBox1.updateUI();
            try {
                Thread.sleep(600 - x);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public interface AddListItem {
        void add(int x);
    }

    static class Class1 {
        private final Task1_1_6_1 f1;

        public Class1(Task1_1_6_1 x) {
            f1 = x;
        }

        public void doSomething(int data) {
            SwingUtilities.invokeLater(() -> f1.myDelegate.add(data));
        }
    }
}
