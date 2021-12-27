package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    private SteppeJungleMap map;
    private IEngine engine;
    private ImageLoader images;

    @Override
    public void init() throws Exception {
        images = new ImageLoader();
        try {
            map = new SteppeJungleMap(15,15, 2, 40);
            engine = new SimulationEngine(map, 12, 50, 1);
            System.out.println(map);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MapGui mapGui = new MapGui(map, engine, images);
        mapGui.init();
        Scene scene = new Scene(mapGui.getParent(), 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread engineThread = new Thread((Runnable) engine);
        engineThread.start();
    }

}
