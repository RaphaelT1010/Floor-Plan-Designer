package Application;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FileMenu {
    private static FileMenu INSTANCE;
    private JMenu fileMenu;

    private FileMenu() {
        // Create Save option
        fileMenu = new JMenu("File");
        final JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add your save logic here
                ToolBox.getInstance().emptyToolBox();
                saveFile();
            }
        });

        // Create Load option
        final JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ToolBox.getInstance().emptyToolBox();
                loadFile();
            }
        });

        // Add save and load items to File menu
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
    }

    public static FileMenu getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileMenu();
        }
        return INSTANCE;
    }

    public JMenu getMenu() {
        return fileMenu;
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(selectedFile)) {

                JOptionPane.showMessageDialog(null, "File saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while saving the file.");
            }
        }
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {

                JOptionPane.showMessageDialog(null, "File loaded successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while loading the file.");
            }
        }

    }
}
