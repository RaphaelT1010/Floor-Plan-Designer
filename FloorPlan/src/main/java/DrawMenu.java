import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class DrawMenu {
	private static DrawMenu instance;
	private JMenu drawMenu;
	
	private DrawMenu(){
        // Create Wall option
		drawMenu = new JMenu("Draw");
        JMenuItem wallItem = new JMenuItem("Wall");
        wallItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add your save logic here
                JOptionPane.showMessageDialog(null, "Wall option clicked!");
            }
        });
        
        // Create Door option
        JMenuItem doorItem = new JMenuItem("Door");
        doorItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add your load logic here
                JOptionPane.showMessageDialog(null, "Door option clicked!");
            }
        });
        
        // Create Window option
        JMenuItem windowItem = new JMenuItem("Window");
        windowItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add your load logic here
                JOptionPane.showMessageDialog(null, "Window option clicked!");
            }
        });
        
        // Add save and load items to File menu
        drawMenu.add(wallItem);
        drawMenu.add(doorItem);
        drawMenu.add(windowItem);
	}
    public static DrawMenu getInstance() {
        if (instance == null) {
            instance = new DrawMenu();
        }
        return instance;
    }

    public JMenu getMenu() {
        return drawMenu;
    }
	
}
