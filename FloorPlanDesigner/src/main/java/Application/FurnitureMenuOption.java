package Application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.*;

//Extends JPanel to support drawing sprites using paintComponenet method
public class FurnitureMenuOption {
    private static FurnitureMenuOption INSTANCE;
    private JMenuItem furnitureMenu;

    private JPanel panel = DrawingPanel.getInstance().getPanel();
    private BufferedImage selectedSpriteImage;
    Point mousePosition;
    private DrawingPanelFurniture selectedSprite;


    private FurnitureMenuOption() {
        furnitureMenu = new JMenuItem("Furniture");
        furnitureMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToolBox.getInstance().setToolBoxLabel("Adding furniture...");
                ToolBox.getInstance().populateToolBoxWithFurniture();
                setSpriteToDraw(null);
                removePriorMouseListeners();
                setMouseListeners();
            }
        });

        furnitureMenu.setPreferredSize(null); // Resetting preferred size
        furnitureMenu.setMaximumSize(new Dimension(furnitureMenu.getPreferredSize())); // Adjust width as needed
    }


    public static FurnitureMenuOption getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new FurnitureMenuOption();
        }
        return INSTANCE;
    }

    public JMenuItem getJMenuItem() {
    	return furnitureMenu;
    }

    private void removePriorMouseListeners(){
        JPanel panel =  DrawingPanel.getInstance().getPanel();
        for (MouseListener listener : panel.getMouseListeners()) {
            panel.removeMouseListener(listener);
        }
        for (MouseMotionListener listener : panel.getMouseMotionListeners()) {
            panel.removeMouseMotionListener(listener);
        }
    }



    private void setMouseListeners() {
        List<DrawingPanelFurniture> furnitureSpriteList = DrawingPanel.getInstance().drawingPanelFurniture;
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    boolean foundExistingSprite = false;
                    if (furnitureSpriteList != null){
                        for (DrawingPanelFurniture sprite : furnitureSpriteList) {
                            if (sprite.contains(e.getPoint())) {
                                selectedSprite = sprite; // Set the selected sprite for dragging
                                foundExistingSprite = true;
                                mousePosition = e.getPoint();
                                break;
                        }
                        }
                    }
                    if (!foundExistingSprite && selectedSpriteImage != null) {
                        furnitureSpriteList.add(new DrawingPanelFurniture(e.getPoint(), selectedSpriteImage, 0));
                        panel.repaint();
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    for (DrawingPanelFurniture sprite : furnitureSpriteList) {
                        if (sprite.contains(e.getPoint())) {
                            showPopupMenu(sprite, e.getPoint());
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedSprite = null; // Clear selected sprite after mouse release
            }

        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedSprite != null) {
                    int dx = e.getX() - mousePosition.x;
                    int dy = e.getY() - mousePosition.y;
                    selectedSprite.moveBy(dx, dy);
                    mousePosition = e.getPoint();
                    panel.repaint();
                }

            }
        });
    }


    private void showPopupMenu(DrawingPanelFurniture sprite, Point point) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem rotateMenuItem = new JMenuItem("Rotate");
        JMenuItem increaseMenuItem = new JMenuItem("Enlarge");
        JMenuItem decreaseSizeItem = new JMenuItem("Shrink");



        rotateMenuItem.addActionListener(e -> {
            sprite.rotate();
            panel.repaint();
        });

        increaseMenuItem.addActionListener(e -> {
            sprite.increaseSize();
            panel.repaint();
        });

        decreaseSizeItem.addActionListener(e -> {
            sprite.decreaseSize();
            panel.repaint();
        });


        popupMenu.add(rotateMenuItem);
        popupMenu.add(increaseMenuItem);
        popupMenu.add(decreaseSizeItem);

        popupMenu.show(panel, point.x, point.y); // Corrected this line
    }

    public void setSpriteToDraw(BufferedImage SelectedSpriteImage) {
        selectedSpriteImage = SelectedSpriteImage;
    }



}
