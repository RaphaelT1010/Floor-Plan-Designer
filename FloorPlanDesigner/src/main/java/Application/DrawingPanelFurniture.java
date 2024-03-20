package Application;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
    private double originalWidth;
    private double originalHeight;


    public DrawingPanelFurniture(Point position, BufferedImage spriteImage, double angle) {
        this.furniturePosition = new Point(position);
        this.spriteImage = spriteImage;
        this.angle = angle;
        this.originalWidth = spriteImage.getWidth();
        this.originalHeight = spriteImage.getHeight();
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

    public void increaseSize(){
        double scaleFactor = 1.1; // Scaling factor for increasing size

        // Create a scaling transformation
        AffineTransform transform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        // Apply the transformation to the image
        spriteImage = op.filter(spriteImage, null);
    }

    public void decreaseSize(){

        if (spriteImage.getHeight() * .9 >= originalHeight || spriteImage.getWidth() * .9 >= originalWidth){
            double scaleFactor = .9; // Scaling factor for decreasing size

            // Create a scaling transformation
            AffineTransform transform = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

            // Apply the transformation to the image
            spriteImage = op.filter(spriteImage, null);
        }

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
