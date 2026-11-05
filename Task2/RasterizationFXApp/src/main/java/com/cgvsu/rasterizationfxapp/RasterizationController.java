package com.cgvsu.rasterizationfxapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

import com.cgvsu.rasterization.*;
import javafx.scene.paint.Color;

public class RasterizationController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        //произвольный
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 0, 100, 300, 300, 100, Color.AQUA);

        //остроугольный разносторонний
        //Rasterization.drawTriangle_1(canvas.getGraphicsContext2D(), 100, 220, 160, 100, 240, 200, Color.AQUA);

        //прямоугольный разносторонний
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 100, 300, 200, 100, 300, Color.RED);

        //остроугольный разносторонний
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 100, 300, 100, 200, 200, Color.AQUA);

        //остроугольный разносторонний
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 150, 100, 100, 200, 200, 200, Color.AQUA);

        //линия х
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 100, 100, 200, 100, 300, Color.AQUA);

        //линия у
        Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 100, 200, 100, 300, 100, Color.AQUA);
    }

}