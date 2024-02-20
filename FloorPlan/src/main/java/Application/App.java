package Application;
import javax.swing.JFrame;

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
        
        homeScreen.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
