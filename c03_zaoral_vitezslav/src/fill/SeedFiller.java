package fill;

import rasterize.Raster;

import java.awt.*;

public class SeedFiller implements Filler {
    private final Raster raster;
    private final int x, y;
    private final Color fillColor, backgroundColor;

    public SeedFiller(Raster raster, int x, int y, Color fillColor, Color backgroundColor) {
        this.raster = raster;
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void fill() {
        seedFill(x, y);
    }

    private void seedFill(int x, int y) {
        Color pixelColor = raster.getColor(x, y);
        if (!pixelColor.equals(backgroundColor))
            return;

        raster.setPixel(x, y, fillColor);
        seedFill(x, y - 1);
        seedFill(x, y + 1);
        seedFill(x + 1, y);
        seedFill(x - 1, y);
    }

}
