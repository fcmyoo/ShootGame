package com.tarena.shoot;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    int flyEnteredIndex = 0;

    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) {
            FlayingObject one = nextOne();
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length - 1] = one;
        }
    }

    //飞行物行走
    public void stepAction() {
        hero.step();
        for (int i = 0; i < flyings.length; i++) {
            flyings[i].step();
        }
        for (int i = 0; i < bullets.length; i++) {
            bullets[i].step();
        }
    }

    int shootIndex = 0;

    public void shootAction() {
        shootIndex++;
        if (shootIndex % 30 == 0) {
            //创建子弹对象
            Bullet[] bs = hero.shoot();
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
            //数组追加
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
        }
    }

    //    删除越界的对象包括小蜜蜂,敌机
    private void outOfBoundsAction() {
        int index = 0;
        FlayingObject[] flayingLives = new FlayingObject[flyings.length];
        for (int i = 0; i < flyings.length; i++) {
            FlayingObject flay = flyings[i];
            if (!flay.outOfBounds()) {
                flayingLives[index] = flay;
                index++;
            }
        }
        flyings = Arrays.copyOf(flayingLives, index);
        int bIndex = 0;
        Bullet[] bulletLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            if (!b.outOfBounds()) {
                bulletLives[bIndex] = b;
                bIndex++;
            }
        }
        bullets = Arrays.copyOf(bulletLives, bIndex);
    }

    //子弹与敌人的碰撞
    private void bangAction() {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            bang(b);
        }
    }

    int score = 0;//得分

    private void bang(Bullet b) {
        int index = -1;
        for (int i = 0; i < flyings.length; i++) {
            FlayingObject flying = flyings[i];
            if (flying.shootBy(b)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            FlayingObject one = flyings[index];//获取被撞敌人
            if (one instanceof Enemy) {//敌人
                Enemy e = (Enemy) one;//强转
                score += e.getScore();//得分
            }
            if (one instanceof Award) {//奖励
                Award a = (Award) one;//强转
                int type = a.getType();//获取类型
                switch (type) {
                    case Award.DOUBLE_FIRE:
                        hero.addDoubleFire();
                        break;
                    case Award.LIFE:
                        hero.addLife();
                        break;
                }
            }
        }

    }

    //启动程序执行
    private void action() {
        MouseAdapter l = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                hero.moveTo(e.getX(), e.getY());
            }
        };
        this.addMouseListener(l);
        this.addMouseMotionListener(l);
        Timer timer = new Timer();
        int intervel = 10; //时间间隔,毫秒为单位
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                outOfBoundsAction();
                enterAction();//敌人
                stepAction();//飞行物
                shootAction();//子弹
                bangAction();
                repaint();//重画
//                System.out.println("飞机个数:"+flyings.length);
//                System.out.println("子弹个数:"+bullets.length);
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


