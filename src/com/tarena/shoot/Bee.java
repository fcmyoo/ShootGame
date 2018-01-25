package com.tarena.shoot;

import java.util.Random;

public class Bee extends FlayingObject implements Award {
    private int xSpeed = 1;
    private int ySpeed = 2;
    private int awardType;

    public Bee() {
        image = ShootGames.bee;
        width = image.getWidth();
        height = image.getHeight();
        Random rand = new Random();
        x = rand.nextInt(ShootGames.WIDTH - this.width + 1);
        y = -this.height;
        awardType = rand.nextInt(2);
    }

    @Override
    public int getType() {
        return awardType;
    }

    @Override
    public void step() {
        x += xSpeed;
        y += ySpeed;
        if (x >= ShootGames.WIDTH - this.width) {
            xSpeed = -1;
        }
        if (x <= 0) {
            xSpeed = 1;
        }
    }

    @Override
    public boolean outOfBounds() {
        return this.y >= ShootGames.HEIGHT;
    }
}
