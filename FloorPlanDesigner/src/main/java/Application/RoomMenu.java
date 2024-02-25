package Application;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;


public class RoomMenu {
    private static JMenuItem INSTANCE;

    private RoomMenu() {

    }

    public static JMenuItem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JMenuItem("Room");
            INSTANCE.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add your logic here when "Room" is clicked
                    ToolBox.getInstance().setToolBoxLabel("Adding rooms...");
                }
            });

            INSTANCE.setPreferredSize(null); // Resetting preferred size
            INSTANCE.setMaximumSize(new Dimension(INSTANCE.getPreferredSize())); // Adjust width as needed
        }
        return INSTANCE;
    }
}
