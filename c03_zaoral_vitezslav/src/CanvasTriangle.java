import model.Point;
import rasterize.LineRasterizerGraphics;
import rasterize.RasterBufferedImage;
import rasterize.Triangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CanvasTriangle {

    private JPanel panel;
    private RasterBufferedImage raster;
    private int x1,y1;
    private Point p1 = null;
    private LineRasterizerGraphics rasterizer;
    Triangle triangle = new Triangle();
    int settingPoint = 0;

    public CanvasTriangle(int width, int height) {
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

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                raster.clear();
                triangle.setPoint(2, new Point(e.getX(), e.getY()));
                triangle.draw(rasterizer, Color.cyan);
                present(raster.getGraphics());
                panel.repaint();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                if (settingPoint == 2) settingPoint = 0;
                triangle.setPoint(settingPoint, new Point(x1, y1));
                settingPoint++;
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == 'c'){
                    raster.clear();
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
        SwingUtilities.invokeLater(() -> new CanvasTriangle(800, 600).start());
    }

}
