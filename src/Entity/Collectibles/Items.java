package Entity.Collectibles;

import GameState.LevelState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public enum Items
{
    APPLE("Apple", LevelState.class.getClass().getResourceAsStream("/Sprites/Collectibles/apple.png"));


    private BufferedImage sprite;
    private String name;

    public String getName() {
        return this.name;
    }
    public BufferedImage getSprite() {
        return this.sprite;
    }


    private Items(String name, InputStream file){
        this.name = name;
        try {
            this.sprite = ImageIO.read(file).getSubimage(0, 0, 30, 30);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}