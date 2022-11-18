import model.Point;
import rasterize.LineRasterizerGraphics;
import rasterize.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CanvasDashedLine {

    private JPanel panel;
    private RasterBufferedImage raster;
    private int x,y;
    private Point p1 = null;
    private LineRasterizerGraphics rasterizer;
    //private DrawDashedLine drawDashedLine;

    public CanvasDashedLine(int width, int height) {
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
            public void mousePressed(MouseEvent e) {
                p1 = new Point(e.getX(), e.getY());
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                raster.clear();
                Point p2 = new Point(e.getX(), e.getY());
                rasterizer.drawDashedLine(raster.getGraphics(), p1.getX(), p1.getY(), p2.getX(), p2.getY(), Color.cyan);
                present(raster.getGraphics());
                panel.repaint();
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
        SwingUtilities.invokeLater(() -> new CanvasDashedLine(800, 600).start());
    }

}
