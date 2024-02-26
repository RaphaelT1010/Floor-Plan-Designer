package Application;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToolBox {
    private static ToolBox INSTANCE;
    private JPanel toolboxPanel;
    private JPanel drawingPanel = DrawingPanel.getInstance().getPanel();
    private Map<JButton, ImageIcon> furnitureMap = new HashMap<>();
    private ImageIcon lastClickedIcon = null;

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

    public void emptyToolBox(){
        toolboxPanel.removeAll();
    }

    // Overloaded method to populate toolbox based on selected JMenu
    public void setToolBoxLabel(String desiredLabel){
        emptyToolBox();
        toolboxPanel.add(new JLabel((desiredLabel)));
        // Update UI
        toolboxPanel.revalidate();
        toolboxPanel.repaint();
    }

    public void populateToolBoxWithFurniture() {
        // Assuming your furniture sprites are stored in a directory named "furniture"
        File furnitureDirectory = new File(getClass().getResource("/furniture").getFile());

        if (furnitureDirectory.exists() && furnitureDirectory.isDirectory()) {
            File[] furnitureFiles = furnitureDirectory.listFiles();

            if (furnitureFiles != null) {

                for (File file : furnitureFiles) {
                    if (file.isFile()) {
                        try {
                            ImageIcon icon = new ImageIcon(ImageIO.read(file));
                            JButton furnitureButton = new JButton(icon);
                            furnitureMap.put(furnitureButton, icon);

                            furnitureButton.setMargin(new Insets(10, 10, 10, 10));
                            furnitureButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    lastClickedIcon = icon;
                                }
                            });
                            toolboxPanel.add(furnitureButton);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }



	public void addMouseListenerToDrawingPanel() {
	    drawingPanel.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            if (lastClickedIcon != null) {
	                JLabel iconLabel = new JLabel(lastClickedIcon);
	                int x = e.getX() - lastClickedIcon.getIconWidth() / 2;
	                int y = e.getY() - lastClickedIcon.getIconHeight() / 2;
	                iconLabel.setBounds(x, y, lastClickedIcon.getIconWidth(), lastClickedIcon.getIconHeight());
	                drawingPanel.add(iconLabel);
	                drawingPanel.revalidate();
	                drawingPanel.repaint();
	            }
	        }
	    });
	}
}
