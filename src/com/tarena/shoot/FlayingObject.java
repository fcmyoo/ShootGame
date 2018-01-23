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
}

