package fill;

import model.Point;
import rasterize.Raster;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class SeedFillQueue implements Filler {
    private final Raster raster;
    private final HashSet<Point> visited = new HashSet<>();
    private final Queue<Point> queue = new ArrayDeque<>();
    private final Color fillColor, backgroundColor;
    private final Point seed;

    public SeedFillQueue(Raster raster, Color fillColor, Color backgroundColor, Point seed) {
        this.raster = raster;
        this.fillColor = fillColor;
        this.backgroundColor = backgroundColor;
        this.seed = seed;
        queue.add(seed);
    }

    @Override
    public void fill() {
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            if (point.getX() < 0 || point.getX() > raster.getWidth() || point.getY() < 0 || point.getY() > raster.getHeight())
                continue;
            if (!raster.getColor(point.getX(), point.getY()).equals(backgroundColor) || visited.contains(point))
                continue;
            visited.add(point);

            raster.setPixel(point.getX(), point.getY(), fillColor);

            // LEFT
            queue.add(new Point(point.getX() - 1, point.getY()));
            // RIGHT
            queue.add(new Point(point.getX() + 1, point.getY()));
            // UP
            queue.add(new Point(point.getX(), point.getY() - 1));
            // DOWN
            queue.add(new Point(point.getX(), point.getY() + 1));

        }

    }
}
