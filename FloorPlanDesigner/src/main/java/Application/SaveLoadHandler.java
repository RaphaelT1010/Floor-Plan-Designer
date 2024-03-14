package Application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveLoadHandler implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<DrawingPanelSegment> savedDrawingPanelSegments = new ArrayList<>();
    private List<DrawingPanelRoom> savedDrawingPanelRooms = new ArrayList<>();
    private List <DrawingPanelFurniture> savedDrawingPanelFurniture = new ArrayList<>();



    public SaveLoadHandler(){

    }
    public void saveDrawingPanel(){
        savedDrawingPanelSegments = DrawingPanel.getInstance().drawingPanelSegments;
        savedDrawingPanelRooms = DrawingPanel.getInstance().drawingPanelRooms;
        savedDrawingPanelFurniture = DrawingPanel.getInstance().drawingPanelFurniture;
    }

    public List<DrawingPanelSegment> getSavedDrawingPanelSegments() {
        return savedDrawingPanelSegments;
    }

    public List<DrawingPanelRoom> getSavedDrawingPanelRooms() {
        return savedDrawingPanelRooms;
    }

    public List<DrawingPanelFurniture> getSavedDrawingPanelFurniture() {
        return savedDrawingPanelFurniture;
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

            oos.writeInt(savedDrawingPanelFurniture.size());
            for (DrawingPanelFurniture furniture : savedDrawingPanelFurniture) {
                // Serialize the BufferedImage separately
                oos.writeObject(furniture.getFurniturePosition());
                oos.writeDouble(furniture.getAngle());

                // Serialize the BufferedImage separately
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(furniture.getImage(), "PNG", baos);
                baos.flush();
                byte[] imageBytes = baos.toByteArray();
                baos.close();
                oos.writeObject(imageBytes);
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

            int furnitureSize = ois.readInt();
            // Read each DrawingPanelSegment object and add it to the list
            for (int i = 0; i < furnitureSize; i++) {
                // Deserialize other attributes of DrawingPanelFurniture
                Point position = (Point) ois.readObject();
                double angle = ois.readDouble();

                // Deserialize the BufferedImage separately
                byte[] imageBytes = (byte[]) ois.readObject();
                ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                BufferedImage image = ImageIO.read(bais);
                bais.close();

                // Create a new DrawingPanelFurniture object with deserialized attributes
                data.savedDrawingPanelFurniture.add(new DrawingPanelFurniture(position, image, angle));
            }

            return data;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while loading the file.");
            return null;
        }
    }


}
