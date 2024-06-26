package Application;
import javax.swing.*;

//Implements Singleton

public class MenuBar {
    private static MenuBar INSTANCE;
    private JMenuBar menuBar;

    private MenuBar() {
        // Create menu bar
        menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS)); // Horizontal layout

        // Create File menu
        JMenu fileMenu = FileMenuOption.getInstance().getMenu();
        JMenu drawMenu = DrawMenuOption.getInstance().getMenu();
        JMenuItem roomMenu = RoomMenuOption.getInstance();
        JMenuItem furnitureMenu = FurnitureMenuOption.getInstance().getJMenuItem();
        JMenuItem eraseMenu = EraseMenuOption.getInstance();
        JMenuItem panningMenu = PanningMenuOption.getInstance();

        // Set preferred sizes for JMenu object

        // Add File menu to menu bar
        menuBar.add(fileMenu);
        menuBar.add(drawMenu);
        menuBar.add(roomMenu);
        menuBar.add(furnitureMenu);
        menuBar.add(eraseMenu);
        menuBar.add(panningMenu);

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
