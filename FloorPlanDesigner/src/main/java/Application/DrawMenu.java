package Application;
import java.awt.event.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class DrawMenu {
	private static DrawMenu INSTANCE;
	private JMenu drawMenu;
    private Color drawColor;
	
	private DrawMenu(){
        // Create Wall option
		drawMenu = new JMenu("Draw");
        JMenuItem wallItem = new JMenuItem("Wall");
        wallItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ToolBox.getInstance().setToolBoxLabel("Drawing walls...");
                drawColor = Color.BLACK;
                removePriorMouseListeners();
                setMouseListeners();
            }
        });
        
        // Create Door option
        JMenuItem doorItem = new JMenuItem("Door");
        doorItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ToolBox.getInstance().setToolBoxLabel("Drawing doors...");
                drawColor = new Color(139, 69, 19);
                removePriorMouseListeners();
                setMouseListeners();
            }
        });
        
        // Create Window option
        JMenuItem windowItem = new JMenuItem("Window");
        windowItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ToolBox.getInstance().setToolBoxLabel("Drawing windows...");
                drawColor = Color.BLUE;
                removePriorMouseListeners();
                setMouseListeners();
            }
        });
        
        // Add save and load items to File menu
        drawMenu.add(wallItem);
        drawMenu.add(doorItem);
        drawMenu.add(windowItem);
	}
    public static DrawMenu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DrawMenu();
        }
        return INSTANCE;
    }
    private void removePriorMouseListeners(){
        JPanel panel =  DrawingPanel.getInstance().getPanel();
        for (MouseListener listener : panel.getMouseListeners()) {
            panel.removeMouseListener(listener);
        }
        for (MouseMotionListener listener : panel.getMouseMotionListeners()) {
            panel.removeMouseMotionListener(listener);
        }
    }

    private void setMouseListeners(){
        int gridSize = DrawingPanel.getInstance().getGRID_SIZE();
        JPanel panel =  DrawingPanel.getInstance().getPanel();
        List <DrawingPanelSegment> segmentsList= DrawingPanel.getInstance().drawingPanelSegments;
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int startX = (e.getX() / gridSize) * gridSize;// Align with grid
                int startY = (e.getY() / gridSize) * gridSize;// Align with grid

                DrawingPanelSegment toDrawSegment = new DrawingPanelSegment(drawColor);
                toDrawSegment.addPoint(new Point(startX, startY)); // Add starting point
                segmentsList.add(toDrawSegment);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int endX = (e.getX() / gridSize) * gridSize;// Align with grid
                int endY = (e.getY() / gridSize) * gridSize;

                DrawingPanelSegment toDrawSegment = segmentsList.get(segmentsList.size() - 1);
                Point startPoint = toDrawSegment.getStartingPoint(); // Get the starting point

                // Calculate the absolute differences between start and end points
                int dx = Math.abs(endX - startPoint.x);
                int dy = Math.abs(endY - startPoint.y);

                // Adjust the end point to ensure the line is purely horizontal or vertical
                if (dx == dy) {
                    ;
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


        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    DrawingPanelSegment segment = segmentsList.get(segmentsList.size() - 1);
                    Point startPoint = segment.getStartingPoint(); // Get the starting point of the segment

                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    int newX = (mouseX / gridSize) * gridSize;
                    int newY = (mouseY / gridSize) * gridSize;

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
                    if (mouseX % gridSize == 0 || mouseY % gridSize == 0)
                        panel.repaint();

                    // Draw the new temporary line segment in gray
                    g2d.setColor(Color.GRAY);
                    g2d.drawLine(startPoint.x, startPoint.y, newX, newY);
                }
            }
        });
    }


    public JMenu getMenu() {
        return drawMenu;
    }
	
}
