package com.cgvsu.rasterization;

import Jama.Matrix;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;

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

        //dot border box  (format [x, y])
        int border_y_up = Math.min(Math.min(y_a, y_b), y_c);
        int border_y_down = Math.max(Math.max(y_a, y_b), y_c);
        int border_x_left = Math.min(Math.min(x_a, x_b), x_c);
        int border_x_right = Math.max(Math.max(x_a, x_b), x_c);


        //finding intersection const y and triangle
        for (int y = border_y_up; y <= border_y_down; y++) {
            if ((y_a != y_b) || (y_b != y_c)) {
                int intersection_ab = finding_board_x(x_a, y_a, x_b, y_b, y);
                int intersection_bc = finding_board_x(x_b, y_b, x_c, y_c, y);
                int intersection_ca = finding_board_x(x_c, y_c, x_a, y_a, y);

                ArrayList<Integer> borders_x = dots_in_box(intersection_ab, intersection_bc, intersection_ca, border_x_left, border_x_right);
                if (!borders_x.isEmpty()) {
                    int xLeft = Collections.min(borders_x);
                    int xRight = Collections.max(borders_x);

                    for (int x = xLeft; x <= xRight; x++) {
                        pixelWriter.setColor(x, y, interpolated(x_a, y_a, x_b, y_b, x_c, y_c, x, y, color));
                    }
                }
            } else {
                for (int x = border_x_left; x <= border_x_right; x++) {
                    pixelWriter.setColor(x, y, interpolated(x_a, y_a, x_b, y_b, x_c, y_c, x, y, color));
                }
            }
        }

    }

    //point membership test
    private static ArrayList<Integer> dots_in_box(int dot1, int dot2, int dot3, int left, int right) {
        ArrayList<Integer> answer = new ArrayList<>();

        if (left <= dot1 && dot1 <= right) {
            answer.add(dot1);
        }
        if (left <= dot2 && dot2 <= right) {
            answer.add(dot2);
        }
        if (left <= dot3 && dot3 <= right) {
            answer.add(dot3);
        }

        return answer;
    }

    private static Integer finding_board_x(int x1, int y1, int x2, int y2, int board_y) {
        if (y1 == y2) {
            return null;
        }
        double x = x1 + (board_y - y1) / (double) (y2 - y1) * (x2 - x1);
        return (int) Math.round(x);
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