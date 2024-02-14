import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends JFrame {

    private BufferedImage canvas;
    private List<Rectangle> rooms; // Store created rooms
    private Point startPoint;
    private Point currentPoint; // Track the current point during dragging
    private int gridSize = 30; // Size of grid cells
    private FloorElementType currentElementType = FloorElementType.WALL; // Default to drawing walls

    public enum FloorElementType {
        WALL(Color.BLACK),
        WINDOW(Color.CYAN),
        DOOR(Color.RED),
        ROOM(Color.BLACK); // Added ROOM type

        private final Color color;

        FloorElementType(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    public App() {
        super("Floor Plan Designer");
        initUI();
    }

    private void initUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        clearCanvas();
        rooms = new ArrayList<>();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(canvas, 0, 0, null);

                // Draw grid lines
                g.setColor(Color.LIGHT_GRAY);
                for (int x = 0; x <= getWidth(); x += gridSize) {
                    g.drawLine(x, 0, x, getHeight());
                }
                for (int y = 0; y <= getHeight(); y += gridSize) {
                    g.drawLine(0, y, getWidth(), y);
                }

                // Draw the rooms
                for (Rectangle room : rooms) {
                    g.setColor(FloorElementType.ROOM.getColor());
                    g.drawRect(room.x, room.y, room.width, room.height);
                }
            }
        };

        panel.setPreferredSize(new Dimension(width, height));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = getNearestGridPoint(e.getPoint());
                currentPoint = startPoint; // Set current point to start point initially
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentElementType == FloorElementType.ROOM) {
                    Point endPoint = getNearestGridPoint(e.getPoint());
                    drawRoom(startPoint, endPoint); // Draw room
                } else {
                    Point endPoint = getNearestGridPoint(e.getPoint());
                    drawElement(startPoint, endPoint); // Draw straight lines for walls, windows, and doors
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentPoint = getNearestGridPoint(e.getPoint());
                repaint();
            }
        });

        add(panel);
        setupMenuBar();
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void drawElement(Point start, Point end) {
        if (isInCanvasBounds(start) && isInCanvasBounds(end)) {
            // Calculate the nearest horizontal or vertical grid point for the end of the line
            int x1 = start.x;
            int y1 = start.y;
            int x2 = end.x;
            int y2 = end.y;
            if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
                // Horizontal line
                y2 = y1;
            } else {
                // Vertical line
                x2 = x1;
            }

            Graphics2D g2d = canvas.createGraphics();
            g2d.setColor(currentElementType.getColor());
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x1, y1, x2, y2);
            g2d.dispose();
            repaint();
        }
    }

    private void drawRoom(Point start, Point end) {
        if (isInCanvasBounds(start) && isInCanvasBounds(end)) {
            // Ensure start point is top-left and end point is bottom-right
            int x = Math.min(start.x, end.x);
            int y = Math.min(start.y, end.y);
            int width = Math.abs(start.x - end.x);
            int height = Math.abs(start.y - end.y);
            rooms.add(new Rectangle(x, y, width, height)); // Add room to list
            repaint();
        }
    }

    private boolean isInCanvasBounds(Point point) {
        return point.x >= 0 && point.x < canvas.getWidth() &&
                point.y >= 0 && point.y < canvas.getHeight();
    }

    private Point getNearestGridPoint(Point point) {
        int x = (point.x / gridSize) * gridSize;
        int y = (point.y / gridSize) * gridSize;

        return new Point(x, y);
    }

    private void clearCanvas() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.dispose();
        repaint();
    }

    private void saveImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ImageIO.write(canvas, "PNG", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveImage());
        fileMenu.add(saveItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu elementMenu = new JMenu("Element");
        ButtonGroup group = new ButtonGroup();
        for (FloorElementType type : FloorElementType.values()) {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(type.name());
            item.addActionListener(e -> currentElementType = type);
            group.add(item);
            elementMenu.add(item);
        }

        menuBar.add(fileMenu);
        menuBar.add(elementMenu);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
