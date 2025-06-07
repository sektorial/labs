package lab5;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Lab5_Client extends JFrame {
    private static final int SIZE = 50;
    private int x, y;

    public Lab5_Client() {
        super("Client Display");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        x = (getWidth() - SIZE) / 2;
        y = (getHeight() - SIZE) / 2;
        setVisible(true);

        new Thread(() -> {
            try {
                Socket sock = new Socket("localhost", 6000);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(sock.getInputStream())
                );

                String line;
                while ((line = in.readLine()) != null) {
                    String[] parts = line.split(":");
                    String dir = parts[0];
                    int dist = Integer.parseInt(parts[1]);
                    SwingUtilities.invokeLater(() -> {
                        switch (dir) {
                            case "UP":
                                y -= dist;
                                break;
                            case "DOWN":
                                y += dist;
                                break;
                            case "LEFT":
                                x -= dist;
                                break;
                            case "RIGHT":
                                x += dist;
                                break;
                        }
                        repaint();
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, "Client-Thread").start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Lab5_Client::new);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
        g.fillRect(x, y, SIZE, SIZE);
    }
}
