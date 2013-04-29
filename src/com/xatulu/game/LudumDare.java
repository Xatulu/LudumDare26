package com.xatulu.game;

import com.xatulu.game.GameStates.Dummy;
import com.xatulu.game.GameStates.Goal;
import com.xatulu.game.GameStates.Level1;
import com.xatulu.game.GameStates.MainMenuState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * User: Patrick
 * Date: 27.04.13
 * Time: 03:59
 */
public class LudumDare extends StateBasedGame {

    public static final Integer WIDTH = 640;
    public static final Integer HEIGHT = 480;
    public static final int GAMEPLAYSTATE = 1;
    public static final int MAINMENUSTATE = 0;
    public static final int DUMMYSTATE = 2;
    public static final int GOALSTATE = 3;

    public static boolean SoundOn = true;
    public static boolean MusicOn = true;


    private LudumDare() {
        super("Minimaljump");
        this.addState(new MainMenuState());
        this.addState(new Level1());
        this.addState(new Dummy());
        this.addState(new Goal());
        this.enterState(MAINMENUSTATE);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new LudumDare());
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setVSync(true);
        app.setMinimumLogicUpdateInterval(10);
        app.setMaximumLogicUpdateInterval(10);
        app.setShowFPS(false);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(GOALSTATE).init(gameContainer, this);
        this.getState(MAINMENUSTATE).init(gameContainer, this);
        this.getState(GAMEPLAYSTATE).init(gameContainer, this);
        this.getState(DUMMYSTATE).init(gameContainer, this);
    }
}