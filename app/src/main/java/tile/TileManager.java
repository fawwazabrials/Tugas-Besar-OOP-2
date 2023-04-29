package tile;

import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    GamePanel gp;
    Tile[] roomTile;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        roomTile = new Tile[10];
        getTileImage();
    }

    public void getTileImage() {
        try {
            roomTile[0] = new Tile();
            roomTile[0].image = ImageIO.read(getClass().getResourceAsStream("/room/floor.png"));

            roomTile[1] = new Tile();
            roomTile[1].image = ImageIO.read(getClass().getResourceAsStream("/room/fridge.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawRoom(Graphics2D g2, int map[][]) {
        int width = 6; int height = 6;
        int offsetX = (gp.screenWidth-width*gp.tileSize)/2;
        int offsetY = (gp.screenHeight-height*gp.tileSize)/2;

        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                if (map[i][j] == 0) { // floor
                    g2.drawImage(roomTile[0].image, offsetX+i*gp.tileSize, offsetY+j*gp.tileSize, gp.tileSize, gp.tileSize, null);
                }
                else if (map[i][j] == 1) { // fridge
                    g2.drawImage(roomTile[1].image, offsetX+i*gp.tileSize, offsetY+j*gp.tileSize, gp.tileSize, gp.tileSize, null);
                }
                // System.out.println(i*gp.tileSize + " " + j*gp.tileSize);
            }
        }
    }
}
