package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.time.Clock;
import java.util.NoSuchElementException;

import static java.lang.Math.min;

public class MapGui implements IMapStateObserver {
    public int cellSize;
    public int cellHeight;
    public int cellWidth;
    private static final long MIN_REFRESH_DELAY = 30;
    private int fullEnergy;
    private final AbstractWorldMap map;
    private final GridPane grid;
    private final ImageLoader images;
    private final SimulationEngine engine;
    private boolean markGenome;
    private Genome markedGenome;
    private TrackerGui tracker;
    private final VBox root;
    private long lastRefresh;

    public MapGui(AbstractWorldMap map, SimulationEngine engine, ImageLoader images, SimulationParameters params, int width, int height) {
        this.map = map;
        cellWidth = width/(map.getWidth()+1);
        cellHeight = height/(map.getHeight()+1);
        cellSize = min(cellWidth, cellHeight);

        fullEnergy = params.startEnergy()+3*params.grassEnergy();
        this.images = images;
        this.engine = engine;
        markGenome = false;
        tracker = new TrackerGui();
        lastRefresh = Clock.systemDefaultZone().millis();

        grid = new GridPane();
        grid.setGridLinesVisible(true);
        root = new VBox();
        root.getChildren().addAll(grid, tracker.getRoot());

        engine.addMapStateObserver(this);
        setCellSize(cellWidth, cellHeight);
    }

    public void init(){
       showColumnsAndRowsLabels();
       showMap(new Vector2d(1, 1));
    }

    public Pane getRoot(){
        return root;
    }

    public void showMap(Vector2d origin) {
        tracker.refresh();
        if(markGenome){
            markedGenome = engine.getTopGenome();
        }
        for (int i = map.bottomLeftCorner().x(); i <= map.upperRightCorner().x(); i++) {
            for (int j = map.bottomLeftCorner().y(); j <= map.upperRightCorner().y(); j++) {
                try {
                    showMapElement(map.objectAt(new Vector2d(i, j)).representingElement(),
                            origin.x() + i - map.bottomLeftCorner().x(),
                            origin.y() + map.upperRightCorner().y() - j, grid);
                } catch (NoSuchElementException | NullPointerException ignored) {
                }

            }
        }
    }

    public void showColumnsAndRowsLabels() {
        addStringAsCenteredLabel("y/x", 0, 0, grid);
        for (int i = map.bottomLeftCorner().x(); i <= map.upperRightCorner().x(); i++) {
            addStringAsCenteredLabel(Integer.toString(i), i + 1 - map.bottomLeftCorner().x(), 0, grid);
        }
        for (int i = map.bottomLeftCorner().y(); i <= map.upperRightCorner().y(); i++) {
            addStringAsCenteredLabel(Integer.toString(i), 0, map.upperRightCorner().y() + 1 - i, grid);
        }
    }

    public void setCellSize(int width, int height) {
        for (int i = map.bottomLeftCorner().x(); i <= map.upperRightCorner().x() + 1; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(width));
        }
        for (int i = map.bottomLeftCorner().y(); i <= map.upperRightCorner().y() + 1; i++) {
            grid.getRowConstraints().add(new RowConstraints(height));
        }
    }

    public void showMapElement(IMapElement element, int i, int j, GridPane grid) {
        Image image = images.getImage(element.representationImagePath());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(cellSize);
        imageView.setFitHeight(cellSize);
        expressEnergy(element, imageView);
        imageView.setRotate(360/MapDirection.values().length * element.getOrientation().toInt());
        expressMarkedGene(element, imageView);
        Button button = new Button();
        button.setGraphic(imageView);
        button.setBackground(null);
        if(element instanceof ITrackable){
            button.setOnAction(event -> {
                tracker.track((ITrackable) element, engine.getDay());

            });
        }

        grid.add(button, i, j, 1, 1);
        GridPane.setHalignment(button, HPos.CENTER);
    }

    private void expressMarkedGene(IMapElement element, ImageView imageView) {
        if(markGenome &&
                map.objectAt(element.getPosition()).stream().anyMatch(el ->
                        el instanceof Animal && ((Animal) el).getGenome().equals(markedGenome)))
        {
            ColorAdjust markedHue = imageView.getEffect() instanceof ColorAdjust?
                    (ColorAdjust) imageView.getEffect() :
                    new ColorAdjust();
            markedHue.setHue(2);
            imageView.setEffect(markedHue);
        }
    }

    private void expressEnergy(IMapElement element, ImageView imageView) {
        ColorAdjust energySaturation = new ColorAdjust();
        energySaturation.setSaturation(((double) element.getEnergy() / fullEnergy - 0.5) * 2);
        imageView.setEffect(energySaturation);
    }

    public void addStringAsCenteredLabel(String objectsRepresentation, int i, int j, GridPane grid) {
        Label label = new Label(objectsRepresentation);
        grid.add(label, i, j, 1, 1);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    @Override
    public void mapStateChanged() {
        if(Clock.systemDefaultZone().millis()-lastRefresh >= MIN_REFRESH_DELAY) {
            lastRefresh = Clock.systemDefaultZone().millis();
            Platform.runLater(() -> {
                grid.getChildren().clear();
                showColumnsAndRowsLabels();
                showMap(new Vector2d(1, 1));
            });
        }
    }

    public int getFullEnergy() {
        return fullEnergy;
    }

    public void setFullEnergy(int fullEnergy) {
        this.fullEnergy = fullEnergy;
    }

    public void setMarkGenome(boolean markGenome) {
        this.markGenome = markGenome;
        this.showMap(new Vector2d(1,1));
    }
}
