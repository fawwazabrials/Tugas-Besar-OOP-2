package main;

import java.awt.*;
import javax.swing.*;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // private static GamePanel instance = null;
    // private static Object lock = new Object();

    // SPRITE SETTINGS
    public final int originalTileSize = 16;    // sprite 16x16
    public final int scale = 4; // scale ganti klao screen ganti
    public final int tileSize = originalTileSize * scale;

    // PANEL SETTINGS
    public final int screenWidth = 1024; // 64 x 64 tiling
    public final int screenHeight = 1024; // 64 x 64 tiling

    // DRAW COMPONENTS
    TileManager tileM = new TileManager(this);
    Thread gameThread;
    Game game = new Game();

    public GamePanel() {
        // System.out.println(screenHeight);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    // public static GamePanel getInstance() {
    //     GamePanel result = instance;
    //     if (result == null) {
    //         synchronized (lock) {
    //             result = instance;
    //             if (result == null) {
    //                 instance = result = new GamePanel();
    //             }
    //         }
    //     }
    //     return result;
    // }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            // update
            update();

            // draw
            repaint();
        }
    }

    public void update() {
        game.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int[][] test = new int[6][6];
        for (int i=0; i<6; i++) {
            for (int j=0; j<6; j++) {
                test[i][j] = 0;
            }
        }

        // HARUS FILL MANUAL GABISA MAKE ARRAYS.FILL, KONTOLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL
        tileM.drawRoom(g2, game.currentView.render());
    }
}
