package lab5;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Lab5_Server extends JFrame {
    private final JTextField distanceBox;
    private PrintWriter out;

    public Lab5_Server() {
        super("Server Control");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        distanceBox = new JTextField("50", 5);
        JButton up = new JButton("Up");
        JButton down = new JButton("Down");
        JButton left = new JButton("Left");
        JButton right = new JButton("Right");

        add(new JLabel("Distance:"));
        add(distanceBox);
        add(up);
        add(down);
        add(left);
        add(right);

        ActionListener sendDir = e -> {
            String dir = ((JButton) e.getSource()).getText().toUpperCase();
            String msg = dir + ":" + distanceBox.getText().trim();
            System.out.println("[SERVER] Clicked → " + msg);
            if (out != null) {
                out.println(msg);
                System.out.println("[SERVER] Sent → " + msg);
            } else {
                System.out.println("[SERVER] OUT is null, no client yet");
            }
        };
        up.addActionListener(sendDir);
        down.addActionListener(sendDir);
        left.addActionListener(sendDir);
        right.addActionListener(sendDir);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        new Thread(() -> {
            try {
                ServerSocket server = new ServerSocket(6000);
                Socket client = server.accept();
                out = new PrintWriter(client.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "Server-Thread").start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Lab5_Server();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
