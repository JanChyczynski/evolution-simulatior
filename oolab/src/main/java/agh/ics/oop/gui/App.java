package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {
    private ImageLoader images;

    @Override
    public void init() throws Exception {
        images = new ImageLoader();
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SimulationGui sim1 = new SimulationGui(images, 15,15, 2, 40,
                12, 50, 1);
        SimulationGui sim2 = new SimulationGui(images, 15,15, 2, 40,
                12, 50, 1);

        HBox rootBox = new HBox();
        rootBox.getChildren().addAll(sim1.getRoot(), sim2.getRoot());

        Scene scene = new Scene(rootBox, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();

        sim1.start();
        sim2.start();
    }
}
