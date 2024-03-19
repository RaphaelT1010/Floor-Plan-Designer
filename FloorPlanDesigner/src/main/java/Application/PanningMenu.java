package Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanningMenu {

    private static JMenuItem INSTANCE;
    private static Point lastPoint;

    private PanningMenu() {}

    public static JMenuItem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JMenuItem("Pan");
            INSTANCE.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ToolBox.getInstance().setToolBoxLabel("Panning...");
                    removePriorMouseListeners();
                    setMouseListeners();
                }
            });

            INSTANCE.setPreferredSize(null);
            INSTANCE.setMaximumSize(new Dimension(INSTANCE.getPreferredSize()));
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
            public void mousePressed(MouseEvent e) {
                lastPoint = e.getPoint();
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                panDrawingPanel(e);
            }
        });
    }

    private static void panDrawingPanel(MouseEvent e) {
        DrawingPanel panelInstance = DrawingPanel.getInstance();
        JPanel panel = panelInstance.getPanel();

        Point currentPoint = e.getPoint();

        int deltaX = lastPoint.x - currentPoint.x; // Calculate the change in X position
        int deltaY = lastPoint.y - currentPoint.y; // Calculate the change in Y position

        panel.setLocation(panel.getX() - deltaX, panel.getY() - deltaY);

        lastPoint = currentPoint;

    }


}

