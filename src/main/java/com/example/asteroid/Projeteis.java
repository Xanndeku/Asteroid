package com.example.asteroid;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Projeteis extends com.example.asteroid.character {
    public Projeteis(int x,int y){
        super(new Polygon(2,-2,2,2,-2,2,-2,-2),x ,y);

    }
    private boolean alive = true;
    public void move() {
        super.getCharacter().setTranslateX(
                super.getCharacter().getTranslateX() + super.getMovement().getX()
        );
        super.getCharacter().setTranslateY(
                super.getCharacter().getTranslateY() + super.getMovement().getY()
        );


        if (super.getCharacter().getTranslateX() < 0
                || super.getCharacter().getTranslateX() > HelloApplication.WIDTH
                || super.getCharacter().getTranslateY() < 0
                || super.getCharacter().getTranslateY() > HelloApplication.HEIGHT) {

            alive = false;
        }


    }
    public boolean isAlive() {
        return alive;
    }
}




