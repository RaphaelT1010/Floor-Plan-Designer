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
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

//Extends JPanel to support drawing sprites using paintComponenet method
public class FurnitureMenu extends JPanel{
    private static FurnitureMenu INSTANCE;
    private JMenuItem furnitureMenu;
    
    private JPanel panel = DrawingPanel.getInstance().getPanel();
    private BufferedImage selectedSpriteImage;
    private List<Sprite> placedSprites;
    private Sprite selectedSprite;
    private Point mousePosition;
    
    private FurnitureMenu() {
        furnitureMenu = new JMenuItem("Furniture");
        furnitureMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToolBox.getInstance().setToolBoxLabel("Adding furniture...");
                ToolBox.getInstance().populateToolBoxWithFurniture();
                removePriorMouseListeners();
                setMouseListeners();
            }
        });

        furnitureMenu.setPreferredSize(null); // Resetting preferred size
        furnitureMenu.setMaximumSize(new Dimension(furnitureMenu.getPreferredSize())); // Adjust width as needed
    }

    public static FurnitureMenu getInstance() {
        if (INSTANCE == null) {
        	INSTANCE = new FurnitureMenu();
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
    	placedSprites = new ArrayList<>();
        selectedSprite = null;
        mousePosition = null;

        // Add mouse listener to handle clicks and drags
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    boolean found = false;
                    for (Sprite sprite : placedSprites) {
                        if (sprite.contains(e.getPoint())) {
                            selectedSprite = sprite;
                            mousePosition = e.getPoint();
                            found = true;
                            break;
                        }
                    }
                    if (!found && selectedSpriteImage != null) {
                        placedSprites.add(new Sprite(e.getPoint(), selectedSpriteImage));
                        panel.repaint();
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    for (Sprite sprite : placedSprites) {
                        if (sprite.contains(e.getPoint())) {
                            selectedSprite = sprite;
                            showPopupMenu(e.getX(), e.getY()); // Corrected this line
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && selectedSprite != null) {
                    selectedSprite = null;
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedSprite != null) {
                    Point newPoint = e.getPoint();
                    int dx = newPoint.x - mousePosition.x;
                    int dy = newPoint.y - mousePosition.y;
                    selectedSprite.moveBy(dx, dy);
                    mousePosition = newPoint;
                    panel.repaint();
                }
            }
        });
    }

    private void showPopupMenu(int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem rotateMenuItem = new JMenuItem("Rotate");
        rotateMenuItem.addActionListener(e -> {
            selectedSprite.rotate();
            panel.repaint();
        });
        popupMenu.add(rotateMenuItem);
        popupMenu.show(panel, x, y); // Corrected this line
    }

    public void setSelectedSpriteImage(BufferedImage SelectedSpriteImage) {
        selectedSpriteImage = SelectedSpriteImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the colored background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw all placed sprites
        for (Sprite sprite : placedSprites) {
            sprite.draw(g);
        }
    }

    private class Sprite {
        private Point position;
        private double angle;
        private BufferedImage spriteImage;

        public Sprite(Point position, BufferedImage spriteImage) {
            this.position = position;
            this.spriteImage = spriteImage;
            this.angle = 0;
        }

        public void rotate() {
            angle += Math.PI / 2; // Rotate by 90 degrees
        }

        public boolean contains(Point point) {
            return new Rectangle(position.x, position.y, spriteImage.getWidth(), spriteImage.getHeight()).contains(point);
        }

        public void moveBy(int dx, int dy) {
            position.translate(dx, dy);
        }

        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate(angle, position.x + spriteImage.getWidth() / 2.0, position.y + spriteImage.getHeight() / 2.0);
            g2d.drawImage(spriteImage, position.x, position.y, null);
            g2d.dispose();
        }
    }
}
