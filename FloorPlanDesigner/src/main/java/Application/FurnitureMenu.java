package Application;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

public class FurnitureMenu {
    private static JMenuItem INSTANCE;

    private FurnitureMenu() {
    }

    public static JMenuItem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JMenuItem("Furniture");
            INSTANCE.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removePriorMouseListeners();
                    ToolBox.getInstance().setToolBoxLabel("Adding furniture...");
                    ToolBox.getInstance().populateToolBoxWithFurniture();
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
}
