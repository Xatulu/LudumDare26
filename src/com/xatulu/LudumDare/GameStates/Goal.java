package com.xatulu.LudumDare.GameStates;

import com.xatulu.LudumDare.LudumDare;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * User: Patrick
 * Date: 28.04.13
 * Time: 22:45
 */
public class Goal extends BasicGameState implements KeyListener {
    private int stateID = 3;
    int elapsedTime = 0;
    float scale = 0;
    private Music bgm;
    // private Level level;     MACHT PROBLEME MIT COLLISION
    private Sound select;
    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;
    private Image title;
    private Image credits;
    private boolean music_started = false;


    @Override
    public int getID() {
        return stateID;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        bgm = new Music("res/music/winning.ogg");
        select = new Sound("res/sounds/select.wav");
        title = new Image("res/graphics/title.png");
        credits = new Image("res/graphics/end.png");
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setBackground(Color.white);
        MainMenuState.level.getMap().render(0, 0);
        title.draw(128, 48);
        credits.draw(LudumDare.WIDTH / 2 - credits.getWidth() / 2 * scale, 180, scale);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        elapsedTime += i;
        if (!music_started) {
            bgm.loop();
            music_started = true;
        }
        if (elapsedTime >= 100) {
            elapsedTime = 0;
            if (scale < 1) {
                scale += 0.02;
            } else {
                scale = 1;
            }
        }
    }

    public void keyPressed(int i, char c) {
        if ((i == Input.KEY_ESCAPE)) {
            gameContainer.exit();
        }
        if ((i == Input.KEY_ENTER)) {
            select.play();
            stateBasedGame.enterState(LudumDare.MAINMENUSTATE);
            bgm.stop();
            music_started = false;
        }
    }
}
