package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DrawingPanel {
    private static DrawingPanel INSTANCE = null;
    private JPanel panel;
    private int startX, startY;
    private final int GRID_SIZE = 30; // Size of the grid squares
    private Color drawColor;
    private boolean canDraw;
    private List<DrawingPanelSegment> drawingPanelSegments = new ArrayList<>();

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

                for (DrawingPanelSegment segment : drawingPanelSegments) {

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
                    if (canDraw) {
                        int startX = (e.getX() / GRID_SIZE) * GRID_SIZE;// Align with grid
                        int startY = (e.getY() / GRID_SIZE) * GRID_SIZE; // Align with grid

                        DrawingPanelSegment toDrawSegment = new DrawingPanelSegment(drawColor);
                        toDrawSegment.addPoint(new Point(startX, startY)); // Add starting point
                        drawingPanelSegments.add(toDrawSegment);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (canDraw) {
                        int endX = (e.getX() / GRID_SIZE) * GRID_SIZE;// Align with grid
                        int endY = (e.getY() / GRID_SIZE) * GRID_SIZE;

                        DrawingPanelSegment toDrawSegment = drawingPanelSegments.get(drawingPanelSegments.size() - 1);
                        Point startPoint = toDrawSegment.getStartingPoint(); // Get the starting point

                        // Calculate the absolute differences between start and end points
                        int dx = Math.abs(endX - startPoint.x);
                        int dy = Math.abs(endY - startPoint.y);

                        // Adjust the end point to ensure the line is purely horizontal or vertical
                        if (dx == dy) {

                        } else if (dx > dy) {
                            // Horizontal line: keep Y coordinate
                            endY = startPoint.y;
                        } else {
                            // Vertical line: keep X coordinate
                            endX = startPoint.x;
                        }

                        toDrawSegment.addPoint(new Point(endX, endY));

                        panel.repaint();
                    }
                }
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (canDraw) {
                    DrawingPanelSegment segment = drawingPanelSegments.get(drawingPanelSegments.size() - 1);
                    Point startPoint = segment.getStartingPoint(); // Get the starting point of the segment

                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    int newX = (mouseX / GRID_SIZE) * GRID_SIZE;
                    int newY = (mouseY / GRID_SIZE) * GRID_SIZE;

                    int dx = Math.abs(newX - startPoint.x);
                    int dy = Math.abs(newY - startPoint.y);

                    if (dx == dy) {
                        // Diagonal line: keep both coordinates
                    } else if (dx > dy) {
                        // Horizontal line: keep Y coordinate
                        newY = startPoint.y;
                    } else {
                        // Vertical line: keep X coordinate
                        newX = startPoint.x;
                    }


                    Graphics2D g2d = (Graphics2D) panel.getGraphics();
                    g2d.setStroke(new BasicStroke(2)); // Set line thickness

                    // Clear the previous temporary line by repainting the panel
                    if (mouseX % GRID_SIZE == 0 || mouseY % GRID_SIZE == 0)
                        panel.repaint();

                    // Draw the new temporary line segment in gray
                    g2d.setColor(Color.GRAY);
                    g2d.drawLine(startPoint.x, startPoint.y, newX, newY);
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

    public void setColor(Color color) {
        this.drawColor = color;
    }

    public void canDraw(boolean canDrawFlag){
        canDraw = canDrawFlag;
    }

    public JPanel getPanel() {
        return panel;
    }



    // Add any additional methods for drawing functionality as needed
}
