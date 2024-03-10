package Application;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class RoomMenu {
    private static JMenuItem INSTANCE;


    private RoomMenu() {
    }

    public static JMenuItem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JMenuItem("Room");
            INSTANCE.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add your logic here when "Room" is clicked
                    ToolBox.getInstance().setToolBoxLabel("Adding rooms...");
                    removePriorMouseListeners();
                    setMouseListeners();

                }

            });

            INSTANCE.setPreferredSize(null); // Resetting preferred size
            INSTANCE.setMaximumSize(new Dimension(INSTANCE.getPreferredSize())); // Adjust width as needed
        }
        return INSTANCE;
    }

    private static void removePriorMouseListeners(){
        JPanel panel =  DrawingPanel.getInstance().getPanel();
        for (MouseListener listener : panel.getMouseListeners()) {
            panel.removeMouseListener(listener);
        }
        for (MouseMotionListener listener : panel.getMouseMotionListeners()) {
            panel.removeMouseMotionListener(listener);
        }
    }

    private static void setMouseListeners(){
        int gridSize = DrawingPanel.getInstance().getGRID_SIZE();
        JPanel panel =  DrawingPanel.getInstance().getPanel();
        List<DrawingPanelRoom> roomsList= DrawingPanel.getInstance().drawingPanelRooms;
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int startX = (e.getX() / gridSize) * gridSize;// Align with grid
                    int startY = (e.getY() / gridSize) * gridSize;// Align with grid
                    DrawingPanelRoom currentRoom = new DrawingPanelRoom(Color.BLACK);
                    currentRoom.setStartingPoint(new Point(startX, startY));
                    roomsList.add(currentRoom);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int endX = (e.getX() / gridSize) * gridSize;// Align with grid
                    int endY = (e.getY() / gridSize) * gridSize;
                    DrawingPanelRoom currentRoom = roomsList.get(roomsList.size() - 1);
                    currentRoom.setEndingPoint(new Point(endX, endY));
                    panel.repaint();
                }
            }


        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    DrawingPanelRoom room = roomsList.get(roomsList.size() - 1);
                    Point startPoint = room.getStartingPoint(); // Get the starting point of the segment
                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    int alignedStartX = (startPoint.x / gridSize) * gridSize;
                    int alignedStartY = (startPoint.y / gridSize) * gridSize;
                    int alignedMouseX = (mouseX / gridSize) * gridSize;
                    int alignedMouseY = (mouseY / gridSize) * gridSize;

                    int topLeftX = Math.min(alignedStartX, alignedMouseX);
                    int topLeftY = Math.min(alignedStartY, alignedMouseY);
                    int bottomRightX = Math.max(alignedStartX, alignedMouseX);
                    int bottomRightY = Math.max(alignedStartY, alignedMouseY);

                    // Calculate the width and height of the box, ensuring they are multiples of GRID_SIZE
                    int width = ((bottomRightX - topLeftX) / gridSize) * gridSize;
                    int height = ((bottomRightY - topLeftY) / gridSize) * gridSize;

                    // Draw the outline of the box
                    Graphics2D g2d = (Graphics2D) panel.getGraphics();
                    g2d.setStroke(new BasicStroke(2)); // Set line thickness

                    // Clear the previous temporary box by repainting the panel
                    if (mouseX % gridSize == 0 || mouseY % gridSize == 0)
                        panel.repaint();

                    // Draw the new temporary box aligned with the grid lines
                    g2d.setColor(Color.GRAY);
                    g2d.drawRect(topLeftX, topLeftY, width, height);
                }
            }
        });

    }
}
