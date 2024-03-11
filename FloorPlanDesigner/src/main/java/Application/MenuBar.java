package Application;
import javax.swing.*;
import java.awt.Dimension;
import java.time.chrono.Era;

//Implements Singleton

public class MenuBar {
    private static MenuBar INSTANCE;
    private JMenuBar menuBar;

    private MenuBar() {
        // Create menu bar
        menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS)); // Horizontal layout

        // Create File menu
        JMenu fileMenu = FileMenu.getInstance().getMenu();
        JMenu drawMenu = DrawMenu.getInstance().getMenu();
        JMenuItem roomMenu = RoomMenu.getInstance();
        JMenuItem furnitureMenu = FurnitureMenu.getInstance().getJMenuItem();
        JMenuItem eraseMenu = Application.EraseMenu.getInstance();

        // Set preferred sizes for JMenu object

        // Add File menu to menu bar
        menuBar.add(fileMenu);
        menuBar.add(drawMenu);
        menuBar.add(roomMenu);
        menuBar.add(furnitureMenu);
        menuBar.add(eraseMenu);

    }

    public static MenuBar getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenuBar();
        }
        return INSTANCE;
    }
    public JMenuBar getMenuBar() {
    	return menuBar;
    }
}
