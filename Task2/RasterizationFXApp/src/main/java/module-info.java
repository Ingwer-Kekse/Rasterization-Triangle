module com.cgvsu.rasterizationfxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jama;


    opens com.cgvsu.rasterizationfxapp to javafx.fxml;
    exports com.cgvsu.rasterizationfxapp;
    exports com.cgvsu.rasterization;
    opens com.cgvsu.rasterization to javafx.fxml;
}
