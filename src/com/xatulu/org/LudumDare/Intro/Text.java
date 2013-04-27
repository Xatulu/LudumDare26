package com.xatulu.org.LudumDare.Intro;

import com.xatulu.org.LudumDare.LudumDare;
import org.newdawn.slick.Font;

/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 27.04.13
 * Time: 05:11
 * To change this template use File | Settings | File Templates.
 */
public class Text {
    int x;
    int y;
    String text;
    Font font;

    public Text(String text, Font font) {
        this.text = text;
        this.y = LudumDare.HEIGHT + font.getHeight(text);
        this.x = LudumDare.WIDTH /2 - font.getWidth(text)/2;
    }

    public Text(int x, int y, String text, Font font) {
        this.x = LudumDare.WIDTH/2 - font.getWidth(text)/2;
        this.y = y + font.getHeight(text);
        this.text = text;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getText() {
        return text;
    }
}
