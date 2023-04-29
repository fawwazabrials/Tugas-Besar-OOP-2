package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
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

    public GamePanel() {
        System.out.println(screenHeight);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        
    }

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

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
    }
}
