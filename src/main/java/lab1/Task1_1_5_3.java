package lab1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Task1_1_5_3 extends JFrame {

    private final JTextField textBox1;
    private final List<JLabel> labels;
    private final List<JList<Integer>> dynamicLists;
    private int x;

    public Task1_1_5_3() {
        super("Dynamic Controls Demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        textBox1 = new JTextField("3");
        textBox1.setBounds(20, 20, 50, 25);
        add(textBox1);

        JButton button1 = new JButton("Add Lists");
        button1.setBounds(90, 20, 120, 25);
        add(button1);

        JButton button2 = new JButton("Remove Lists");
        button2.setBounds(220, 20, 140, 25);
        add(button2);

        JLabel label1 = new JLabel("Label 1");
        label1.setBounds(20, 60, 80, 25);
        JLabel label2 = new JLabel("Label 2");
        label2.setBounds(20, 90, 80, 25);
        JLabel label3 = new JLabel("Label 3");
        label3.setBounds(20, 120, 80, 25);

        add(label1);
        add(label2);
        add(label3);

        labels = List.of(label1, label2, label3);
        dynamicLists = new ArrayList<>();

        button1.addActionListener(e -> {
            dynamicLists.clear();
            try {
                x = Integer.parseInt(textBox1.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter an integer");
                return;
            }

            Random r = new Random();

            for (int i = 0; i < x; i++) {
                DefaultListModel<Integer> model = new DefaultListModel<>();
                model.addElement(i);

                JList<Integer> lb = new JList<>(model);
                lb.setVisible(true);
                lb.setBackground(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));

                lb.setBounds(200 + i * 150, 60 + i * 30, 100, 60);

                add(lb);
                dynamicLists.add(lb);
            }

            for (JLabel lbl : labels) {
                lbl.setVisible(true);
            }

            revalidate();
            repaint();
        });

        button2.addActionListener(e -> {
            for (JList<Integer> lb : new ArrayList<>(dynamicLists)) {
                remove(lb);
            }
            dynamicLists.clear();

            for (JLabel lbl : labels) {
                lbl.setVisible(false);
            }

            revalidate();
            repaint();
        });

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Task1_1_5_3::new);
    }
}
