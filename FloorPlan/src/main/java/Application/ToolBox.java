package Application;

import javax.swing.*;
import java.awt.*;

public class ToolBox {
    private static ToolBox INSTANCE;
    private JPanel toolboxPanel;

    private ToolBox() {
        // Create the toolbox panel
        toolboxPanel = new JPanel();
        Color backgroundColor = new Color(184, 196, 204);
        toolboxPanel.setBackground(backgroundColor);
        // Set preferred size for the toolbox panel
        toolboxPanel.setPreferredSize(new Dimension(250, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight())); // Adjust size as needed
    }

    public static ToolBox getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ToolBox();
        }
        return INSTANCE;
    }

    public JPanel getToolboxPanel() {
        return toolboxPanel;
    }

    // Overloaded method to populate toolbox based on selected JMenu
    public void populateToolbox(JMenu selectedMenu) {
        String menuText = selectedMenu.getText();
        switch (menuText) {
            case "File":
                toolboxPanel.removeAll();
                break;
            case "Draw":
                toolboxPanel.removeAll();
            default:
                break;
        }

        // Update UI
        toolboxPanel.revalidate();
        toolboxPanel.repaint();
    }

    // Overloaded method to populate toolbox based on selected JMenuItem
    public void populateToolbox(JMenuItem selectedItem) {
        String itemText = selectedItem.getText();
        switch (itemText) {
            case "Room":
                toolboxPanel.removeAll();
                break;
            case "Load":
                toolboxPanel.removeAll();
                toolboxPanel.add(new JLabel("Furniture"));
                break;
            default:
                break;
        }

        // Update UI
        toolboxPanel.revalidate();
        toolboxPanel.repaint();
    }
}