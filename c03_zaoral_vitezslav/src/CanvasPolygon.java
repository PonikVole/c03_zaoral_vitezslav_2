import fill.Filler;
import fill.ScanFiller;
import fill.SeedFillQueue;
import fill.SeedFiller;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.PolygonRasterizer;
import rasterize.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CanvasPolygon {

    private JPanel panel;
    private RasterBufferedImage raster;
    private int x, y;
    private Point p1 = null;
    private LineRasterizerGraphics rasterizer;
    private Polygon polygon = new Polygon();
    private PolygonRasterizer polygonRasterizer = new PolygonRasterizer();


    public CanvasPolygon(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());

        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBufferedImage(width, height);
        rasterizer = new LineRasterizerGraphics(raster);
        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    raster.clear();
                    Point p1 = new Point(e.getX(), e.getY());
                    polygon.addPoint(p1);
                    polygonRasterizer.draw(polygon, rasterizer, Color.cyan);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    Filler seedFiller = new SeedFiller(raster, e.getX(), e.getY(), Color.MAGENTA, new Color(0x2f2f2f));
                    seedFiller.fill();

                    /*
                    Filler seedFillQueue = new SeedFillQueue(raster, Color.MAGENTA, new Color(0x2f2f2f), new Point(e.getX(), e.getY()));
                    seedFillQueue.fill();
                    */
                }
                present(raster.getGraphics());
                panel.repaint();
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                raster.clear();
                polygonRasterizer.draw(polygon, rasterizer, Color.cyan);
                if (polygon.getPoints().size() > 0) {
                    Point last = polygon.getPoints().get(polygon.getCount() - 1);
                    rasterizer.drawLine(last.getX(), last.getY(), e.getX(), e.getY(), Color.cyan);
                }
                present(raster.getGraphics());
                panel.repaint();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'c') {
                    raster.clear();
                    polygon.getPoints().clear();
                    present(raster.getGraphics());
                    panel.repaint();
                }
                if (e.getKeyChar() == 'f') {
                    raster.clear();
                    Filler scanFiller = new ScanFiller(rasterizer, polygonRasterizer, polygon, Color.MAGENTA, Color.cyan);
                    scanFiller.fill();
                    present(raster.getGraphics());
                    panel.repaint();
                }
            }
        });

    }

    public void present(Graphics graphics) {
        raster.repaint(graphics);
    }

    public void start() {
        raster.clear();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasPolygon(800, 600).start());
    }

}
