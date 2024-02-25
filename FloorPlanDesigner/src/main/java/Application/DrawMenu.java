package Application;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;

public class DrawMenu {
	private static DrawMenu INSTANCE;
	private JMenu drawMenu;
	
	private DrawMenu(){
        // Create Wall option
		drawMenu = new JMenu("Draw");
        JMenuItem wallItem = new JMenuItem("Wall");
        wallItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ToolBox.getInstance().setToolBoxLabel("Drawing walls...");
                DrawingPanel.getInstance().setColor(Color.BLACK);
                DrawingPanel.getInstance().canDraw(Boolean.TRUE);
            }
        });
        
        // Create Door option
        JMenuItem doorItem = new JMenuItem("Door");
        doorItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ToolBox.getInstance().setToolBoxLabel("Drawing doors...");
                DrawingPanel.getInstance().setColor(new Color(139, 69, 19));
                DrawingPanel.getInstance().canDraw(Boolean.TRUE);

            }
        });
        
        // Create Window option
        JMenuItem windowItem = new JMenuItem("Window");
        windowItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ToolBox.getInstance().setToolBoxLabel("Drawing windows...");
                DrawingPanel.getInstance().setColor(Color.BLUE);
                DrawingPanel.getInstance().canDraw(Boolean.TRUE);
            }
        });
        
        // Add save and load items to File menu
        drawMenu.add(wallItem);
        drawMenu.add(doorItem);
        drawMenu.add(windowItem);
	}
    public static DrawMenu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DrawMenu();
        }
        return INSTANCE;

    }

    public JMenu getMenu() {
        return drawMenu;
    }
	
}
