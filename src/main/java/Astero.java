package com.example.asteroid;

import javafx.scene.shape.Polygon;
public class Astero extends com.example.asteroid.character {


    public Astero(int x, int y) {
        super(new Polygon(20,-20,20,20,-20,20,-20,-20),x,y);
    }
}
