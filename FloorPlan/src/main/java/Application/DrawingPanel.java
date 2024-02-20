package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingPanel {
    private static DrawingPanel instance = null;
    private JPanel panel;
    private int prevX, prevY;

    private DrawingPanel() {
        // Private constructor to prevent instantiation
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw a line from previous position to current position
                g.setColor(Color.BLACK);
                g.drawLine(prevX, prevY, MouseInfo.getPointerInfo().getLocation().x - panel.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y - panel.getLocationOnScreen().y);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    prevX = e.getX();
                    prevY = e.getY();
                    panel.repaint();
                }
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    prevX = e.getX();
                    prevY = e.getY();
                    panel.repaint();
                }
            }
        });

        // Additional initialization for the panel can be done here
    }

    public static DrawingPanel getInstance() {
        if (instance == null) {
            instance = new DrawingPanel();
        }
        return instance;
    }

    public JPanel getPanel() {
        return panel;
    }

    // Add any additional methods for drawing functionality as needed
}
