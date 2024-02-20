package Application;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomMenu {
    private static JMenuItem instance;

    private RoomMenu() {
    }

    public static JMenuItem getInstance() {
        if (instance == null) {
            instance = new JMenuItem("Room");
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
