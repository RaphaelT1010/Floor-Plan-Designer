package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel {
    private static DrawingPanel INSTANCE = null;
    private JPanel panel;
    private int startX, startY;
    private final int GRID_SIZE = 40; // Size of the grid squares
    private String drawType = "";
    private List<Segment> segments = new ArrayList<>();
    private Color setColorBasedOnDrawType(String drawType){
        switch (drawType) {
            case "Wall":
                return Color.BLACK;
            case "Door":
                return new Color(139, 69, 19);
            case "Window":
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }

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

                g2d.setStroke(new BasicStroke(3)); // Set line thickness

                for (Segment segment : segments) {

                    List<Point> segmentPoints = segment.getPoints(); // Get points of this segment
                    g2d.setColor(segment.getColor());

                    for (int i = 1; i < segmentPoints.size(); i++) {
                        Point prevPoint = segmentPoints.get(i - 1);
                        Point currentPoint = segmentPoints.get(i);
                        g2d.drawLine(prevPoint.x, prevPoint.y, currentPoint.x, currentPoint.y);
                    }
                }
            }
        };



        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int startX = (e.getX()/GRID_SIZE)* GRID_SIZE;// Align with grid
                    int startY = (e.getY()/GRID_SIZE)* GRID_SIZE; // Align with grid

                    Segment toDrawSegment = new Segment(setColorBasedOnDrawType((drawType)));
                    toDrawSegment.addPoint(new Point(startX, startY)); // Add starting point
                    segments.add(toDrawSegment);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {

                    int endX = (e.getX()/GRID_SIZE)* GRID_SIZE;// Align with grid
                    int endY = (e.getY()/GRID_SIZE)* GRID_SIZE;


                    Segment toDrawSegment = segments.get(segments.size() - 1);
                    toDrawSegment.addPoint(new Point(endX, endY));

                    panel.repaint();
                }
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
               Segment segment = segments.get(segments.size() - 1);
               Point startPoint = segment.getStartingPoint(); // Get the starting point of the segment

                int mouseX = e.getX();
                int mouseY = e.getY();

                int newX = (mouseX / GRID_SIZE) * GRID_SIZE;
                int newY = (mouseY / GRID_SIZE) * GRID_SIZE;

                Graphics2D g2d = (Graphics2D) panel.getGraphics();
                g2d.setStroke(new BasicStroke(2)); // Set line thickness

                // Clear the previous temporary line by repainting the panel
                if (mouseX % GRID_SIZE == 0 || mouseY % GRID_SIZE == 0)
                    panel.repaint();

                // Draw the new temporary line segment in gray
                g2d.setColor(Color.GRAY);
                g2d.drawLine(startPoint.x, startPoint.y, newX, newY);

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

    // Method to set drawing type (e.g., "Wall", "Door", "Window")
    public void setDrawType(String type) {
        this.drawType = type;
    }

    public JPanel getPanel() {
        return panel;
    }

    // Add any additional methods for drawing functionality as needed
}
