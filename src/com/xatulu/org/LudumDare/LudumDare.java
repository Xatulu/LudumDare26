package com.xatulu.org.LudumDare;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * User: Patrick
 * Date: 27.04.13
 * Time: 03:59
 */
public class LudumDare extends StateBasedGame {

    public static final Integer WIDTH = 640;
    public static final Integer HEIGHT = 480;
    private static final int GAMEPLAYSTATE = 1;


    private LudumDare() {
        super("Minimalism");
        this.addState(new GameplayState(GAMEPLAYSTATE));
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new LudumDare());
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setMinimumLogicUpdateInterval(20);
        app.setMaximumLogicUpdateInterval(20);
        app.setVSync(true);
        app.setShowFPS(false);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(GAMEPLAYSTATE).init(gameContainer, this);
    }
}
