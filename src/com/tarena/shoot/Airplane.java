package com.tarena.shoot;

import java.util.Random;

//敌机
public class Airplane extends FlayingObject implements Enemy {
    private int speed = 2;

    public Airplane() {
        image = ShootGames.airplane;
        width = image.getWidth();
        height = image.getHeight();
        Random rand = new Random();
        x = rand.nextInt(ShootGames.WIDTH - this.width + 1);
        y = -this.height;
    }


    @Override
    public int getScore() {
        return 5;
    }

    @Override
    public void step() {
        y+=speed;
    }
}
