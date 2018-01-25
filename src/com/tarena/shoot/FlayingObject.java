package com.tarena.shoot;

import java.awt.image.BufferedImage;

//飞行物
public abstract class FlayingObject {
    protected BufferedImage image; //图片
    protected int width;  //宽
    protected int height;  //高
    protected int x; //坐标
    protected int y; //坐标


    public abstract void step();

    //检查是否越界
    public abstract boolean outOfBounds();

    //射击敌人
    public boolean shootBy(Bullet bullet) {
        int x1 = this.x;
        int x2 = this.x + this.width;
        int y1 = this.y;
        int y2 = this.y + this.height;
        int x = bullet.x;
        int y = bullet.y;
        return x > x1 && x < x2 && y > y1 && y < y2;
    }
}

