package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;

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
    public static void drawTriangle(final GraphicsContext graphicsContext,
                                    final int x_a, final int y_a,
                                    final int x_b, final int y_b,
                                    final int x_c, final int y_c,
                                    final Color color){
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        //dot border box  (format [x, y])
        int[] dot_left_up = {Math.min(Math.min(x_a, x_b), x_c), Math.min(Math.min(y_a, y_b), y_c)};
        int[] dot_left_down = {Math.min(Math.min(x_a, x_b), x_c), Math.max(Math.max(y_a, y_b), y_c)};

        //finding midpoints
        int midpoin_x = 0;
        int midpoin_y = median(y_a, y_b, y_c);
        int board_x = 0;

        //and draw triangle

        if(midpoin_y == y_a){
            midpoin_x = x_a;
            drawHalfTriangle(midpoin_x, midpoin_y, x_b, y_b, x_c, y_c, dot_left_up[1], pixelWriter, color);
            drawHalfTriangle(midpoin_x, midpoin_y, x_b, y_b, x_c, y_c, dot_left_down[1], pixelWriter, color);
        }
        else if(midpoin_y == y_b){
            midpoin_x = x_b;
            drawHalfTriangle(midpoin_x, midpoin_y, x_a, y_a, x_c, y_c, dot_left_up[1], pixelWriter, color);
            drawHalfTriangle(midpoin_x, midpoin_y, x_a, y_a, x_c, y_c, dot_left_down[1], pixelWriter, color);
        }
        else if(midpoin_y == y_c){
            midpoin_x = x_c;
            drawHalfTriangle(midpoin_x, midpoin_y, x_b, y_b, x_a, y_a, dot_left_up[1], pixelWriter, color);
            drawHalfTriangle(midpoin_x, midpoin_y, x_b, y_b, x_a, y_a, dot_left_down[1], pixelWriter, color);
        }

    }

    //XOR
    private static int median(int a, int b, int c) {
        int[] answer = {a, b, c};
        Arrays.sort(answer);
        return answer[1];
    }

    private static int[] finding_board_x(int x_m, int y_m, int x2, int y2, int x3, int y3, int board_y){
        int board_x1 = 0;
        int board_x2 = 0;
        int maxY = Math.max(y2, y3);
        int minY = Math.min(y2, y3);
        int maxX = Math.max(x2, x3);
        int minX = Math.min(x2, x3);

        if(board_y <y_m){
            board_x1 = ((x_m - minX)*(board_y-minY))/(y_m-minY) + minX;
            board_x2 = ((maxX - minX)*(board_y-minY))/(maxY-minY) + minX;
        }else{
            board_x1 = ((maxX - minX)*(board_y-minY))/(maxY-minY) + minX;
            board_x2 = ((maxX - x_m)*(board_y-y_m))/(maxY-y_m) + minX;
        }

        return new int[]{Math.min(board_x1, board_x2), Math.max(board_x1, board_x2)};
    }

    private static void drawHalfTriangle(int midpoin_x, int midpoin_y,
                                         int x_b, int y_b, int x_c, int y_c,
                                         int y_half, PixelWriter pixelWriter, Color color){
        int[] range_x = {};
        for (int y=Math.min(midpoin_y, y_half); y <= Math.max(midpoin_y, y_half);y++){
            range_x = finding_board_x(midpoin_x, midpoin_y, x_b, y_b, x_c, y_c, y);
            for(int x=range_x[0]; x <= range_x[1]; x++){
                pixelWriter.setColor(x, y, color);
            }
        }
    }

    public static void drawTriangle_1(GraphicsContext gc,
                                    int x1, int y1,
                                    int x2, int y2,
                                    int x3, int y3,
                                    Color color) {
        PixelWriter pw = gc.getPixelWriter();

        int minY = Math.min(y1, Math.min(y2, y3));
        int maxY = Math.max(y1, Math.max(y2, y3));

        int[][] edges = {
                {x1, y1, x2, y2},
                {x2, y2, x3, y3},
                {x3, y3, x1, y1}
        };

        for (int y = minY; y <= maxY; y++) {
            double minX = Double.MAX_VALUE;
            double maxX = -Double.MAX_VALUE;

            for (int[] e : edges) {
                int xa = e[0], ya = e[1];
                int xb = e[2], yb = e[3];

                if (ya == yb) {
                    if (y == ya) {
                        minX = Math.min(minX, Math.min(xa, xb));
                        maxX = Math.max(maxX, Math.max(xa, xb));
                    }
                } else if (y >= Math.min(ya, yb) && y <= Math.max(ya, yb)) {
                    double x = xa + (double)(xb - xa) * (y - ya) / (yb - ya);
                    minX = Math.min(minX, x);
                    maxX = Math.max(maxX, x);
                }
            }

            if (minX <= maxX) {
                int startX = (int)Math.ceil(minX);
                int endX = (int)Math.floor(maxX);

                for (int x = startX; x <= endX; x++) {
                    pw.setColor(x, y, color);
                }
            }
        }
    }
}
