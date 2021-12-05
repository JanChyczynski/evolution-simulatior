package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import static java.util.Objects.isNull;

public class App extends Application{
    public static final int CELL_WIDTH = 20;
    public static final int CELL_HEIGHT = 20;
    private AbstractWorldMap map;

    @Override
    public void init() throws Exception {
        try{
            MoveDirection[] directions = new OptionParser().parse(getParameters().getRaw().toArray(new String[0]));
            map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(0,3), new Vector2d(6,10) };
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
            System.out.println(map);
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);

        setCellSize(grid, CELL_WIDTH, CELL_HEIGHT);

        showColumnsAndRowsLabels(grid);

        showMap(grid, new Vector2d(1,1));

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMap(GridPane grid, Vector2d origin) {
        for (int i = map.bottomLeftCorner().x(); i <= map.upperRightCorner().x(); i++) {
            for (int j = map.bottomLeftCorner().y(); j <= map.upperRightCorner().y(); j++) {
                MapElementsSet objects = map.objectAt(new Vector2d(i, j));
                String objectsRepresentation = isNull(objects) ? " " : objects.toString();
                addStringAsCenteredLabel(objectsRepresentation,
                        origin.x() + i  - map.bottomLeftCorner().x(),
                        origin.y() + map.upperRightCorner().y() - j, grid);
            }
        }
    }

    private void showColumnsAndRowsLabels(GridPane grid) {
        addStringAsCenteredLabel("y/x", 0, 0, grid);
        for (int i = map.bottomLeftCorner().x(); i <= map.upperRightCorner().x(); i++) {
            addStringAsCenteredLabel(Integer.toString(i),i+1-map.bottomLeftCorner().x(), 0, grid);
        }
        for (int i = map.bottomLeftCorner().y(); i <= map.upperRightCorner().y(); i++) {
            addStringAsCenteredLabel(Integer.toString(i), 0, map.upperRightCorner().y()+1-i, grid);
        }
    }

    private void setCellSize(GridPane grid, int width, int height) {
        for (int i = map.bottomLeftCorner().x(); i <= map.upperRightCorner().x()+1; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(width));
        }
        for (int i = map.bottomLeftCorner().y(); i <= map.upperRightCorner().y()+1; i++) {
            grid.getRowConstraints().add(new RowConstraints(height));
        }
    }

    private void addStringAsCenteredLabel(String objectsRepresentation, int i, int j, GridPane grid) {
        Label label = new Label(objectsRepresentation);
        grid.add(label, i, j,1, 1);
        GridPane.setHalignment(label, HPos.CENTER);
    }
}
