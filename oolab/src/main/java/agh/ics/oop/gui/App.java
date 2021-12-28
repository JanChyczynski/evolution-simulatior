package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        Thread executionThread = new Thread(() ->execute(primaryStage));
        executionThread.start();
    }

    private void execute(Stage primaryStage){

        SimulationParameters params = getSimulationParameters(primaryStage);

        SimulationGui sim1 = new SimulationGui(images, params);
        SimulationGui sim2 = new SimulationGui(images, params);

        HBox rootBox = new HBox();
        rootBox.getChildren().addAll(sim1.getRoot(), sim2.getRoot());

        Scene scene = new Scene(rootBox, 1920, 1080);
        Platform.runLater( () ->{
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        sim1.start();
        sim2.start();
    }

    private SimulationParameters getSimulationParameters(Stage primaryStage) {
        Object waitForButtonMonitor = new Object();
        final boolean[] done = {false};
        VBox rootBox = new VBox();
        NamedIntField heightField = new NamedIntField(15, "Height:");
        NamedIntField widthField = new NamedIntField(15, "Width:");
        NamedIntField jungleRatioField = new NamedIntField(2, "Jungle ratio:");
        NamedIntField grassEnergyField = new NamedIntField(40, "Grass energy:");
        NamedIntField initialPopulationField = new NamedIntField(12, "Initial population:");
        NamedIntField startEnergyField = new NamedIntField(50, "Start energy:");
        NamedIntField moveEnergyField = new NamedIntField(1, "Move energy:");

        Button goButton = new Button("GO!");
        goButton.setOnAction(event -> {
            synchronized (waitForButtonMonitor){
                done[0] = true;
                waitForButtonMonitor.notifyAll();
            }
        });

        Label title = new Label("Enter simulations initial parameters to these beautifully aligned fields:");

        rootBox.getChildren().addAll(title, heightField.getRoot(), widthField.getRoot(), jungleRatioField.getRoot(),
                grassEnergyField.getRoot(), initialPopulationField.getRoot(),
                startEnergyField.getRoot(), moveEnergyField.getRoot(),
                goButton);

        rootBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(rootBox, 1920, 1080);
        Platform.runLater( () ->{
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        while (!done[0]){
            synchronized (waitForButtonMonitor){
                System.out.println(this);
                try {
                    waitForButtonMonitor.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return new SimulationParameters(heightField.getValue(), widthField.getValue(), jungleRatioField.getValue(),
                grassEnergyField.getValue(), initialPopulationField.getValue(),
                startEnergyField.getValue(), moveEnergyField.getValue());
    }
}
