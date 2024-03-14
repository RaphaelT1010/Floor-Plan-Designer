package Application;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DrawingPanelFurniture implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Point furniturePosition;
    private double angle;
    private transient BufferedImage  spriteImage;


    public DrawingPanelFurniture(Point position, BufferedImage spriteImage, double angle) {
        this.furniturePosition = new Point(position);
        this.spriteImage = spriteImage;
        this.angle = angle;
    }

    public BufferedImage getImage(){
        return spriteImage;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Serialize other fields by default

        // Serialize BufferedImage by writing it as an image file
        ImageIO.write(spriteImage, "PNG", out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserialize other fields by default

        // Deserialize BufferedImage by reading it from the image file
        spriteImage = ImageIO.read(in);
    }

    public void rotate() {
        angle += Math.PI / 2; // Rotate by 90 degrees
    }

    public boolean contains(Point point) {
        return new Rectangle(furniturePosition.x, furniturePosition.y, spriteImage.getWidth(), spriteImage.getHeight()).contains(point);
    }

    public void moveBy(int dx, int dy) {
        furniturePosition.translate(dx, dy);
    }

    public Rectangle getBounds() {
        return new Rectangle(furniturePosition.x, furniturePosition.y, spriteImage.getWidth(), spriteImage.getHeight());
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(angle, furniturePosition.x + spriteImage.getWidth() / 2.0, furniturePosition.y + spriteImage.getHeight() / 2.0);
        g2d.drawImage(spriteImage, furniturePosition.x, furniturePosition.y, null);
        g2d.dispose();
    }

    public Point getFurniturePosition() {
        return furniturePosition;
    }

    public double getAngle(){
        return angle;
    }

}
