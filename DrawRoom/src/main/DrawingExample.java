package main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingExample extends JFrame {
    private DrawingPanel drawingPanel;

    public DrawingExample() {
        setTitle("Drawing Example");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingPanel = new DrawingPanel();
        getContentPane().add(drawingPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DrawingExample::new);
    }
}

class DrawingPanel extends JPanel {
    private List<Room> rooms;
    private Room currentRoom;
    private Room selectedRoom;
    private int startX, startY, endX, endY;

    public DrawingPanel() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);

        rooms = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                endX = startX;
                endY = startY;

                // Check if clicked on a room
                selectedRoom = null;
                for (Room room : rooms) {
                    if (room.contains(startX, startY)) {
                        selectedRoom = room;
                        selectedRoom.highlightWall(startX, startY);
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                if (currentRoom != null) {
                    rooms.add(currentRoom);
                    currentRoom = null;
                }
                selectedRoom = null;
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                if (selectedRoom == null) {
                    currentRoom = new Room(startX, startY, endX, endY);
                    repaint();
                } else {
                    selectedRoom.moveWall(startX, startY, endX, endY);
                    startX = endX;
                    startY = endY;
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Room room : rooms) {
            if (room == selectedRoom) {
                room.drawHighlighted(g);
            } else {
                room.draw(g);
            }
        }
        if (currentRoom != null) {
            currentRoom.draw(g);
        }
    }
}


class Room {
    private int x1, y1, x2, y2;
    private boolean highlightedTop, highlightedBottom, highlightedLeft, highlightedRight;

    public Room(int x1, int y1, int x2, int y2) {
        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);
        initializeWallHighlights();
    }

    private void initializeWallHighlights() {
        highlightedTop = false;
        highlightedBottom = false;
        highlightedLeft = false;
        highlightedRight = false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3)); // Set stroke width to 3
        g2d.drawRect(x1, y1, x2 - x1, y2 - y1);
        g2d.dispose();
    }

    public void drawHighlighted(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(3)); // Set stroke width to 3
        
        if (highlightedTop) {
            g2d.setColor(Color.RED);
            g2d.drawLine(x1, y1, x2, y1);
        } 
        
        if (highlightedBottom) {
            g2d.setColor(Color.RED);
            g2d.drawLine(x1, y2, x2, y2);
        }
        
        if (highlightedLeft) {
            g2d.setColor(Color.RED);
            g2d.drawLine(x1, y1, x1, y2);
        }
        
        if (highlightedRight) {
            g2d.setColor(Color.RED);
            g2d.drawLine(x2, y1, x2, y2);
        }
        g2d.dispose();
    }

    public boolean contains(int x, int y) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    public void highlightWall(int x, int y) {
        if (x == x1 && y == y1) {
            highlightedTop = true;
        } else if (x == x1 && y == y2) {
            highlightedLeft = true;
        } else if (x == x2 && y == y1) {
            highlightedRight = true;
        } else if (x == x2 && y == y2) {
            highlightedBottom = true;
        }
    }

    public void moveWall(int startX, int startY, int endX, int endY) {
        if (highlightedTop) {
            y1 += (endY - startY);
        } else if (highlightedBottom) {
            y2 += (endY - startY);
        } else if (highlightedLeft) {
            x1 += (endX - startX);
        } else if (highlightedRight) {
            x2 += (endX - startX);
        }
    }
}
