package com.xatulu.org.LudumDare;

import com.xatulu.org.LudumDare.Entities.Player;
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
    private int elapsedTime = 0;
    private int offsetX = 0;
    int offsetY = 0;

    private Player player = null;

    public static Font font = null;

    private enum STATES {START_INTRO_STATE, INTRO_STATE, INTRODUCE_PLAYER_STATE, MAIN_GAMEPLAY_STATE, EXIT_STATE}

    private STATES currentState = null;

    private SpriteSheet sheet = null;

    public GameplayState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
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
        player = new Player(-32, LudumDare.HEIGHT - 90, sheet.getSprite(0, 0), sheet.getSprite(1, 0), new Animation(new Image[]{sheet.getSprite(0, 0), sheet.getSprite(2, 0)}, 250), new Animation(new Image[]{sheet.getSprite(1, 0), sheet.getSprite(3, 0)}, 250));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setBackground(Color.white);
        graphics.setColor(Color.black);
        graphics.drawString(Text.intro0.getText(), Text.intro0.getX() + offsetX, Text.intro0.getY());
        graphics.drawString(Text.intro1.getText(), Text.intro1.getX() + offsetX, Text.intro1.getY());
        graphics.drawString(Text.intro2.getText(), Text.intro2.getX() + offsetX, Text.intro2.getY());

        if (player.isMoving() && player.getDir() == 1) {
            graphics.drawAnimation(player.walk_r, player.getX(), player.getY());
        } else if (player.isMoving() && player.getDir() == -1) {
            graphics.drawAnimation(player.walk_l, player.getX(), player.getY());
        } else if (!player.isMoving() && player.getLastdir() == 1) {
            graphics.drawImage(player.right, player.getX(), player.getY());
        } else if (!player.isMoving() && player.getLastdir() == -1) {
            graphics.drawImage(player.left, player.getX(), player.getY());
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        elapsedTime += i;
        switch (currentState) {
            case START_INTRO_STATE:
                if (elapsedTime >= 2000) {
                    elapsedTime = 0;
                    currentState = STATES.INTRO_STATE;
                }
                break;
            case INTRO_STATE:
                moveIntro(i);
                if (elapsedTime >= 10000) {
                    elapsedTime = 0;
                    currentState = STATES.INTRODUCE_PLAYER_STATE;
                }
                break;
            case INTRODUCE_PLAYER_STATE:
                if (player.getX() < LudumDare.WIDTH / 2 - 16) {
                    player.runin(i);
                } else {
                    player.setMoving(false);
                }
                /*if (elapsedTime >= 5000) {
                    elapsedTime = 0;
                    movecontrols(i);
                }       */
                if (elapsedTime >= 2500) {
                    elapsedTime = 0;
                    currentState = STATES.MAIN_GAMEPLAY_STATE;
                }
                break;
            case MAIN_GAMEPLAY_STATE:
                Input input = gameContainer.getInput();
                if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
                    player.setMoving(true);
                    player.setDir(1);
                    player.setLastdir(1);
                    offsetX -= i * 0.3;
                } else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
                    player.setMoving(true);
                    player.setDir(-1);
                    offsetX += i * 0.3;
                    player.setLastdir(-1);
                } else {
                    player.setMoving(false);
                }
            case EXIT_STATE:
                break;
        }
    }

    private void moveIntro(int i) {
        if (Text.intro0.getY() > 50) {
            Text.intro0.setY((int) (Text.intro0.getY() - i * 0.1));
            Text.intro1.setY((int) (Text.intro1.getY() - i * 0.1));
        }
    }

    private void movecontrols(int i) {      // TODO Schauen worans hängt
        //if (Text.intro2.getY() > Text.intro1.getY() + 50) {
        Text.intro2.setY((int) (Text.intro2.getY() - i * 0.1));
        // }
    }
}
