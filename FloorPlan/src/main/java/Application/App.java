package Application;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.*;

public class App {
    private JFrame homeScreen;

    public App() {
        homeScreen = new JFrame();
        homeScreen.setTitle("Floor Planner");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        homeScreen.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        homeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeScreen.setLocationRelativeTo(null); // Center the window on the screen

        // Get the MenuBar instance and add its JMenuBar to the JFrame
        MenuBar menuBar = MenuBar.getInstance();
        homeScreen.setJMenuBar(menuBar.getMenuBar());

        ToolBox toolbox = ToolBox.getInstance();
        DrawingPanel drawingPanel = DrawingPanel.getInstance();

        homeScreen.setLayout(new BorderLayout());

        // Add components to the JFrame
        homeScreen.add(drawingPanel.getPanel(), BorderLayout.CENTER); // Adding the drawing panel to the center
        homeScreen.add(toolbox.getToolboxPanel(), BorderLayout.EAST); // Adding the toolbox to the right side

        homeScreen.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
