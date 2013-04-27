package com.xatulu.org.LudumDare;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created with IntelliJ IDEA.
 * User: Patrick
 * Date: 27.04.13
 * Time: 04:59
 * To change this template use File | Settings | File Templates.
 */
public class GamePlayState extends BasicGameState {

    private int stateID = 0;

    Font font = null;

    private enum STATES { START_GAME_STATE }
    private STATES currentState = null;

    public GamePlayState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        font = new AngelCodeFont("res/font.fnt","res/font_0.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
