package Application;
import java.awt.*;
import java.io.Serializable;

public class DrawingPanelRoom implements Serializable {
    private static final long serialVersionUID = 1L; // Version ID for serialization
    private final Color color;

    private Point startPoint;
    private Point endPoint;

    public DrawingPanelRoom(Color color){
        this.color = color;
    }

    public void setStartingPoint(Point startPoint){
        this.startPoint = startPoint;
    }

    public Point getStartingPoint(){
        return startPoint;
    }

    public void setEndingPoint(Point endPoint){
        this.endPoint = endPoint;
    }

    public Point getEndingPoint(){
        return endPoint;
    }

    public int getWidth(){
        return endPoint.x - startPoint.x;
    }

    public int getHeight(){
        return endPoint.y - startPoint.y;
    }





}
