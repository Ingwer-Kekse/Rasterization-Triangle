package com.cgvsu.rasterization;

import Jama.Matrix;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.*;

public class Rasterization {

    public static void drawRectangle(
            final GraphicsContext graphicsContext,
            final int x, final int y,
            final int width, final int height,
            final Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        for (int row = y; row < y + height; ++row)
            for (int col = x; col < x + width; ++col)
                pixelWriter.setColor(col, row, color);
    }

    public static void drawTriangle(final GraphicsContext graphicsContext,
                                    final int x_a, final int y_a,
                                    final int x_b, final int y_b,
                                    final int x_c, final int y_c,
                                    final Color[] color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        Color[] cols = {color[0], color[1], color[2]};

        int[][] points = {
                {x_a, y_a},
                {x_b, y_b},
                {x_c, y_c}
        };
        Integer[] order = {0, 1, 2};
        Arrays.sort(order, Comparator.comparingInt(i -> points[i][1]));

        int[] p0 = points[order[0]];
        int[] p1 = points[order[1]];
        int[] p2 = points[order[2]];

        Color c0 = cols[order[0]];
        Color c1 = cols[order[1]];
        Color c2 = cols[order[2]];
        int x_second_midpoint = finding_board_x(
                p0[0], p0[1],
                p2[0], p2[1],
                p1[1]
        );

        int[] secondMidpoint = {x_second_midpoint, p1[1]};
        drawHalfTriangle(
                p1, p0, secondMidpoint, p2,
                pixelWriter,
                new Color[]{c1, c0, c2}
        );
        drawHalfTriangle(
                p1, p2, secondMidpoint, p0,
                pixelWriter,
                new Color[]{c1, c2, c0}
        );
    }


    private static int finding_board_x(int x1, int y1, int x2, int y2, int board_y) {
        double x = x1 + (board_y - y1) / (double) (y2 - y1) * (x2 - x1);
        return (int) x;
    }

    private static void drawHalfTriangle(final int[] pointMid1,
                                         final int[] point2,
                                         final int[] pointMid2,
                                         final int[] point3,
                                         final PixelWriter pixelWriter, final Color[] color){
        ArrayList<int[]> listPoint = new ArrayList<>(Arrays.asList(pointMid1, point2, pointMid2));
        int[] min = Collections.min(listPoint, Comparator.comparingInt(a -> a[1]));
        int[] max = Collections.max(listPoint, Comparator.comparingInt(a -> a[1]));

        for(int y = min[1]; y <= max[1]; y++){
            int border_x_1 = finding_board_x(pointMid1[0], pointMid1[1], point2[0], point2[1], y);
            int border_x_2 = finding_board_x(pointMid2[0], pointMid2[1], point2[0], point2[1], y);
            for(int x = Math.min(border_x_1, border_x_2); x <= Math.max(border_x_1, border_x_2); x++){
                pixelWriter.setColor(x, y, interpolated(
                        pointMid1[0], pointMid1[1],
                        point2[0], point2[1],
                        point3[0], point3[1], x, y, color));
            }
        }
    }

    public static Color interpolated(final int x_a, final int y_a,
                                     final int x_b, final int y_b,
                                     final int x_c, final int y_c,
                                     final int point_x, final int point_y, Color[] C) {

        double[] bc = getBarCoords(x_a, y_a, x_b, y_b, x_c, y_c, point_x, point_y);
        if (bc == null) {
            return Color.TRANSPARENT; // рисует "в цвет фона"
        }
        double r = bc[0] * C[0].getRed() + bc[1] * C[1].getRed() + bc[2] * C[2].getRed();
        double g = bc[0] * C[0].getGreen() + bc[1] * C[1].getGreen() + bc[2] * C[2].getGreen();
        double b = bc[0] * C[0].getBlue() + bc[1] * C[1].getBlue() + bc[2] * C[2].getBlue();
        return new Color(fix(r), fix(g), fix(b), 1);
    }

    private static double fix(double v) {
        if (Double.isNaN(v)) return 0;
        return v < 0 ? 0 : v > 1 ? 1 : v;
    }

    private static double[] getBarCoords(int x_a, int y_a,
                                         int x_b, int y_b,
                                         int x_c, int y_c,
                                         int x, int y) {
        double det = (double)(x_b - x_a) * (y_c - y_a)
                - (double)(x_c - x_a) * (y_b - y_a);
        // проверка на вырожденный треугольник
        if (Math.abs(det) < 1e-9) {
            return null;
        }
        double[][] a = {
                {x_a, x_b, x_c},
                {y_a, y_b, y_c},
                {1,    1,    1}
        };
        double[][] b = {
                {x},
                {y},
                {1}
        };
        Matrix A = new Matrix(a);
        Matrix B = new Matrix(b);
        Matrix result = A.solve(B);
        return result.getColumnPackedCopy();
    }


}