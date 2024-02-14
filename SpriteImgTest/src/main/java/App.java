import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App extends JFrame {
    private JPanel drawingPanel;

    public App() {
        setTitle("Toolbar Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Create toolbar
        JToolBar toolBar = new JToolBar();
        JButton copyButton = new JButton(new ImageIcon("src/main/java/sprite1.png")); // replace "src/main/java/sprite1.png" with your icon file path
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code to handle copying of images on the drawing surface
                drawingPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Retrieve mouse position relative to content pane
                        Point mousePos = getMousePosition();
                        // Convert mouse position to be relative to drawing panel
                        Point panelPos = drawingPanel.getLocationOnScreen();
                        Point clickPoint = new Point(mousePos.x - panelPos.x, mousePos.y - panelPos.y);
                        // Add code to handle placement of copied images on the drawing surface
                        // Example:
                        JLabel label = new JLabel(new ImageIcon("src/main/java/sprite1.png")); // replace "src/main/java/sprite1.png" with your image file path
                        label.setBounds(clickPoint.x, clickPoint.y, label.getPreferredSize().width, label.getPreferredSize().height);
                        drawingPanel.add(label);
                        drawingPanel.revalidate();
                        drawingPanel.repaint();
                    }
                });
            }
        });
        toolBar.add(copyButton);

        // Create drawing surface
        drawingPanel = new JPanel();
        drawingPanel.setBackground(Color.WHITE);

        // Add components to the frame
        getContentPane().add(toolBar, BorderLayout.NORTH);
        getContentPane().add(drawingPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App().setVisible(true);
            }
        });
    }
}
