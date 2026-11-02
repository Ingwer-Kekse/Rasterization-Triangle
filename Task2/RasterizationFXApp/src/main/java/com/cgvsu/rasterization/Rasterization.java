package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rasterization {

    public static void drawRectangle(
            final GraphicsContext graphicsContext,
            final int x, final int y,
            final int width, final int height,
            final Color color)
    {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        for (int row = y; row < y + height; ++row)
            for (int col = x; col < x + width; ++col)
                pixelWriter.setColor(col, row, color);
    }
    public static void drawTriangle(final int x_a, final int y_a,
                                    final int x_b, final int y_b,
                                    final int x_c, final int y_c,
                                    final int[] color_list){
        //dot border box  (format [x, y])
        int[] dot_left_up = {Math.min(Math.min(x_a, x_b), x_c), Math.min(Math.min(y_a, y_b), y_c)};
        int[] dot_left_down = {Math.min(Math.min(x_a, x_b), x_c), Math.max(Math.max(y_a, y_b), y_c)};
        int[] dot_right_up = {Math.max(Math.max(x_a, x_b), x_c), Math.min(Math.min(y_a, y_b), y_c)};
        int[] dot_right_down = {Math.max(Math.max(x_a, x_b), x_c), Math.max(Math.max(y_a, y_b), y_c)};

        //finding midpoint
        int midpoin_x = 0;
        int midpoin_y = median(y_a, y_b, y_c);

        if(midpoin_y == y_a){midpoin_x = x_a;}
        if(midpoin_y == y_b){midpoin_x = x_b;}
        if(midpoin_y == y_c){midpoin_x = x_c;}
    }

    //XOR
    private static int median(int a, int b, int c) {
        if ((a - b) * (b - c) > 0) return b;
        if ((b - a) * (a - c) > 0) return a;
        return c;
    }

    //TODO: create method for filling in half triangle(scanline), add barycentric coordinates
}
