package com.xatulu.LudumDare.Levels;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * User: Patrick
 * Date: 28.04.13
 * Time: 16:57
 */
public class Level {
    int width, height;

    TiledMap map;
    public static boolean[][] blocked;

    public Level(TiledMap map) throws SlickException {
        this.width = map.getWidth();
        this.height = map.getHeight();
        this.map = map;
        buildCollision(width, height);
    }

    void buildCollision(int width, int height) {
        blocked = new boolean[width][height];
        for (int xAxis = 0; xAxis < width; xAxis++) {
            for (int yAxis = 0; yAxis < height; yAxis++) {
                int tileID = map.getTileId(xAxis, yAxis, 0);
                if (tileID == 7) {
                    blocked[xAxis][yAxis] = true;
                }
            }
        }
    }

    public TiledMap getMap() {
        return map;
    }

}
