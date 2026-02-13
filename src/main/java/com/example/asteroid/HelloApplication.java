package com.example.asteroid;

import com.example.asteroid.Astero;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.*;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {

        Pane pane = new Pane();
        List<Astero> asteroids = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Astero astero = new Astero(
                    (int) (Math.random() * 600),
                    (int) (Math.random() * 400)
            );

            asteroids.add(astero);
            pane.getChildren().add(astero.getCharacter());
        }


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
                for (Astero astero : asteroids) {
                    astero.move();

                    if (ship.collide(astero)) {
                        stop();
                    }
                }
            }
        }.start();

        stage.show();
    }
}
