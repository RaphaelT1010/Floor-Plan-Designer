package Application;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class ToolBox {
    private static ToolBox INSTANCE;
    private JPanel toolboxPanel;
    private JPanel buttonPanel; // Panel for buttons

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

    public void emptyToolBox() {
        if (buttonPanel != null){
            buttonPanel.removeAll();
        }
        toolboxPanel.removeAll();
    }

    // Overloaded method to populate toolbox based on selected JMenu
    public void setToolBoxLabel(String desiredLabel) {
        emptyToolBox();
        toolboxPanel.add(new JLabel((desiredLabel)));
        // Update UI
        toolboxPanel.revalidate();
        toolboxPanel.repaint();
    }

    public void populateToolBoxWithFurniture() {
        // Create panel for buttons
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Left align buttons
        Color backgroundColor = new Color(184, 196, 204);
        buttonPanel.setBackground(backgroundColor);
        toolboxPanel.add(buttonPanel, BorderLayout.CENTER); // Add buttons panel to center
        buttonPanel.setPreferredSize(new Dimension(200, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        buttonPanel.setMaximumSize(new Dimension(200, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight())); // Limit maximum size to preferred size

        // Assuming your furniture sprites are stored in a directory named "furniture"
        File furnitureDirectory = new File(Objects.requireNonNull(getClass().getResource("/furniture")).getFile());

        if (furnitureDirectory.exists() && furnitureDirectory.isDirectory()) {
            File[] furnitureFiles = furnitureDirectory.listFiles();
            if (furnitureFiles != null) {
                for (File file : furnitureFiles) {
                    if (file.isFile()) {

                        try {
                            ImageIcon icon = new ImageIcon(ImageIO.read(file));
                            JButton furnitureButton = new JButton(icon);

                            furnitureButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    JOptionPane.showMessageDialog(null, "Furniture button!");
                                }
                            });

                            buttonPanel.add(furnitureButton); // Add button to button panel

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
