package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    private AbstractWorldMap map;
    private IEngine engine;

    @Override
    public void init() throws Exception {
        try {
            MoveDirection[] directions = new OptionParser().parse(getParameters().getRaw().toArray(new String[0]));
            map = new GrassField(10);
            Vector2d[] positions = {new Vector2d(0, 3), new Vector2d(6, 10)};
            engine = new SimulationEngine(directions, map, positions);
            System.out.println(map);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MapGui mapGui = new MapGui(map, engine);
        mapGui.init();
        Scene scene = new Scene(mapGui.getParent(), 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread engineThread = new Thread((Runnable) engine);
        engineThread.start();
    }

}
