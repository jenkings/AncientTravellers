package Entity.Collectibles;

import Entity.Animation;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Apple extends Collectible {
    private BufferedImage[] sprites;
    final int width = 30;
    final int height = 30;
    final int cwidth = 35;
    final int cheight = 35;

    public Apple(TileMap tm, int x, int y) {
        super(tm, x, y);

        // load sprites
        try
        {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Collectibles/apple.png"));
            sprites = new BufferedImage[4];
            for(int i = 0; i < sprites.length; i++)
            {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }

        }
        catch(Exception e)
        {
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
