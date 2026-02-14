package com.example.asteroid;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class character {
    public boolean collide(character other) {
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    private Polygon character;
    private Point2D movement;

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    public character(Polygon polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.movement = new Point2D(0, 0);
    }

    public void turnLeft() {
        character.setRotate(character.getRotate() - 5);
    }

    public void turnRight() {
        character.setRotate(character.getRotate() + 5);
    }

    public void move() {
        character.setTranslateX(character.getTranslateX() + movement.getX());
        character.setTranslateY(character.getTranslateY() + movement.getY());
        if (this.character.getTranslateX() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + HelloApplication.WIDTH);
        }

        if (this.character.getTranslateX() > HelloApplication.WIDTH) {
            this.character.setTranslateX(this.character.getTranslateX() % HelloApplication.WIDTH);
        }

        if (this.character.getTranslateY() < 0) {
            this.character.setTranslateY(this.character.getTranslateY() + HelloApplication.HEIGHT);
        }

        if (this.character.getTranslateY() > HelloApplication.HEIGHT) {
            this.character.setTranslateY(this.character.getTranslateY() % HelloApplication.HEIGHT);
        }
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(character.getRotate()));
        double changeY = Math.sin(Math.toRadians(character.getRotate()));
        changeY *= 0.05;
        changeX *=0.05;

        movement = movement.add(changeX, changeY);
    }

    public Polygon getCharacter() {
        return character;
    }
}
