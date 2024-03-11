package Application;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanelSegment implements Serializable {
    private static final long serialVersionUID = 1L; // Version ID for serialization
    private List<Point> points;
    private Color color;

    private int strokeSize;

    public DrawingPanelSegment(Color color, int strokeSizeParameter) {
        this.points = new ArrayList<>();
        this.color = color;
        this.strokeSize = strokeSizeParameter;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point getStartingPoint(){
        return points.get(0);
    }


    public Color getColor() {
        return color;
    }

    public int getStrokeSize(){
        return strokeSize;
    }
}
