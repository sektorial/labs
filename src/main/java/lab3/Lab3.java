package lab3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Lab3 {

    private final DrawPanel panel;
    private final BlockingQueue<Event> leftQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Event> rightQueue = new LinkedBlockingQueue<>();

    public Lab3() {
        JFrame frame = new JFrame("Threaded Color Sync");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new DrawPanel();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getX() < panel.getWidth() / 2) {
                    leftQueue.offer(new Event(EventType.CLICK, null));
                } else {
                    rightQueue.offer(new Event(EventType.CLICK, null));
                }
            }
        });
        frame.add(panel);
        frame.setSize(200, 200);
        frame.setVisible(true);
        startThreads();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Lab3::new);
    }

    private void startThreads() {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    Event ev = leftQueue.take();
                    if (ev.type == EventType.CLICK) {
                        Color c = new Color(
                                (int) (Math.random() * 256),
                                (int) (Math.random() * 256),
                                (int) (Math.random() * 256)
                        );
                        panel.setLeftColor(c);
                        SwingUtilities.invokeLater(panel::repaint);
                        rightQueue.offer(new Event(EventType.SIGNAL, c));
                    }
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        });
        t1.setDaemon(true);
        t1.start();

        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    Event ev = rightQueue.take();
                    if (ev.type == EventType.CLICK) {
                        Color c = new Color(
                                (int) (Math.random() * 256),
                                (int) (Math.random() * 256),
                                (int) (Math.random() * 256)
                        );
                        panel.setRightColor(c);
                        SwingUtilities.invokeLater(panel::repaint);
                    } else if (ev.type == EventType.SIGNAL) {
                        Color c = ev.color;
                        Color comp = new Color(
                                255 - c.getRed(),
                                255 - c.getGreen(),
                                255 - c.getBlue()
                        );
                        panel.setRightColor(comp);
                        SwingUtilities.invokeLater(panel::repaint);
                    }
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        });
        t2.setDaemon(true);
        t2.start();
    }

    private enum EventType {CLICK, SIGNAL}

    private static class Event {
        final EventType type;
        final Color color;

        Event(EventType t, Color c) {
            type = t;
            color = c;
        }
    }

    private static class DrawPanel extends JPanel {
        private Color leftColor = Color.WHITE;
        private Color rightColor = Color.WHITE;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth(), h = getHeight();
            g.setColor(leftColor);
            g.fillRect(0, 0, w / 2, h);
            g.setColor(rightColor);
            g.fillRect(w / 2, 0, w - w / 2, h);
        }

        void setLeftColor(Color c) {
            leftColor = c;
        }

        void setRightColor(Color c) {
            rightColor = c;
        }
    }
}
