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
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 0, 100, 300, 300, 100, new Color[]{Color.RED, Color.BLUE, Color.GREEN});

        //остроугольный разносторонний
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 220, 160, 100, 240, 200, new Color[]{Color.RED, Color.BLUE, Color.GREEN});

        //прямоугольный разносторонний
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 100, 100, 300, 300, 200, new Color[]{Color.RED, Color.BLUE, Color.GREEN});

        //остроугольный разносторонний
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 100, 300, 100, 200, 200, new Color[]{Color.RED, Color.BLUE, Color.GREEN});

        //остроугольный разносторонний
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 150, 100, 100, 200, 200, 200, new Color[]{Color.RED, Color.BLUE, Color.GREEN});

        //прямоугольный
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 100, 100, 300, 300, 300, new Color[]{Color.RED, Color.BLUE, Color.GREEN});
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 300, 100, 500, 100, 300, 300, new Color[]{Color.RED, Color.BLUE, Color.GREEN});
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 500, 500, 500, 300, 300, 300, new Color[]{Color.RED, Color.BLUE, Color.GREEN});
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 300, 500, 100, 500, 300, 300, new Color[]{Color.RED, Color.BLUE, Color.GREEN});

        //линия х
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 100, 100, 200, 100, 300, Color.AQUA);

        //линия у
        //Rasterization.drawTriangle(canvas.getGraphicsContext2D(), 100, 100, 200, 100, 300, 100, new Color[]{Color.RED, Color.BLUE, Color.GREEN});
    }

}