package com.xatulu.org.LudumDare;

import com.xatulu.org.LudumDare.Intro.Text;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * User: Patrick
 * Date: 27.04.13
 * Time: 05:06
 */
public class GameplayState extends BasicGameState {

    private int stateID = 1;
    int elapsedTime = 0;

    Font font = null;

    Text Intro0 = null;
    Text Intro1 = null;

    private enum STATES { START_INTRO_STATE, INTRO_STATE, INTRODUCE_PLAYER_STATE, EXIT_STATE }

    private STATES currentState = null;

    SpriteSheet sheet = null;

    public GameplayState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException{
        super.enter(gameContainer, stateBasedGame);
        currentState = STATES.START_INTRO_STATE;
    }

    @Override
    public int getID() {
        return stateID;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        font = new AngelCodeFont("res/font.fnt", "res/font_0.png");
        sheet = new SpriteSheet("res/SpriteSheet.png", 32, 32);
        Intro0 = new Text("This is the void.", font);
        Intro1 = new Text(Intro0.getX(), Intro0.getY()+50, "It's a pretty lonely place.", font);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setBackground(Color.white);
        graphics.setColor(Color.black);
        graphics.drawString(Intro0.getText(), Intro0.getX(), Intro0.getY());
        graphics.drawString(Intro1.getText(), Intro1.getX(), Intro1.getY());
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        elapsedTime += i;
        switch(currentState){
            case START_INTRO_STATE:
                if (elapsedTime >= 2000){
                    elapsedTime = 0;
                    currentState = STATES.INTRO_STATE;
                }
                break;
            case INTRO_STATE:
                moveIntro(i);
                if (elapsedTime >= 5000){
                    elapsedTime = 0;
                    currentState = STATES.EXIT_STATE;
                }
                break;
            case INTRODUCE_PLAYER_STATE:

            case EXIT_STATE:
                break;
    }
}

    private void moveIntro(int i) {
        if (Intro0.getY()>50){
            Intro0.setY((int) (Intro0.getY() - i * 0.1));
            Intro1.setY((int) (Intro1.getY() - i * 0.1));
        }
    }
}
