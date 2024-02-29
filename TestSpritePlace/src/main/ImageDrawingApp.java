package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageDrawingApp extends JFrame {

    private DrawingPanel drawingPanel;
    private JPanel spritePanel;

    public ImageDrawingApp() {
        setTitle("Image Drawing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        // Create a new DrawingPanel and add it to the frame
        drawingPanel = new DrawingPanel();
        getContentPane().add(drawingPanel, BorderLayout.CENTER);

        // Create a new JPanel for sprite selection and add it to the frame
        spritePanel = new JPanel();
        getContentPane().add(spritePanel, BorderLayout.EAST);

        // Load the sprite image
        BufferedImage[] spriteImage = new BufferedImage[1];
        try {
            URL spriteUrl = getClass().getResource("sprite.png");
            if (spriteUrl != null) {
                spriteImage[0] = ImageIO.read(spriteUrl);
            } else {
                System.err.println("Sprite image not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a JButton with the sprite image and add it to the sprite panel
        if (spriteImage[0] != null) {
            JButton spriteButton = new JButton(new ImageIcon(spriteImage[0]));
            spriteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingPanel.setSelectedSpriteImage(spriteImage[0]);
                }
            });
            spritePanel.add(spriteButton);
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageDrawingApp());
    }

    // Inner class for the drawing panel
    private class DrawingPanel extends JPanel {

        private BufferedImage selectedSpriteImage;
        private List<Sprite> placedSprites;
        private Sprite selectedSprite;
        private Point mousePosition;

        public DrawingPanel() {
            // Set a preferred size for the panel
            setPreferredSize(new Dimension(600, 400));

            placedSprites = new ArrayList<>();
            selectedSprite = null;
            mousePosition = null;

            // Add mouse listener to handle clicks and drags
            addMouseListener(new MouseAdapter() {
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
                            repaint();
                        }
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        for (Sprite sprite : placedSprites) {
                            if (sprite.contains(e.getPoint())) {
                                selectedSprite = sprite;
                                showPopupMenu(e.getX(), e.getY());
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

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (selectedSprite != null) {
                        Point newPoint = e.getPoint();
                        int dx = newPoint.x - mousePosition.x;
                        int dy = newPoint.y - mousePosition.y;
                        selectedSprite.moveBy(dx, dy);
                        mousePosition = newPoint;
                        repaint();
                    }
                }
            });
        }

        private void showPopupMenu(int x, int y) {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem rotateMenuItem = new JMenuItem("Rotate");
            rotateMenuItem.addActionListener(e -> {
                selectedSprite.rotate();
                repaint();
            });
            popupMenu.add(rotateMenuItem);
            popupMenu.show(this, x, y);
        }

        public void setSelectedSpriteImage(BufferedImage selectedSpriteImage) {
            this.selectedSpriteImage = selectedSpriteImage;
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
    }

    // Inner class to represent a placed sprite
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
