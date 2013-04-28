package com.xatulu.LudumDare.Entities;

import com.xatulu.LudumDare.GameplayState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

/**
 * User: Patrick
 * Date: 27.04.13
 * Time: 08:42
 */
public class Player {
    private float x;
    private float y;
    private float dir = 1;

    private int lastdir = 1;
    private boolean moving = false;
    private boolean inAir = false;

    int moveymax = 96;


    public Image right;
    public Image left;
    public Animation walk_r;
    public Animation walk_l;
    private float size = 0.3f;

    public Player(int x, int y, Image right, Image left, Animation walk_r, Animation walk_l) {
        this.x = x;
        this.y = y;
        this.right = right;
        this.left = left;
        this.walk_r = walk_r;
        this.walk_l = walk_l;
    }

    public float getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
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

    public boolean isInAir() {
        return inAir;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public boolean move(float dx, float dy, int offsetx, int offsety) {
        float nx = this.x + dx;
        float ny = this.y + dy;

        if (validLocation(nx, ny, offsetx, offsety)) {
            this.x = nx;
            this.y = ny;

            return true;
        }
        return false;
    }

    public boolean validLocation(float nx, float ny, int offsetx, int offsety) {
        if (isBlocked(nx - offsetx + 16, ny - offsety)) {
            return false;
        }
        if (isBlocked(nx - offsetx + 26, ny - offsety + 16)) {
            return false;
        }
        if (isBlocked(nx - offsetx + 10, ny - offsety + 16)) {
            return false;
        }
        if (isBlocked(nx - offsetx + 16, ny - offsety + 32)) {
            this.setInAir(false);
            return false;
        }
        if (isBlocked(nx - offsetx + 16, ny - offsety + 40)) {
            this.setInAir(false);
            return true;
        }
        return true;
    }

    public boolean isBlocked(float x, float y) {
        return GameplayState.blocked[(int) x / GameplayState.SIZE][(int) y / GameplayState.SIZE] == true;
    }
}
