package Application;
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
        JMenuItem roomMenu = RoomMenu.getInstance();
        JMenuItem furnitureMenu = FurnitureMenu.getInstance();
        
        
        // Add File menu to menu bar
        menuBar.add(fileMenu);
        menuBar.add(drawMenu);
        menuBar.add(roomMenu);
        menuBar.add(furnitureMenu);
        
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
