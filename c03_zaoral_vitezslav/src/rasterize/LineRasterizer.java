package rasterize;

import model.Line;

import java.awt.*;

public abstract class LineRasterizer {
    Raster raster;
    Color color;

    public LineRasterizer(Raster raster){
        this.raster = raster;
    }

    public LineRasterizer() {

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(int color) {
        this.color = new Color(color);
    }

    public void rasterize(Line line) {
        drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2(), line.getColor());
    }

    public void rasterize(int x1, int y1, int x2, int y2, Color color) {
        drawLine(x1, y1, x2, y2, color);
    }

    public void drawLine(int x1, int y1, int x2, int y2, Color color) {

    }

    public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2, Color color){

        // Create a copy of the Graphics instance
        Graphics2D g2d = (Graphics2D) g.create();

        // Set the stroke of the copy, not the original
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        // Draw to the copy
        g2d.setColor(color);
        g2d.drawLine(x1, y1, x2, y2);
        // Get rid of the copy
        g2d.dispose();
    }
    //source: https://stackoverflow.com/questions/21989082/drawing-dashed-line-in-java
}
