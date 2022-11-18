package fill;

import model.Edge;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.PolygonRasterizer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScanFiller implements Filler {
    private final LineRasterizer lineRasterizer;
    private final PolygonRasterizer polygonRasterizer;
    private final Polygon polygon;
    private final Color fillColor;
    private final Color outlineColor;

    public ScanFiller(LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer, Polygon polygon, Color fillColor, Color outlineColor) {
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        this.polygon = polygon;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
    }

    @Override
    public void fill() {
        scanLine();
    }

    private void scanLine() {
        // init seznamu hran
        List<Edge> edges = new ArrayList<>();

        // projdu pointy a vytvořím z nich hrany
        for (int i = 0; i < polygon.getCount(); i++) {
            int nextIndex = (i + 1) % polygon.getCount();
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint(nextIndex);
            Edge edge = new Edge(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            // Pokud je horizontální, ignoruju
            if (edge.isHorizontal())
                continue;
            edge.orientate();

            // Přidám hranu do seznamu
            edges.add(edge);
        }

        int yMin = polygon.getPoint(0).getY();
        int yMax = yMin;
        for (Point p : polygon.getPoints()) {
            if (yMin > p.getY()) {
                yMin = p.getY();
            }
            if (yMax < p.getY()) {
                yMax = p.getY();
            }
        }

        for (int y = yMin; y <= yMax; y++) {
            // Vytvořit seznam průsečíků: List<Integer>

            // Projdu všechny hrany
            // {
            // Zjistim, jestli existuje průsečík s hranou
            // Pokud ano, spočítám a přidám do seznamu průsečíků
            // }

            // Seřadit průsečíky
            List<Integer> points = new ArrayList<>();
            for (Edge edge : edges) {
                if (edge.isIntersection(y)) {
                    points.add(edge.getIntersection(y));
                }
            }

            points.sort(Comparator.comparingInt(value -> value));
            // Vykreslit lines mezi lichými a sudými průsečíky
            for (int i = 1; i < points.size(); i += 2) {
                lineRasterizer.drawLine(points.get(i - 1), y, points.get(i), y, fillColor);
            }
        }

        // Obkreslit polygon
        polygonRasterizer.draw(polygon, lineRasterizer, outlineColor);
    }

}
