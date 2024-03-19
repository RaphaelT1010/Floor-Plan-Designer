package Application;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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
        JLabel label = new JLabel(desiredLabel);
        Font font = new Font("Verdana", Font.BOLD, 12);
        label.setFont(font);
        toolboxPanel.add(label);

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
                            BufferedImage image = ImageIO.read(file);

                            int desiredWidth = 25; // Set your desired width here
                            int desiredHeight = 35; // Set your desired height here

                            // Resize the image while maintaining aspect ratio
                            Image scaledImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);

                            // Create ImageIcon from the scaled image
                            ImageIcon icon = new ImageIcon(scaledImage);
                            JButton furnitureButton = new JButton(icon);

                            FurnitureMenu furnitureMenu = FurnitureMenu.getInstance();


                            furnitureButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
									furnitureMenu.setSpriteToDraw(image);
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
