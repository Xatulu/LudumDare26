package com.xatulu.LudumDare;

import com.xatulu.LudumDare.GameStates.Dummy;
import com.xatulu.LudumDare.GameStates.Level1;
import com.xatulu.LudumDare.GameStates.MainMenuState;
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

    public static boolean SoundOn = true;
    public static boolean MusicOn = true;


    private LudumDare() {
        super("Minimalism");
        this.addState(new MainMenuState());
        this.addState(new Level1());
        this.addState(new Dummy());
        this.enterState(MAINMENUSTATE);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new LudumDare());
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setVSync(true);
        app.setShowFPS(false);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.getState(MAINMENUSTATE).init(gameContainer, this);
        this.getState(GAMEPLAYSTATE).init(gameContainer, this);
        this.getState(DUMMYSTATE).init(gameContainer, this);
    }
}
