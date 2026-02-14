package com.example.asteroid;
import java.util.Random;


import javafx.scene.shape.Polygon;
public class Astero extends com.example.asteroid.character {


    private double movimentoRotacional;

    public Astero(int x, int y) {
        super(new com.example.asteroid.PolygonFactory().createPolygon(), x, y);
        Random rnd = new Random();

        super.getCharacter().setRotate(rnd.nextInt(360));

        int accelerationAmount = 1 + rnd.nextInt(10);
        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }

        this.movimentoRotacional = 0.5 - rnd.nextDouble();
    }
    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + movimentoRotacional);
    }
    }
