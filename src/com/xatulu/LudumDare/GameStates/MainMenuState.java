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
    private Sound select, error;

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
                new Image("res/graphics/menu_options.png").getSubImage(0, 160, 128, 32),
                new Image("res/graphics/menu_options.png").getSubImage(0, 192, 32, 32),
                new Image("res/graphics/menu_options.png").getSubImage(32, 192, 32, 32),
                new Image("res/graphics/menu_options.png").getSubImage(64, 192, 32, 32),
                new Image("res/graphics/menu_options.png").getSubImage(96, 192, 32, 32)
        };
        select = new Sound("res/sounds/select.wav");
        error = new Sound("res/sounds/error.wav");
        input = gameContainer.getInput();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setBackground(Color.white);
        level.getMap().render(0, 0);
        title.draw(128, 96);
        menuOptions[0].draw(320 - menuOptions[0].getWidth() / 2, 240);
        menuOptions[1].draw(320 - menuOptions[1].getWidth() / 2, 288);
        menuOptions[2].draw(320 - menuOptions[2].getWidth() / 2, 336);
        if (LudumDare.MusicOn) {
            menuOptions[6].draw(272, 380);
        } else {
            menuOptions[7].draw(272, 380);
        }
        if (LudumDare.SoundOn) {
            menuOptions[8].draw(336, 380);
        } else {
            menuOptions[9].draw(336, 380);
        }
        switch (state) {
            case (1):
                menuOptions[3].draw(320 - menuOptions[3].getWidth() / 2, 240);
                break;
            case (2):
                menuOptions[4].draw(320 - menuOptions[4].getWidth() / 2, 288);
                break;
            case (3):
                menuOptions[5].draw(320 - menuOptions[5].getWidth() / 2, 336);
                break;
            default:
                break;
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (!music_started) {
            bgm.play();
            music_started = true;
        }
        getMouseInput();
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            switch (state) {
                case (1):
                    stateBasedGame.enterState(LudumDare.GAMEPLAYSTATE);
                    select.play();
                    bgm.stop();
                    music_started = false;
                    break;
                case (2):
                    error.play();
                    break;
                case (3):
                    gameContainer.exit();
                    select.play();
                    break;
                case (4):
                    if (LudumDare.MusicOn) {
                        gameContainer.setMusicOn(false);
                        LudumDare.MusicOn = false;
                    } else {
                        gameContainer.setMusicOn(true);
                        LudumDare.MusicOn = true;
                        select.play();
                    }
                    break;
                case (5):
                    if (LudumDare.SoundOn) {
                        gameContainer.setSoundOn(false);
                        LudumDare.SoundOn = false;
                    } else {
                        gameContainer.setSoundOn(true);
                        LudumDare.SoundOn = true;
                        select.play();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void getMouseInput() {
        int mousex = input.getMouseX(), mousey = input.getMouseY();
        if (mousex >= 256 && mousex < 384 && mousey >= 256 && mousey < 278) {
            state = 1;
        } else if (mousex >= 256 && mousex < 384 && mousey >= 304 && mousey < 326) {
            state = 2;
        } else if (mousex >= 256 && mousex < 384 && mousey >= 352 && mousey < 374) {
            state = 3;
        } else if (mousex >= 272 && mousex < 308 && mousey >= 380 && mousey < 412) {
            state = 4;
        } else if (mousex >= 336 && mousex < 368 && mousey >= 380 && mousey < 412) {
            state = 5;
        }
    }
}
