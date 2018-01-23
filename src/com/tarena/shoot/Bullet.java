package com.tarena.shoot;

import java.util.Random;

public class Bullet extends FlayingObject {
    private int speed = 3;

    public Bullet(int x,int y) {
        image = ShootGames.bullet;
        width = image.getWidth();
        height = image.getHeight();
        Random rand = new Random();
        this.x = x;
        this.y = y;
    }

    @Override
    public void step() {
        y-=speed;
    }
}
