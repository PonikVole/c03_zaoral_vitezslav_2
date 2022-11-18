package rasterize;

import model.Line;
import model.Point;
import model.Polygon;

import java.awt.*;
import java.util.List;

public class PolygonRasterizer {
    public void draw(Polygon polygon, LineRasterizer liner, Color color) {
        List<Point> points = polygon.getPoints();
        for (int i = 1; i < points.size(); i++) {
            Point previous = points.get(i - 1);
            Point current = points.get(i);
            liner.drawLine(previous.getX(), previous.getY(), current.getX(), current.getY(), color);
        }
        Point last = points.get(points.size() - 1);
        Point first = points.get(0);
        liner.drawLine(last.getX(), last.getY(), first.getX(), first.getY(), color);
    }


}
