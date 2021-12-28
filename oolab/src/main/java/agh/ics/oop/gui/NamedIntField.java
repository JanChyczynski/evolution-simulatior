package agh.ics.oop.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class NamedIntField {
    private TextField field;
    private HBox root;

    public NamedIntField(int initialValue, String name) {
        field = new TextField(Integer.toString(initialValue));
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        field.setMaxWidth(300);
        root = new HBox();
        root.getChildren().addAll(new Label(name), field);
        root.setAlignment(Pos.CENTER);
    }

    public Pane getRoot(){
        return root;
    }

    public int getValue(){
        return Integer.parseInt(field.getText());
    }
}
