package com.example.asteroid;

import javafx.scene.shape.Polygon;
public class Astero extends com.example.asteroid.character {


    public Astero(int x, int y) {
        super(new com.example.asteroid.PolygonFactory().createPolygon(), x, y);
    }
}