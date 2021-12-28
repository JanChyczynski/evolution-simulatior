package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private SteppeJungleMap map;
    private SimulationEngine engine;
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
        ToggleButton pauseButton = createPauseButton(engine, "pause");

        VBox mapBox = new VBox();
        mapBox.getChildren().addAll(mapGui.getParent(), pauseButton);

        HBox rootBox = new HBox();
        StatisticsGui statisticsGui = new StatisticsGui(engine);

        rootBox.getChildren().addAll(mapBox, statisticsGui.getRootBox());
        Scene scene = new Scene(rootBox, 1400, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread engineThread = new Thread((Runnable) engine);
        engineThread.start();
    }

    private ToggleButton createPauseButton(IPausable pausable, String text) {
        ToggleButton pauseButton = new ToggleButton(text);
        ToggleGroup pauseGroup = new ToggleGroup();
        pauseButton.setToggleGroup(pauseGroup);
        pauseGroup.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
            if (new_toggle == null) {
                pausable.resume();
            } else {
                pausable.pause();
            }
        });
        return pauseButton;
    }

}
