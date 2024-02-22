package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingPanel {
    private static DrawingPanel INSTANCE = null;
    private JPanel panel;
    private int prevX, prevY;
    private final int GRID_SIZE = 40; // Size of the grid squares


    private DrawingPanel() {
        // Private constructor to prevent instantiation
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Set the color to light blue with partial transparency
                Color gridColor = new Color(173, 216, 230, 191); // Light blue with 50% transparency
                g2d.setColor(gridColor);

                // Draw grid lines
                for (int x = 0; x < getWidth(); x += GRID_SIZE) {
                    g2d.drawLine(x, 0, x, getHeight());
                }
                for (int y = 0; y < getHeight(); y += GRID_SIZE) {
                    g2d.drawLine(0, y, getWidth(), y);
                }

                g2d.dispose();
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
        if (INSTANCE == null) {
            INSTANCE = new DrawingPanel();
        }
        return INSTANCE;
    }

    public JPanel getPanel() {
        return panel;
    }

    // Add any additional methods for drawing functionality as needed
}
