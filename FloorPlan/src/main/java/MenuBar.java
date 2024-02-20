import javax.swing.*;

//Implements Singleton

public class MenuBar {
    private static MenuBar instance;
    private JMenuBar menuBar;

    private MenuBar() {
        // Create menu bar
        menuBar = new JMenuBar();
        
        // Create File menu
        JMenu fileMenu = FileMenu.getInstance().getMenu();
        JMenu drawMenu = DrawMenu.getInstance().getMenu();
        
        
        // Add File menu to menu bar
        menuBar.add(fileMenu);
        menuBar.add(drawMenu);
    }

    public static MenuBar getInstance() {
        if (instance == null) {
            instance = new MenuBar();
        }
        return instance;
    }
    public JMenuBar getMenuBar() {
    	return menuBar;
    }
}
