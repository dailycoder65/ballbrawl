package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tilesize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tilesize * maxScreenCol;
    final int screenHeight = tilesize * maxScreenRow;

    int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();

    Player player = new Player(this,keyHandler);

    Thread gameThread;

    int playerX = 200;
    int playerY = 200;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // Game logic update
                update();

                // Render
                repaint();
                delta--;
            }
        }
    }
/*
    @Override
    public void run() {
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastLoopTime = System.nanoTime();

        while (gameThread != null) {
            System.out.println("The game is running!");
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;

            double delta = updateLength / ((double)OPTIMAL_TIME);

            // Game logic update
            update();

            // Render
            repaint();

            try {
                long sleepTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                Thread.sleep(Math.max(0, sleepTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
/*
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 10000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
*/
    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        player.draw(g2d);

        g2d.dispose();

    }
}
