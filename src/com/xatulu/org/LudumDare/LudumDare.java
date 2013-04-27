package com.xatulu.org.LudumDare;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 27.04.13
 * Time: 03:59
 * To change this template use File | Settings | File Templates.
 */
public class LudumDare extends StateBasedGame {

    public static final Integer WIDTH = 640;
    public static final Integer HEIGHT = 480;
    public static final int GAMEPLAYSTATE = 1;
    public static final int INTROGAMESTATE = 0;

    Image background = null;

    public LudumDare() {
        super("Minimalism");
        this.addState(new IntroGameState(INTROGAMESTATE));
        this.addState(new GamePlayState(GAMEPLAYSTATE));
        this.enterState(INTROGAMESTATE);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new LudumDare());
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setVSync(true);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(GAMEPLAYSTATE).init(gameContainer, this);
        this.getState(INTROGAMESTATE).init(gameContainer, this);
    }
}
