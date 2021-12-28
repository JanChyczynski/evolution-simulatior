package agh.ics.oop.gui;

import agh.ics.oop.Genome;
import agh.ics.oop.IStatisticsObserver;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.Vector2d;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class StatisticsGui implements IStatisticsObserver {
    private SimulationEngine engine;

    private VBox rootBox;
    private XYChart.Series animalsNumberSeries;
    private XYChart.Series plantsNumberSeries;
    private XYChart.Series averageEnergySeries;
    private XYChart.Series averageLifespanSeries;
    private XYChart.Series averageChildrenNumberSeries;
    private Genome genomeMode;
    private Label genomeLabel;
    private HBox genomeBox;

    private XYChart<Number, Number> populationsChart;
    private XYChart<Number, Number> childrenChart;
    private XYChart<Number, Number> energyChart;
    private XYChart<Number, Number> lifespanChart;

    public StatisticsGui(SimulationEngine engine) {
        this.engine = engine;
        engine.addStatisticsObserver(this);
        rootBox = new VBox();
        populationsChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        childrenChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        energyChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lifespanChart = new LineChart<>(new NumberAxis(), new NumberAxis());

        animalsNumberSeries = new XYChart.Series();
        plantsNumberSeries = new XYChart.Series();
        averageEnergySeries = new XYChart.Series();
        averageLifespanSeries = new XYChart.Series();
        averageChildrenNumberSeries = new XYChart.Series();
        animalsNumberSeries.setName("animals #");
        plantsNumberSeries.setName("plants #");
        averageEnergySeries.setName("average Energy");
        averageLifespanSeries.setName("average Lifespan");
        averageChildrenNumberSeries.setName("average Children #");

        populationsChart.getData().add(animalsNumberSeries);
        populationsChart.getData().add(plantsNumberSeries);
        childrenChart.getData().add(averageChildrenNumberSeries);
        energyChart.getData().add(averageEnergySeries);
        lifespanChart.getData().add(averageLifespanSeries);

        genomeLabel = new Label("Top genome: ");
        genomeBox = new HBox();
        genomeBox.getChildren().add(genomeLabel);


        rootBox.getChildren().addAll(populationsChart,
                                    childrenChart,
                                    energyChart,
                                    lifespanChart,
                                    genomeBox);
    }

    public Pane getRoot() {
        return rootBox;
    }

    @Override
    public void statisticsChanged(int day, int animalsNumber, int plantsNumber, double averageEnergy, double averageLifespan, double averageChildrenNumber, Genome genomeMode) {
        Platform.runLater(() -> {
            animalsNumberSeries.getData().add(new XYChart.Data(day, animalsNumber));
            plantsNumberSeries.getData().add(new XYChart.Data(day, plantsNumber));
            averageEnergySeries.getData().add(new XYChart.Data(day, averageEnergy));
            averageLifespanSeries.getData().add(new XYChart.Data(day, averageLifespan));
            averageChildrenNumberSeries.getData().add(new XYChart.Data(day, averageChildrenNumber));
            genomeBox.getChildren().clear();
            genomeBox.getChildren().addAll(genomeLabel, new Label(genomeMode.toString()));
        });
    }
}
