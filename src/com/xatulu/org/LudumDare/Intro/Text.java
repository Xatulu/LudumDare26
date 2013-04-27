package com.xatulu.org.LudumDare.Intro;

import com.xatulu.org.LudumDare.GameplayState;
import com.xatulu.org.LudumDare.LudumDare;
import org.newdawn.slick.Font;

/**
 * User: Patrick
 * Date: 27.04.13
 * Time: 05:11
 */
public class Text {
    private int x;
    private int y;
    private String text;

    public static Text intro0 = new Text("This is the void", GameplayState.font);
    public static Text intro1 = new Text(intro0.getY() + 50, "It's a pretty lonely place.", GameplayState.font);
    public static Text intro2 = new Text(intro1.getY() + 50, "Use WASD or Cursor keys to move.", GameplayState.font);

    private Text(String text, Font font) {
        this.text = text;
        this.y = LudumDare.HEIGHT;
        this.x = (int) (((double) LudumDare.WIDTH / 2) - 0.5 * font.getWidth(text));
    }

    private Text(int y, String text, Font font) {
        this.text = text;
        this.x = (int) (((double) LudumDare.WIDTH / 2) - 0.5 * font.getWidth(text));
        this.y = y;
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
