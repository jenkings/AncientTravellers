package Entity.Collectibles;

import Entity.Animation;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Apple extends Collectible {
    private BufferedImage[] sprites;


    public Apple(TileMap tm, int x, int y) {
        super(tm, x, y);
        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;
        this.dropitem = Items.APPLE;
        // load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Collectibles/apple.png"));
            sprites = new BufferedImage[4];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(150);
    }



    public void update(){
        animation.update();
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }

}
