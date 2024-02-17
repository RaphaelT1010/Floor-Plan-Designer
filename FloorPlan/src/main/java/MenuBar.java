import javax.swing.*;
import java.awt.event.*;

//Implements Singleton

public class MenuBar {
    private static MenuBar instance;
    private JMenuBar menuBar;

    private MenuBar() {
        // Create menu bar
        menuBar = new JMenuBar();
        
        // Create File menu
        JMenu fileMenu = new JMenu("File");
        
        // Create Save option
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add your save logic here
                JOptionPane.showMessageDialog(null, "Save option clicked!");
            }
        });
        
        // Create Load option
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add your load logic here
                JOptionPane.showMessageDialog(null, "Load option clicked!");
            }
        });
        
        // Add save and load items to File menu
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        
        // Add File menu to menu bar
        menuBar.add(fileMenu);
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
