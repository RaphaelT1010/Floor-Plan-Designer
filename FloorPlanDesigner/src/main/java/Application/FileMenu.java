package Application;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Iterator;
import java.util.List;


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
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Drawing Files", "draw");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().endsWith(".draw")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".draw");
            }
            SaveLoadHandler data = new SaveLoadHandler();
            data.saveDrawingPanel();
            data.saveToFile(selectedFile);

            JOptionPane.showMessageDialog(null, "File saved successfully!");
        }
    }

    public static void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Drawing Files", "draw");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                SaveLoadHandler data = SaveLoadHandler.loadFromFile(file);
                if (data != null) {
                    DrawingPanel.getInstance().emptyDrawingPanel();

                    // Load and add segments
                    List<DrawingPanelSegment> segments = data.getDrawingPanelSegments();
                    Iterator<DrawingPanelSegment> segmentIterator = segments.iterator();
                    while (segmentIterator.hasNext()) {
                        DrawingPanelSegment segment = segmentIterator.next();
                        DrawingPanel.getInstance().drawingPanelSegments.add(segment);
                    }

                    // Load and add rooms
                    List<DrawingPanelRoom> rooms = data.getDrawingPanelRooms();
                    Iterator<DrawingPanelRoom> roomIterator = rooms.iterator();
                    while (roomIterator.hasNext()) {
                        DrawingPanelRoom room = roomIterator.next();
                        DrawingPanel.getInstance().drawingPanelRooms.add(room);
                    }

                    DrawingPanel.getInstance().getPanel().repaint();
                    JOptionPane.showMessageDialog(null, "File loaded successfully!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while loading the file.");
            }
        }
    }




}
