package com.tarena.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Hero extends FlayingObject {
    private int life;
    private int doubleFire;
    private BufferedImage[] images;
    private int index;

    public Hero() {
        image = ShootGames.hero0;
        width = image.getWidth();
        height = image.getHeight();
        x = 150;
        y = 400;
        life = 3;
        doubleFire = 0;
        images = new BufferedImage[]{
                ShootGames.hero0,
                ShootGames.hero1
        };
        index = 0;

    }


    @Override
    public void step() {//10ms走一次

        //每100ms换一次图片
        image = images[index++ / 10 % images.length];

//        //每100ms换一次图片
//        index++;
//        int a =index/10;
//        int b = a%2;
//        image = images[a%2];
    }

    @Override
    public boolean outOfBounds() {
        return false;
    }

    /**
     * 生成子弹
     */
    public Bullet[] shoot() {
        int xStep = this.width / 4;//飞机的位置
        int yStep = 20;
        if (doubleFire > 0) {
            Bullet[] bs = new Bullet[2];
            bs[0] = new Bullet(this.x + 1 * xStep, this.y - yStep);
            bs[1] = new Bullet(this.x + 3 * xStep, this.y - yStep);
            doubleFire -= 2;
            return bs;
        } else {

            Bullet[] bs = new Bullet[1];
            bs[0] = new Bullet(this.x + 2 * xStep, this.y - yStep);
            return bs;
        }
    }

    //    英雄机随着鼠标移动
    public void moveTo(int x, int y) {
        //让鼠标在英雄机的最中间
        this.x = x - this.width / 2; //hero的x,鼠标的x-1/2
        this.y = y - this.height / 2;//hero的y
    }

    public void addLife() {
        life++;
    }

    public void addDoubleFire() {
        doubleFire += 40;
    }

}
