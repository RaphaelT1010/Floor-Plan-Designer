package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.util.List;

public class EraseMenu {

    private static JMenuItem INSTANCE;
    private static JPanel panel = DrawingPanel.getInstance().getPanel();

    private void EraseMenu() {
    }

    public static JMenuItem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JMenuItem("Erase");
            INSTANCE.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add your logic here when "Room" is clicked
                    ToolBox.getInstance().setToolBoxLabel("Erasing...");
                    removePriorMouseListeners();
                    setMouseListeners();

                }

            });

            INSTANCE.setPreferredSize(null); // Resetting preferred size
            INSTANCE.setMaximumSize(new Dimension(INSTANCE.getPreferredSize())); // Adjust width as needed
        }
        return INSTANCE;
    }

    private static void removePriorMouseListeners() {
        JPanel panel = DrawingPanel.getInstance().getPanel();
        for (MouseListener listener : panel.getMouseListeners()) {
            panel.removeMouseListener(listener);
        }
        for (MouseMotionListener listener : panel.getMouseMotionListeners()) {
            panel.removeMouseMotionListener(listener);
        }
    }

    private static void setMouseListeners() {
        JPanel panel = DrawingPanel.getInstance().getPanel();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    eraseElementsAt(e.getPoint());
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    showErasePopUpmenu(e.getPoint());
                }
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    eraseElementsNear(e.getPoint());
                }
            }
        });


    }

    private static void showErasePopUpmenu(Point point){
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem eraseMenuItem = new JMenuItem("Erase ");
        JMenuItem eraseAllMenuitem = new JMenuItem("Erase All");

        eraseMenuItem.addActionListener(e -> {
            eraseElementsAt(point);
        });
        eraseAllMenuitem.addActionListener(e -> {
            DrawingPanel.getInstance().emptyDrawingPanel();
            panel.repaint();
        });

        popupMenu.add(eraseMenuItem);
        popupMenu.add(eraseAllMenuitem);
        popupMenu.show(panel, point.x, point.y); // Corrected this line
    }

    private static void eraseElementsAt(Point point) {

        eraseSegmentsNear(point);
        eraseRoomsNear(point);
        eraseFurnitureNear(point);
        DrawingPanel.getInstance().getPanel().repaint();
    }

    private static void eraseElementsNear(Point point) {
        eraseSegmentsNear(point);
        eraseRoomsNear(point);
        eraseFurnitureNear(point);
        DrawingPanel.getInstance().getPanel().repaint();
    }

    private static void eraseSegmentsNear(Point point) {
        Iterator<DrawingPanelSegment> segmentIterator = DrawingPanel.getInstance().drawingPanelSegments.iterator();
        while (segmentIterator.hasNext()) {
            DrawingPanelSegment segment = segmentIterator.next();
            if (isPointNearSegment(segment, point)) {
                segmentIterator.remove();
            }
        }
    }

    private static void eraseRoomsNear(Point point) {
        Iterator<DrawingPanelRoom> roomIterator = DrawingPanel.getInstance().drawingPanelRooms.iterator();
        while (roomIterator.hasNext()) {
            DrawingPanelRoom room = roomIterator.next();
            if (isPointInRoom(room, point)) {
                roomIterator.remove();
            }
        }
    }

    private static void eraseFurnitureNear(Point point) {
        Iterator<DrawingPanelFurniture> furnitureIterator = DrawingPanel.getInstance().drawingPanelFurniture.iterator();
        while (furnitureIterator.hasNext()) {
            DrawingPanelFurniture furniture = furnitureIterator.next();
            if (isPointNearFurniture(furniture, point)) {
                furnitureIterator.remove();
            }
        }
    }

    private static boolean isPointNearSegment(DrawingPanelSegment segment, Point point) {
        List<Point> points = segment.getPoints();
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            Line2D.Double line = new Line2D.Double(p1, p2);
            if (line.ptSegDist(point) <= 5) { // Adjust the tolerance as needed
                return true;
            }
        }
        return false;
    }

    private static boolean isPointInRoom(DrawingPanelRoom room, Point point) {
        Rectangle roomBounds = null;
        if (Math.abs(room.getWidth()) > 0 && Math.abs(room.getHeight()) > 0) {
            if (room.getWidth() > 0 && room.getHeight() > 0)
                roomBounds = new Rectangle(room.getStartingPoint().x, room.getStartingPoint().y, room.getWidth(), room.getHeight());
            else if (room.getWidth() < 0 && room.getHeight() > 0)
                roomBounds = new Rectangle(room.getEndingPoint().x, room.getStartingPoint().y, Math.abs(room.getWidth()), room.getHeight());
            else if (room.getWidth() > 0 && room.getHeight() < 0)
                roomBounds = new Rectangle(room.getStartingPoint().x, room.getEndingPoint().y, room.getWidth(), Math.abs(room.getHeight()));
            else
                roomBounds = new Rectangle(room.getEndingPoint().x, room.getEndingPoint().y, Math.abs(room.getWidth()), Math.abs(room.getHeight()));
        }
        if (roomBounds != null) {
            if (pointOnRectangleBorder(roomBounds, point)) {
                return true;
            }
        }

        return false;

    }
    private static boolean pointOnRectangleBorder(Rectangle roomBounds, Point point) {
        int x = point.x;
        int y = point.y;

        // Check if the point is on any of the four sides of the rectangle
        return (x == roomBounds.x || x == roomBounds.x + roomBounds.width ||
                y == roomBounds.y || y == roomBounds.y + roomBounds.height);
    }
    private static boolean isPointNearFurniture(DrawingPanelFurniture furniture, Point point) {
        // Check if the point is near the furniture's bounding box
        Rectangle furnitureBounds = furniture.getBounds();
        return furnitureBounds.contains(point);
    }
}



