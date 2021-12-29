package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SimulationGui {
    private SteppeJungleMap map;
    private SimulationEngine engine;
    private final ImageLoader images;
    private final Pane rootBox;
    private final StatisticsToFileGui toFileGui;

    public SimulationGui(ImageLoader images, SimulationParameters params, int width, int height, boolean wrapped) {
        this.images = images;
        setupSimulation(params, wrapped);

        MapGui mapGui = new MapGui(map, engine, images, params,(width)*13/20, (height)*16/20);
        mapGui.init();
        HBox buttonsBox = createButtonsBox(mapGui);
        toFileGui = new StatisticsToFileGui(engine);
        VBox mapBox = new VBox();
        mapBox.getChildren().addAll(mapGui.getRoot(), buttonsBox, toFileGui.getRoot());

        rootBox = new HBox();
        StatisticsGui statisticsGui = new StatisticsGui(engine);

        rootBox.getChildren().addAll(mapBox, statisticsGui.getRoot());
    }

    private void setupSimulation(SimulationParameters params, boolean wrapped) {
        try {
            map = new SteppeJungleMap(params);
            engine = new SimulationEngine(map, params);
            System.out.println(map);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        map.setWrapped(wrapped);
    }

    private HBox createButtonsBox(MapGui mapGui) {
        ToggleButton pauseButton = createPauseButton(engine, "pause");
        ToggleButton topGenomeButton = createTopGenomeButton(mapGui, "mark top genome");
        ToggleButton autoRepopulateButton = createAutoRepopulateButton(engine, "magic auto-repopulation");
        HBox buttonsBox = new HBox();
        buttonsBox.getChildren().addAll(pauseButton, topGenomeButton, autoRepopulateButton);
        return buttonsBox;
    }

    public void start(){
        Thread engineThread = new Thread(engine);
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

    private ToggleButton createTopGenomeButton(MapGui mapGui, String text) {
        ToggleButton topGenomeButton = new ToggleButton(text);
        ToggleGroup group = new ToggleGroup();
        topGenomeButton.setToggleGroup(group);
        group.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
            if (new_toggle != null) {
                mapGui.setMarkGenome(true);
            } else {
                mapGui.setMarkGenome(false);
            }
        });
        return topGenomeButton;
    }

    private ToggleButton createAutoRepopulateButton(SimulationEngine engine, String text) {
        ToggleButton topGenomeButton = new ToggleButton(text);
        ToggleGroup group = new ToggleGroup();
        topGenomeButton.setToggleGroup(group);
        group.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
            if (new_toggle != null) {
                engine.setAutoRepopulation(3);
            } else {
                engine.setAutoRepopulation(0);
            }
        });
        return topGenomeButton;
    }
}
