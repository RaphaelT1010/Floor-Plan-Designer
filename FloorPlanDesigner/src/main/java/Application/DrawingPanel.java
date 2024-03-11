package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DrawingPanel implements Serializable {
    private static final long serialVersionUID = 1L; // Version ID for serialization
    private static DrawingPanel INSTANCE = null;
    private JPanel panel;
    public final int GRID_SIZE = 30; // Size of the grid squares
    public List<DrawingPanelSegment> drawingPanelSegments = new ArrayList<>();
    public List<DrawingPanelRoom> drawingPanelRooms = new ArrayList<>();

    public void emptyDrawingPanel(){
        drawingPanelSegments.clear();
        drawingPanelRooms.clear();
    }

    private DrawingPanel() {
        // Private constructor to prevent instantiation
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Draw grid lines
                drawGridLines(g2d, getWidth(), getHeight());

                if(!drawingPanelRooms.isEmpty())
                    drawAllPanelRooms(g2d);

                if (!drawingPanelSegments.isEmpty())
                    drawAllPanelSegments(g2d);

            }
        };
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


    public int getGRID_SIZE(){
        return GRID_SIZE;
    }

    private void drawGridLines (Graphics2D g2d, int panelWidth, int panelHeight){
        Color gridColor = new Color(173, 216, 230, 191); // Light blue with 50% transparency
        g2d.setColor(gridColor);
        for (int x = 0; x < panelWidth; x += GRID_SIZE)
            g2d.drawLine(x, 0, x, panelHeight);
        for (int y = 0; y < panelHeight; y += GRID_SIZE)
            g2d.drawLine(0, y, panelWidth, y);
    }

    private void drawAllPanelSegments(Graphics2D g2d){
        g2d.setStroke(new BasicStroke(3)); // Set line thickness

        for (DrawingPanelSegment segment : drawingPanelSegments) {

            if (segment.getEndingPoint() != null) {

                List<Point> segmentPoints = segment.getPoints(); // Get points of this segment
                g2d.setColor(segment.getColor());
                g2d.setStroke(new BasicStroke(segment.getStrokeSize()));

                g2d.drawLine(segment.getStartingPoint().x, segment.getStartingPoint().y, segment.getEndingPoint().x, segment.getEndingPoint().y);
            }
        }
    }

    private void drawAllPanelRooms(Graphics2D g2d){
        g2d.setStroke(new BasicStroke(3)); // Set line thickness

        for (DrawingPanelRoom room : drawingPanelRooms) {
            g2d.setColor(Color.BLACK);
            if (room.getEndingPoint() != null) {
                if (Math.abs(room.getWidth()) > 0 && Math.abs(room.getHeight()) > 0) {
                    if (room.getWidth() > 0 && room.getHeight() > 0)
                        g2d.drawRect(room.getStartingPoint().x, room.getStartingPoint().y, room.getWidth(), room.getHeight());
                    else if (room.getWidth() < 0 && room.getHeight() > 0)
                        g2d.drawRect(room.getEndingPoint().x, room.getStartingPoint().y, Math.abs(room.getWidth()), room.getHeight());
                    else if (room.getWidth() > 0 && room.getHeight() < 0)
                        g2d.drawRect(room.getStartingPoint().x, room.getEndingPoint().y, room.getWidth(), Math.abs(room.getHeight()));
                    else
                        g2d.drawRect(room.getEndingPoint().x, room.getEndingPoint().y, Math.abs(room.getWidth()), Math.abs(room.getHeight()));
                }
            }
    }




}
}