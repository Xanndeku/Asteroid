package com.example.asteroid;

import com.example.asteroid.Astero;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    public static int WIDTH = 600;
    public static int HEIGHT = 400;


    @Override
    public void start(Stage stage) {
        boolean[] gameStarted = {false};
        boolean[] gameOver = {false};


        List<Projeteis> projectiles = new ArrayList<>();
        long[] lastShotTime = {0};
        long[] lastAsteroidSpawn = {0};

        Pane pane = new Pane();
        Text title = new Text(220, 180, "ASTEROIDS");
        title.setStyle("-fx-font-size: 40px;");

        Text startText = new Text(190, 230, "Press SPACE to Start");
        startText.setStyle("-fx-font-size: 20px;");

        pane.getChildren().addAll(title, startText);
        Text gameOverText = new Text(220, 180, "GAME OVER");
        gameOverText.setStyle("-fx-font-size: 40px;");

        Text restartText = new Text(190, 230, "Press R to Restart");
        restartText.setStyle("-fx-font-size: 20px;");


        pane.setPrefSize(WIDTH, HEIGHT);
        Text text = new Text(40, 30, "Points: 0");
        pane.getChildren().add(text);

        AtomicInteger points = new AtomicInteger();
        List<Astero> asteroids = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            Random rnd = new Random();
            Astero astero = new Astero(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT));
            asteroids.add(astero);
            pane.getChildren().add(astero.getCharacter());
        }


        pane.setPrefSize(600, 400);

        com.example.asteroid.Ship ship = new com.example.asteroid.Ship(WIDTH / 2, HEIGHT / 2);

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

        Random rnd = new Random();
        Runnable spawnAsteroid = () -> {
            Astero astero = new Astero(
                    rnd.nextInt(WIDTH),
                    rnd.nextInt(HEIGHT)
            );

            asteroids.add(astero);
            pane.getChildren().add(astero.getCharacter());
        };





        new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (gameOver[0]) {

                    if (pressedKeys.getOrDefault(KeyCode.R, false)) {

                        pane.getChildren().clear();

                        try {
                            HelloApplication.this.start(stage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        stop();
                    }

                    return;
                }

                if (!gameStarted[0]) {

                    if (pressedKeys.getOrDefault(KeyCode.SPACE, false)) {
                        gameStarted[0] = true;
                        pane.getChildren().removeAll(title, startText);
                    }

                    return;
                }





                if (pressedKeys.getOrDefault(KeyCode.SPACE, false)) {

                    long currentTime = System.currentTimeMillis();

                    if (currentTime - lastShotTime[0] > 300) { // 300ms de intervalo

                        Projeteis projectile = new Projeteis(
                                (int) ship.getCharacter().getTranslateX(),
                                (int) ship.getCharacter().getTranslateY()
                        );

                        projectile.getCharacter().setRotate(
                                ship.getCharacter().getRotate()
                        );

                        projectile.accelerate();
                        projectile.setMovement(
                                projectile.getMovement().normalize().multiply(3)
                        );

                        projectiles.add(projectile);
                        pane.getChildren().add(projectile.getCharacter());

                        lastShotTime[0] = currentTime;
                    }
                }
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastAsteroidSpawn[0] > 3000) { // a cada 3 segundos
                    spawnAsteroid.run();
                    lastAsteroidSpawn[0] = currentTime;
                }








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
                for (Projeteis projectile : projectiles) {
                    projectile.move();
                }
                List<Projeteis> projetilesToremove = projectiles.stream()
                        .filter(projeteis ->
                                projeteis.getCharacter().getTranslateX()<0||
                                projeteis.getCharacter().getTranslateX()>WIDTH||
                                projeteis.getCharacter().getTranslateY()<0||
                                projeteis.getCharacter().getTranslateY()>HEIGHT



                                )
                        .collect(Collectors.toList());
                projetilesToremove.forEach(projeteis -> {
                    pane.getChildren().remove(projeteis.getCharacter());
                    projectiles.remove(projeteis);
                });


                for (Astero astero : asteroids) {
                    astero.move();

                    if (ship.collide(astero)) {
                        gameOver[0] = true;

                        pane.getChildren().addAll(gameOverText, restartText);

                    }

                }
                List<Projeteis> projectilesToRemove = projectiles.stream()
                        .filter(projectile -> {

                            List<Astero> collisions = asteroids.stream()
                                    .filter(asteroid -> asteroid.collide(projectile))
                                    .collect(Collectors.toList());

                            if (collisions.isEmpty()) {
                                return false;
                            }

                            collisions.forEach(collided -> {
                                asteroids.remove(collided);
                                pane.getChildren().remove(collided.getCharacter());
                                points.addAndGet(100);
                            });

                            return true;
                        }).collect(Collectors.toList());

                projectilesToRemove.forEach(projectile -> {
                    pane.getChildren().remove(projectile.getCharacter());
                    projectiles.remove(projectile);
                });
                text.setText("Points: " + points.get());




            }
        }.start();

        stage.show();
    }
}
