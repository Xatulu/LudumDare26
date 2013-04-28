package com.xatulu.LudumDare.Entities;

import com.xatulu.LudumDare.GameStates.Level1;
import com.xatulu.LudumDare.Levels.Level;
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


    public final Image right;
    public final Image left;
    public final Animation walk_r;
    public final Animation walk_l;

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

    public boolean isInAir() {
        return !inAir;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public void move(float dx, float dy, int offsetx, int offsety, Level level) {
        float nx = this.x + dx;
        float ny = this.y + dy;

        if (validLocation(nx, ny, offsetx, offsety, level)) {
            this.x = nx;
            this.y = ny;
        }
    }

    public boolean validLocation(float nx, float ny, int offsetx, int offsety, Level level) {
        if (isBlocked(nx - offsetx + 16, ny - offsety, level)) {
            return false;
        }
        if (isBlocked(nx - offsetx + 26, ny - offsety + 16, level)) {
            return false;
        }
        if (isBlocked(nx - offsetx + 10, ny - offsety + 16, level)) {
            return false;
        }
        if (isBlocked(nx - offsetx + 16, ny - offsety + 32, level)) {
            this.setInAir(false);
            return false;
        }
        if (isBlocked(nx - offsetx + 16, ny - offsety + 40, level)) {
            this.setInAir(false);
            return true;
        }
        return true;
    }

    boolean isBlocked(float x, float y, Level level) {
        return level.blocked[(int) x / Level1.SIZE][(int) y / Level1.SIZE];
    }
}