package Application;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveLoadHandler implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<DrawingPanelSegment> savedDrawingPanelSegments = new ArrayList<>();
    private List<DrawingPanelRoom> savedDrawingPanelRooms = new ArrayList<>();

    public SaveLoadHandler(){

    }
    public void saveDrawingPanel(){
        savedDrawingPanelSegments = DrawingPanel.getInstance().drawingPanelSegments;
        savedDrawingPanelRooms = DrawingPanel.getInstance().drawingPanelRooms;
    }

    public List<DrawingPanelSegment> getDrawingPanelSegments() {
        return savedDrawingPanelSegments;
    }

    public List<DrawingPanelRoom> getDrawingPanelRooms() {
        return savedDrawingPanelRooms;
    }

    public void saveToFile(File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Write the size of the list
            oos.writeInt(savedDrawingPanelSegments.size());
            // Write each DrawingPanelSegment object separately
            for (DrawingPanelSegment segment : savedDrawingPanelSegments) {
                oos.writeObject(segment);
            }

            // Write the size of the list
            oos.writeInt(savedDrawingPanelRooms.size());
            // Write each DrawingPanelRoom object separately
            for (DrawingPanelRoom room : savedDrawingPanelRooms) {
                oos.writeObject(room);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving the file.");
        }
    }

    public static SaveLoadHandler loadFromFile(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            SaveLoadHandler data = new SaveLoadHandler();

            // Read the size of the list
            int segmentSize = ois.readInt();
            // Read each DrawingPanelSegment object and add it to the list
            for (int i = 0; i < segmentSize; i++) {
                data.savedDrawingPanelSegments.add((DrawingPanelSegment) ois.readObject());
            }

            // Read the size of the list
            int roomSize = ois.readInt();
            // Read each DrawingPanelRoom object and add it to the list
            for (int i = 0; i < roomSize; i++) {
                data.savedDrawingPanelRooms.add((DrawingPanelRoom) ois.readObject());
            }

            return data;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while loading the file.");
            return null;
        }
    }


}
