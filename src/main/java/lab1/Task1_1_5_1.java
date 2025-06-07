package lab1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Task1_1_5_1 extends JFrame {

    private final JTextField textFieldA;
    private final JTextField textFieldB;
    private final JTextField textFieldResult;

    public Task1_1_5_1() {
        super("Sum Calculator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textFieldA = new JTextField(8);
        textFieldB = new JTextField(8);
        textFieldResult = new JTextField(8);
        textFieldResult.setEditable(false);
        JButton computeButton = new JButton("Compute Sum");

        computeButton.addActionListener(e -> {
            Data temp = new Data();
            temp.a = Integer.parseInt(textFieldA.getText());
            temp.b = Integer.parseInt(textFieldB.getText());
            new Thread(() -> raschet(temp)).start();
        });

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("A:"));
        panel.add(textFieldA);
        panel.add(new JLabel("B:"));
        panel.add(textFieldB);
        panel.add(computeButton);
        panel.add(textFieldResult);

        getContentPane().add(panel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Task1_1_5_1::new);
    }

    private void raschet(Data input) {
        int res = input.a + input.b;
        SwingUtilities.invokeLater(() ->
                textFieldResult.setText(Integer.toString(res))
        );
    }

    private static class Data {
        int a;
        int b;
    }

}
