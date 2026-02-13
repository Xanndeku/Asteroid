package com.example.asteroid;

import com.example.asteroid.Astero;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {



        Pane pane = new Pane();
        Astero astero=new Astero(50,50);
        pane.getChildren().add(astero.getCharacter());
        astero.turnRight();
        astero.turnRight();
        astero.accelerate();
        astero.accelerate();


        pane.setPrefSize(600, 400);

        com.example.asteroid.Ship ship = new com.example.asteroid.Ship(300, 200);

        pane.getChildren().add(ship.getCharacter());

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Asteroids!");

        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        scene.setOnKeyPressed(event ->
                pressedKeys.put(event.getCode(), true)
        );

        scene.setOnKeyReleased(event ->
                pressedKeys.put(event.getCode(), false)
        );

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }

                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }

                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }

                ship.move();
                astero.move();
                if (ship.collide(astero)){
                    stop();
                }
            }
        }.start();

        stage.show();
    }
}
