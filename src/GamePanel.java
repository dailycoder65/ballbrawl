import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();

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
 */
        }


    }

    public void update() {
        if (keyHandler.upPressed) {
            playerY -= playerSpeed;
            System.out.println("Player Y: " + playerY);
        } else if (keyHandler.leftPressed) {
            System.out.println("Player X: " + playerX);
            playerX -= playerSpeed;
        } else if (keyHandler.rightPressed) {
            System.out.println("Player X: " + playerX);
            playerX += playerSpeed;
        } else if (keyHandler.downPressed) {
            System.out.println("Player X: " + playerX);
            playerY += playerSpeed;
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(playerX, playerY,60, 60);

        g2d.dispose();

    }
}
