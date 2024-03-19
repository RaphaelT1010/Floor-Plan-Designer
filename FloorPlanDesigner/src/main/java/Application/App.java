package Application;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.*;
import java.awt.event.*;

public class App {
    private JFrame homeScreen;

    public App() {
        JFrame homeScreen = new JFrame();
        homeScreen.setTitle("Floor Planner");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        homeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window to fullscreen
        Color backgroundColor = new Color(	102,153,204);
        homeScreen.getContentPane().setBackground(backgroundColor); // Set the background color to white
        homeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeScreen.setLocationRelativeTo(null); // Center the window on the screen

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        homeScreen.add(layeredPane); // Add layered pane to the frame

        // Get the MenuBar instance and add its JMenuBar to the JFrame
        MenuBar menuBar = MenuBar.getInstance();
        homeScreen.setJMenuBar(menuBar.getMenuBar());

        ToolBox toolbox = ToolBox.getInstance();
        DrawingPanel drawingPanel = DrawingPanel.getInstance();

        // Set the bounds for each component
        drawingPanel.getPanel().setBounds(0, 0, (int) screenSize.getWidth() * 2, (int) screenSize.getHeight() * 2);
        toolbox.getToolboxPanel().setBounds((int) screenSize.getWidth() - 225, 0, 225, (int) screenSize.getHeight());

        // Add components to the layered pane with appropriate layers
        layeredPane.add(drawingPanel.getPanel(), JLayeredPane.DEFAULT_LAYER); // Drawing panel at default layer
        layeredPane.add(toolbox.getToolboxPanel(), JLayeredPane.PALETTE_LAYER); // Toolbox at palette layer


        // Add a component listener to handle resizing
        homeScreen.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Set the bounds for each component dynamically
                drawingPanel.getPanel().setBounds(0, 0, homeScreen.getWidth() * 2, homeScreen.getHeight() * 2) ;
                toolbox.getToolboxPanel().setBounds(homeScreen.getWidth() - 225, 0, 225, homeScreen.getHeight());
            }
        });


        homeScreen.setVisible(true);

    }

    public static void main(String[] args) {
        new App();
    }
}