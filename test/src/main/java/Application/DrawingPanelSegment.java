package Application;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanelSegment implements Serializable {
    private static final long serialVersionUID = 1L; // Version ID for serialization
    private Color color;
    private Point startPoint;
    private Point endPoint;

    private int strokeSize;

    public DrawingPanelSegment(Color color, int strokeSizeParameter) {
        this.color = color;
        this.strokeSize = strokeSizeParameter;
    }

    public void addStartingPoint(Point point) {startPoint = point;}
    public void addEndingPoint(Point point) {endPoint = point;}



    public List<Point> getPoints() {
        List<Point> pointsList = new ArrayList<>();

        // Add starting and ending points to the list
        pointsList.add(startPoint);
        pointsList.add(endPoint);

        return pointsList;
    }

    public Point getStartingPoint(){
        return startPoint;
    }

    public Point getEndingPoint(){
        return endPoint;
    }


    public Color getColor() {
        return color;
    }

    public int getStrokeSize(){
        return strokeSize;
    }
}
