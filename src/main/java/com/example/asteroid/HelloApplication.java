package com.example.asteroid;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(600, 400);

        Polygon ship = new Polygon(-5, -5, 10, 0, -5, 5);
        ship.setTranslateX(300);
        ship.setTranslateY(200);
        ship.setRotate(30);

        pane.getChildren().add(ship);

        Scene scene = new Scene(pane);
        stage.setTitle("Asteroids!");
        stage.setScene(scene);

        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        new AnimationTimer() {
            @Override
            public void handle(long now){
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)){
                    ship.setRotate(ship.getRotate() - 5);
                }
                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)){
                    ship.setRotate(ship.getRotate() + 5);
                }
            }
        }.start();






        stage.show();
    }

}

