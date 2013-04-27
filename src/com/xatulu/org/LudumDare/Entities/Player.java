package com.xatulu.org.LudumDare.Entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

/**
 * User: Patrick
 * Date: 27.04.13
 * Time: 08:42
 */
public class Player {
    private int x;
    private int y;
    private int dir = 1;

    private int lastdir = 1;
    private boolean moving = false;

    public Image right;
    public Image left;
    public Animation walk_r;
    public Animation walk_l;

    public Player(int x, int y, Image right, Image left, Animation walk_r, Animation walk_l) {
        this.x = x;
        this.y = y;
        this.right = right;
        this.left = left;
        this.walk_r = walk_r;
        this.walk_l = walk_l;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getLastdir() {
        return lastdir;
    }

    public void setLastdir(int lastdir) {
        this.lastdir = lastdir;
    }

    public void runin(int i) {
        moving = true;
        this.setX((int) (this.getX() + i * 0.2));
    }
}
