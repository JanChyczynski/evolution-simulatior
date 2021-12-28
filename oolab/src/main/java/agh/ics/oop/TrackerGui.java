package agh.ics.oop;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static java.util.Objects.isNull;

public class TrackerGui {
    private VBox root;
    private ITrackable tracked;
    private int startDay;

    public TrackerGui(){
        root = new VBox();
        root.getChildren().add(new Label("Tracker ready - click on an animal to track it!"));
    }

    public void refresh(){
        if(!isNull(tracked)){
            Platform.runLater(()-> {
                root.getChildren().clear();
                root.getChildren().addAll(
                        new Label("Tracked animal's statistics from day : " + startDay),
                        new Label("Tracked animal's genome: " + tracked.getGenome()),
                        new Label("new children number: " + Integer.toString(tracked.getNewChildrenNumber(startDay))),
                        new Label("new ancestors number: " + Integer.toString(tracked.getNewAncestorsNumber(startDay))),
                        new Label((tracked.getDeathDay().isEmpty()) ?
                                "Still alive on position " + tracked.getPosition():
                                "Died on day: " + tracked.getDeathDay().getAsInt())
                );
            });
        }
    }

    public void track(ITrackable trackable, int startDay){
        tracked = trackable;
        this.startDay = startDay;
        refresh();
    }

    public Pane getRoot(){
        return root;
    }
}
