package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DrawingPanel {
    private static DrawingPanel INSTANCE = null;
    private boolean RoomDrawing = false;
    private JPanel panel;
    private int startX, startY;
    private final int GRID_SIZE = 20; // Size of the grid squares
    private Color drawColor;
    private boolean canDraw;
    private List<DrawingPanelSegment> drawingPanelSegments = new ArrayList<>();
    private List<DrawingPanelRoom> drawingPanelRooms = new ArrayList<>();

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

                if (!drawingPanelSegments.isEmpty()){
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

                if(!drawingPanelRooms.isEmpty()){
                    for (DrawingPanelRoom room : drawingPanelRooms) {

                        g2d.setColor(Color.BLACK);
                        if (room.getWidth() > 0 && room.getWidth() > 0)
                            g2d.drawRect(room.getStartingPoint().x,room.getStartingPoint().y, room.getWidth(), room.getHeight());
                        else
                            g2d.drawRect(room.getEndingPoint().x,room.getEndingPoint().y, Math.abs(room.getWidth()), Math.abs(room.getHeight()));


                    }
                }

            }
        };



        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (canDraw && RoomDrawing) {
                        int startX = (e.getX() / GRID_SIZE) * GRID_SIZE;// Align with grid
                        int startY = (e.getY() / GRID_SIZE) * GRID_SIZE;// Align with grid
                        DrawingPanelRoom currentRoom = new DrawingPanelRoom(Color.BLACK);
                        currentRoom.setStartingPoint(new Point(startX,startY));
                        drawingPanelRooms.add(currentRoom);
                    }
                    else if (canDraw){
                        int startX = (e.getX() / GRID_SIZE) * GRID_SIZE;// Align with grid
                        int startY = (e.getY() / GRID_SIZE) * GRID_SIZE;// Align with grid

                        DrawingPanelSegment toDrawSegment = new DrawingPanelSegment(drawColor);
                        toDrawSegment.addPoint(new Point(startX, startY)); // Add starting point
                        drawingPanelSegments.add(toDrawSegment);
                    }
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu popupMenu = new JPopupMenu();

                    JMenuItem clearMenuItem = new JMenuItem("Clear All");
                    clearMenuItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Clear the drawing panel
                            drawingPanelSegments.clear();
                            drawingPanelRooms.clear();
                            panel.repaint(); // Repaint the panel to reflect changes
                        }
                    });


                    popupMenu.add(clearMenuItem);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (canDraw && RoomDrawing){
                        int endX = (e.getX() / GRID_SIZE) * GRID_SIZE;// Align with grid
                        int endY = (e.getY() / GRID_SIZE) * GRID_SIZE;
                        DrawingPanelRoom currentRoom = drawingPanelRooms.get(drawingPanelRooms.size() - 1);
                        currentRoom.setEndingPoint(new Point(endX,endY));
                        panel.repaint();
                    }
                    else if (canDraw) {
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

                if (canDraw && RoomDrawing){
                    DrawingPanelRoom room = drawingPanelRooms.get(drawingPanelRooms.size() - 1);
                    Point startPoint = room.getStartingPoint(); // Get the starting point of the segment
                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    int alignedStartX = (startPoint.x / GRID_SIZE) * GRID_SIZE;
                    int alignedStartY = (startPoint.y / GRID_SIZE) * GRID_SIZE;
                    int alignedMouseX = (mouseX / GRID_SIZE) * GRID_SIZE;
                    int alignedMouseY = (mouseY / GRID_SIZE) * GRID_SIZE;
                    room.setEndingPoint(new Point(alignedMouseX,alignedMouseY));

                    int topLeftX = Math.min(alignedStartX, alignedMouseX);
                    int topLeftY = Math.min(alignedStartY, alignedMouseY);
                    int bottomRightX = Math.max(alignedStartX, alignedMouseX);
                    int bottomRightY = Math.max(alignedStartY, alignedMouseY);

                    // Calculate the width and height of the box, ensuring they are multiples of GRID_SIZE
                    int width = ((bottomRightX - topLeftX) / GRID_SIZE) * GRID_SIZE;
                    int height = ((bottomRightY - topLeftY) / GRID_SIZE) * GRID_SIZE;

                    // Draw the outline of the box
                    Graphics2D g2d = (Graphics2D) panel.getGraphics();
                    g2d.setStroke(new BasicStroke(2)); // Set line thickness

                    // Clear the previous temporary box by repainting the panel
                    if (mouseX % GRID_SIZE == 0 || mouseY % GRID_SIZE == 0)
                        panel.repaint();

                    // Draw the new temporary box aligned with the grid lines
                    g2d.setColor(Color.GRAY);
                    g2d.drawRect(topLeftX, topLeftY, width, height);

                }
                else if (canDraw) {
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

    public void setRoomDrawBoolean(boolean value){
        RoomDrawing = value;
    }



    // Add any additional methods for drawing functionality as needed
}