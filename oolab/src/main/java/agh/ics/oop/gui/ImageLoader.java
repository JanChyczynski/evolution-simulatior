package agh.ics.oop.gui;

import agh.ics.oop.MapDirection;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    Map<String, Image> map = new HashMap<>();

    public Image getImage(String path){
        if(!map.containsKey(path)){
            try {
                map.put(path, new Image(new FileInputStream(path)));
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return map.get(path);
    }

}
