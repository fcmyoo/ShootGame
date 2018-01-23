package com.tarena.shoot;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ShootGames extends JPanel {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 640;

    public static BufferedImage background;
    public static BufferedImage start;
    public static BufferedImage pause;
    public static BufferedImage gameover;
    public static BufferedImage airplane;
    public static BufferedImage bee;
    public static BufferedImage bullet;
    public static BufferedImage hero0;
    public static BufferedImage hero1;

    private Hero hero = new Hero();
    private FlayingObject[] flyings = {};
    private Bullet[] bullets = {};

//    ShootGames() {
//        flyings = new FlayingObject[2];
//        flyings[0] = new Airplane();
//        flyings[1] = new Bee();
//        bullets = new Bullet[1];
//        bullets[0] = new Bullet(100, 200);
//    }

    static { //初始化
        try {
            background = ImageIO.read(ShootGames.class.getResource("background.png"));
            start = ImageIO.read(ShootGames.class.getResource("start.png"));
            pause = ImageIO.read(ShootGames.class.getResource("pause.png"));
            gameover = ImageIO.read(ShootGames.class.getResource("gameover.png"));
            airplane = ImageIO.read(ShootGames.class.getResource("airplane.png"));
            bee = ImageIO.read(ShootGames.class.getResource("bee.png"));
            bullet = ImageIO.read(ShootGames.class.getResource("bullet.png"));
            hero0 = ImageIO.read(ShootGames.class.getResource("hero0.png"));
            hero1 = ImageIO.read(ShootGames.class.getResource("hero1.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FlayingObject nextOne() {
        Random random = new Random();
        int type = random.nextInt(20);
        if (type < 5) {
            return new Bee();
        } else {
            return new Airplane();
        }
    }

    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);
        paintHero(g);
        paintBullets(g);
        paintFlyingObjects(g);
    }

    public void paintHero(Graphics g) {
        g.drawImage(hero.image, hero.x, hero.y, null);
    }

    public void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < flyings.length; i++) {
            FlayingObject f = flyings[i];
            g.drawImage(f.image, f.x, f.y, null);
        }
    }

    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.image, b.x, b.y, null);
        }


    }

    int flyEnteredIndex =0;
    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) {
            FlayingObject one = nextOne();
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length-1] = one;
        }
    }

    //飞行物行走
    public void stepAction() {
        hero.step();
        for (int i =0;i<flyings.length;i++) {
            flyings[i].step();
        }
        for (int i =0;i<bullets.length;i++) {
            bullets[i].step();
        }
    }
    int shootIndex=0;
    public void shootAction() {
        shootIndex++;
        if (shootIndex % 30 == 0) {
            //创建子弹对象
            Bullet[] bs = hero.shoot();
            bullets = Arrays.copyOf(bullets,bullets.length+bs.length);
            //数组追加
            System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);
        }
    }
    //启动程序执行
    public void action() {
        Timer timer = new Timer();
        int intervel = 10 ; //时间间隔,毫秒为单位
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                enterAction();//敌人
                stepAction();//飞行物
                shootAction();//子弹
                repaint();//重画
            }
        }, intervel, intervel);
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Fly");
        ShootGames game = new ShootGames();
        frame.add(game);
        frame.setSize(WIDTH, HEIGHT);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.action();//启动程序执行
    }






}


