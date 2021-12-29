package agh.ics.oop.gui;

import agh.ics.oop.IEngine;
import agh.ics.oop.IStatisticsObserver;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.StatisticsEntry;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class StatisticsToFileGui implements IStatisticsObserver {
    HBox root;
    TextField filenameField;
    Button saveButton;
    List<StatisticsEntry> entries;
    StatisticsEntry sum;

    public StatisticsToFileGui(SimulationEngine engine) {
        entries = new ArrayList<>();
        sum = new StatisticsEntry(0,0,0,0,0,0,null);
        engine.addStatisticsObserver(this);
        filenameField = new TextField("stats.csv");
        saveButton = new Button("save!");
        saveButton.setOnAction(event -> write());
        root = new HBox();
        root.getChildren().addAll(new Label("Stats filename: "), filenameField, saveButton);
    }

    public Pane getRoot(){
        return root;
    }

    public void write(){
        List<String> lines = entries.stream().map(StatisticsEntry::asCsvRecord).collect(Collectors.toList());
        lines.add(0, StatisticsEntry.csvHeader());
        lines.add(lines.size(), sum.divide(entries.size()).asCsvRecord());
        Path file = Paths.get(filenameField.getText());
        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void statisticsChanged(StatisticsEntry statisticsEntry) {
        entries.add(statisticsEntry);
        sum = sum.add(statisticsEntry);
    }
}
