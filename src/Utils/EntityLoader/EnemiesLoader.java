package Utils.EntityLoader;

import Entity.Enemies.Ghost;
import Entity.Enemy;
import Main.GamePanel;
import TileMap.TileMap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EnemiesLoader {
    private ArrayList<Enemy> enemies;
    private TileMap tm;

    public EnemiesLoader(TileMap tm){
        this.tm = tm;
        enemies = new ArrayList<Enemy>();
    }

    public void loadFromFile(String path){
            try {
                InputStream in = getClass().getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String line;
                String delims = "\\s+";
                boolean start = false;
                while(true){
                    line = br.readLine();
                    if(line == null)break;  //konec dokumentu
                    if(line.matches("^--ENEMIES--")) {start = true;continue;} //začátek bloku s entitami typu enemy
                    if(start == false) continue;    // nenacházím se v bloku s entitami enemy => přeskočit iteraci
                    if(line.matches("^--ENEMIES END--")) break; //konec bloku s entitami enemy => konec cyklu
                    enemies.add(this.addEnemyByTokens(line.split(delims)));
                }
            }catch(Exception e){
                e.getStackTrace();
            }
    }

    public ArrayList<Enemy> getEnemies(){
        return this.enemies;
    }

    private Enemy addEnemyByTokens(String[] tokens){
        if(tokens[0].matches("GHOST")){
            System.out.println("x");
            Ghost s = new Ghost(this.tm);
            s.setPosition(Integer.parseInt(tokens[1]) * 2, Integer.parseInt(tokens[2]) * 2);
            return s;
        }else{
            return null;
        }
    }

}
