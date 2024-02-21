package Application;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class FileMenu {
	private static FileMenu instance;
	private JMenu fileMenu;
	
	private FileMenu(){
        // Create Save option
		fileMenu = new JMenu("File");
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
	}
    public static FileMenu getInstance() {
        if (instance == null) {
            instance = new FileMenu();
        }
        return instance;
    }

    public JMenu getMenu() {
        return fileMenu;
    }
	
}