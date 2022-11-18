package rasterize;

import model.Point;

import java.awt.*;

public class Triangle {
    private Point[] points = new Point[3];

    public void setPoint(int index, Point point) {
        points[index] = point;
    }

    public Point[] getPoints() {
        return points;
    }

    public void draw(LineRasterizer rasterizer, Color color) {
        rasterizer.drawLine(points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY(), color);

        double xvec = points[0].getX() - points[1].getX();
        double yvec = points[0].getY() - points[1].getY();
        xvec *= -0.5;
        yvec *= -0.5;

        double midPointX = points[0].getX() + xvec;
        double midPointY = points[0].getY() + yvec;

        // base slope
        double k1 = (points[1].getY() - points[0].getY()) / (double) (points[1].getX() - points[0].getX());

        // Parallel
        double q2 = points[2].getY() - k1 * points[2].getX();

        // Perpendicular
        double k3 = -1.0 / k1;
        double q3 = midPointY - k3 * midPointX;

        // Calculate x, y for point that lays on parallel and perpendicular
        double x = (q3 - q2) / (k1 - k3);
        double y = k1 * x + q2;

        rasterizer.drawLine(points[0].getX(), points[0].getY(), (int) Math.round(x), (int) Math.round(y), color);
        rasterizer.drawLine(points[1].getX(), points[1].getY(), (int) Math.round(x), (int) Math.round(y), color);
    }

}

