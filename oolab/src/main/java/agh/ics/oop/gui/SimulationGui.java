package agh.ics.oop.gui;

import agh.ics.oop.IPausable;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.SteppeJungleMap;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SimulationGui {
    private SteppeJungleMap map;
    private SimulationEngine engine;
    private ImageLoader images;
    private Pane rootBox;

    public SimulationGui(ImageLoader images, int height, int width, int jungleRatio, int grassEnergy, int initialPopulation,
                         int startEnergy, int moveEnergy) {
        this.images = images;
        try {
            map = new SteppeJungleMap(height, width, jungleRatio, grassEnergy);
            engine = new SimulationEngine(map, initialPopulation, startEnergy, moveEnergy);
            System.out.println(map);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        MapGui mapGui = new MapGui(map, engine, images);
        mapGui.init();
        ToggleButton pauseButton = createPauseButton(engine, "pause");

        VBox mapBox = new VBox();
        mapBox.getChildren().addAll(mapGui.getParent(), pauseButton);

        rootBox = new HBox();
        StatisticsGui statisticsGui = new StatisticsGui(engine);

        rootBox.getChildren().addAll(mapBox, statisticsGui.getRoot());
    }

    public void start(){
        Thread engineThread = new Thread((Runnable) engine);
        engineThread.start();
    }

    public Pane getRoot(){
        return rootBox;
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
