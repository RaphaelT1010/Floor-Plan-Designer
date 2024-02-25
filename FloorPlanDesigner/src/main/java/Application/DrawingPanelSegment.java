package Application;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanelSegment {
    private List<Point> points;
    private Color color;

    public DrawingPanelSegment(Color color) {
        this.points = new ArrayList<>();
        this.color = color;
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
}
