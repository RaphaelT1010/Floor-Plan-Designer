package Application;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class FurnitureMenu {
    private static JMenuItem INSTANCE;

    private FurnitureMenu() {
    }

    public static JMenuItem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JMenuItem("Furniture");
            INSTANCE.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add your logic here when "Room" is clicked

                    JOptionPane.showMessageDialog(null, "Furniture option clicked!");
                }
            });

            INSTANCE.setPreferredSize(null); // Resetting preferred size
            INSTANCE.setMaximumSize(new Dimension(INSTANCE.getPreferredSize())); // Adjust width as needed
        }
        return INSTANCE;
    }
}
