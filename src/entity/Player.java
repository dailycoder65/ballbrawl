package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player extends Entity {

    public GamePanel gp;
    public KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.keyHandler = kh;

        this.setDefaults();
    }

    public void setDefaults() {
       x = 100;
       y = 100;
       speed = 4;
    }

    public void update() {
        if (this.keyHandler.upPressed) {
            y -= speed;
            System.out.println("Player Y: " + y);
        } else if (this.keyHandler.leftPressed) {
            System.out.println("Player X: " + x);
            x -= speed;
        } else if (this.keyHandler.rightPressed) {
            System.out.println("Player X: " + x);
            x += speed;
        } else if (this.keyHandler.downPressed) {
            System.out.println("Player Y: " + y);
            y += speed;
        }
    }

    public void draw (Graphics2D g2d) {

        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y,this.gp.tilesize, this.gp.tilesize);

    }
}
