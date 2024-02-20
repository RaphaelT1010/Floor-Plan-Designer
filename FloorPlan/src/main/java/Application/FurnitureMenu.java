package Application;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class FurnitureMenu {
    private static JMenuItem instance;

    private FurnitureMenu() {
    }

    public static JMenuItem getInstance() {
        if (instance == null) {
            instance = new JMenuItem("Furniture");
            instance.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add your logic here when "Room" is clicked
                    JOptionPane.showMessageDialog(null, "Room option clicked!");
                }
            });
        }
        return instance;
    }
}
