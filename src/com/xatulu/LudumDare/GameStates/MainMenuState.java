package com.xatulu.LudumDare.GameStates;

import com.xatulu.LudumDare.Levels.Level;
import com.xatulu.LudumDare.LudumDare;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * User: Patrick
 * Date: 28.04.13
 * Time: 16:13
 */
public class MainMenuState extends BasicGameState {

    private int stateID = 0;
    private int state;
    private Music bgm;
    private Level level;
    private Image title;
    private boolean music_started;
    private Image[] menuOptions;
    private Input input;

    public MainMenuState() {
        this.stateID = LudumDare.MAINMENUSTATE;
    }

    @Override
    public int getID() {
        return stateID;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        bgm = new Music("res/music/level1.ogg");
        level = new Level(new TiledMap("res/graphics/mainmenu.tmx"));
        title = new Image("res/graphics/title.png");
        menuOptions = new Image[]{
                new Image("res/graphics/menu_options.png").getSubImage(0, 0, 128, 32),
                new Image("res/graphics/menu_options.png").getSubImage(0, 32, 128, 32),
                new Image("res/graphics/menu_options.png").getSubImage(0, 64, 128, 32),
                new Image("res/graphics/menu_options.png").getSubImage(0, 96, 128, 32),
                new Image("res/graphics/menu_options.png").getSubImage(0, 128, 128, 32),
                new Image("res/graphics/menu_options.png").getSubImage(0, 160, 128, 32)
        };
        input = gameContainer.getInput();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setBackground(Color.white);
        level.getMap().render(0, 0);
        title.draw(128, 96);
        menuOptions[0].draw(256, 256);
        menuOptions[1].draw(256, 304);
        menuOptions[2].draw(256, 352);
        switch (state) {
            case (1):
                menuOptions[3].draw(256, 256);
                break;
            case (2):
                menuOptions[4].draw(256, 304);
                break;
            case (3):
                menuOptions[5].draw(256, 352);
                break;
            default:
                break;
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (!music_started)
            bgm.play();
        music_started = true;
        getMouseInput();
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            switch (state) {
                case (1):
                    stateBasedGame.enterState(LudumDare.GAMEPLAYSTATE);
                    bgm.stop();
                    break;
                case (2):
                    break;
                case (3):
                    gameContainer.exit();
                    break;
                default:
                    break;
            }
        }
    }

    private void getMouseInput() {
        int mousex = input.getMouseX(), mousey = input.getMouseY();
        if (mousex >= 256 && mousex < 384 && mousey >= 256 && mousey < 288) {
            state = 1;
        } else if (mousex >= 256 && mousex < 384 && mousey >= 304 && mousey < 336) {
            state = 2;
        } else if (mousex >= 256 && mousex < 384 && mousey >= 352 && mousey < 384) {
            state = 3;
        }
    }
}
