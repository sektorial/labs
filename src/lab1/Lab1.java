package lab1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Lab1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Number Threads Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new NumberPanel());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

class NumberPanel extends JPanel {
    private final List<NumberProducer> producers = new ArrayList<>();

    public NumberPanel() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Лівий клік — додаємо новий потік
                    NumberProducer p = new NumberProducer(e.getX(), e.getY(), NumberPanel.this);
                    producers.add(p);
                    p.start();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    // Правий клік — зупиняємо найближчий
                    if (!producers.isEmpty()) {
                        NumberProducer closest = null;
                        double minDist = Double.MAX_VALUE;
                        for (NumberProducer p : producers) {
                            double dx = p.getX() - e.getX();
                            double dy = p.getY() - e.getY();
                            double d = Math.hypot(dx, dy);
                            if (d < minDist) {
                                minDist = d;
                                closest = p;
                            }
                        }
                        if (closest != null) {
                            closest.stopRunning();
                            producers.remove(closest);
                            // ось тут — оновлюємо панель, щоб стерти останню цифру
                            repaint();
                        }
                    }
                }

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Малюємо поточні числа всіх Producer-ів
        g.setColor(Color.BLACK);
        for (NumberProducer p : producers) {
            g.drawString(String.valueOf(p.getCurrent()), p.getX(), p.getY());
        }
    }
}

class NumberProducer extends Thread {
    private final int x, y;
    private final NumberPanel panel;
    private volatile boolean running = true;
    private int current = 0;

    public NumberProducer(int x, int y, NumberPanel panel) {
        this.x = x;
        this.y = y;
        this.panel = panel;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCurrent() {
        return current;
    }

    // Зупинити потік
    public void stopRunning() {
        running = false;
        interrupt();
    }

    @Override
    public void run() {
        try {
            while (running) {
                current++;
                // Оновлюємо UI
                SwingUtilities.invokeLater(panel::repaint);
                Thread.sleep(500);
            }
        } catch (InterruptedException ignored) {
        }
    }
}