package Application;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {
    private JFrame homeScreen;

    public App() {
        homeScreen = new JFrame();
        homeScreen.setTitle("Floor Plan Designers");
        homeScreen.setSize(400, 300);
        homeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeScreen.setLocationRelativeTo(null); // Center the window on the screen
        
        // Get the MenuBar instance and add its JMenuBar to the JFrame
        MenuBar menuBar = MenuBar.getInstance();
        homeScreen.setJMenuBar(menuBar.getMenuBar());
        
        
        // Get the DrawingPanel instance and add it to the JFrame
        JPanel drawingPanel = DrawingPanel.getInstance().getPanel();
        homeScreen.add(drawingPanel); // Adding the drawing panel to the JFrame
        
        homeScreen.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
