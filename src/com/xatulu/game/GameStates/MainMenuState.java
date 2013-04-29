package com.xatulu.game.GameStates;

import com.xatulu.game.Levels.Level;
import com.xatulu.game.LudumDare;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * User: Patrick
 * Date: 28.04.13
 * Time: 16:13
 */

//TODO MouseListener statt standard-listener
public class MainMenuState extends BasicGameState implements KeyListener, MouseListener {

    private int stateID = 0;
    private int state = 1;
    private Music bgm;
    public static Level level;
    private Image title;
    private boolean music_started;
    private Image[] menuOptions;
    private Input input;
    private Sound select, error;
    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;
    private boolean first_try = true;

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
                new Image("res/graphics/menu_options.png").getSubImage(96, 192, 32, 32),
                new Image("res/graphics/menu_options.png").getSubImage(0, 224, 32, 32),
                new Image("res/graphics/menu_options.png").getSubImage(32, 224, 32, 32),
                new Image("res/graphics/menu_options.png").getSubImage(64, 224, 32, 32),
                new Image("res/graphics/menu_options.png").getSubImage(96, 224, 32, 32)
        };
        select = new Sound("res/sounds/select.wav");
        error = new Sound("res/sounds/error.wav");
        input = gameContainer.getInput();
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
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
            case (4):
                if (LudumDare.MusicOn) {
                    menuOptions[10].draw(272, 380);
                } else {
                    menuOptions[11].draw(272, 380);
                }
                break;
            case (5):
                if (LudumDare.SoundOn) {
                    menuOptions[12].draw(336, 380);
                } else {
                    menuOptions[13].draw(336, 380);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (!music_started) {
            bgm.loop();
            music_started = true;
        }
    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        int mousex = newx, mousey = newy;
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
        } else {
            state = 0;
        }
    }

    public void mouseClicked(int button, int x, int y, int clickCount) {
        switch (state) {
            case (1):
                newGame();
                break;
            case (2):
                continueGame();
                break;
            case (3):
                exitGame();
                break;
            case (4):
                SetMusic();
                break;
            case (5):
                SetSound();
                break;
            default:
                break;
        }
    }

    public void keyPressed(int i, char c) {
        if ((i == Input.KEY_F5)) {
            if (LudumDare.MusicOn) {
                LudumDare.MusicOn = false;
                gameContainer.setMusicOn(false);
            } else {
                LudumDare.MusicOn = true;
                gameContainer.setMusicOn(true);
            }
        }
        if ((i == Input.KEY_F6)) {
            if (LudumDare.SoundOn) {
                LudumDare.SoundOn = false;
                gameContainer.setSoundOn(false);
            } else {
                LudumDare.SoundOn = true;
                gameContainer.setSoundOn(true);
            }
        }
        if ((i == Input.KEY_DOWN)) {
            switch (state) {
                case (1):
                    state++;
                    break;
                case (2):
                    state++;
                    break;
                case (3):
                    state++;
                    break;
                case (4):
                    state++;
                    break;
                case (5):
                    state--;
                    break;
                default:
                    state++;
                    break;
            }
        }
        if ((i == Input.KEY_LEFT)) {
            switch (state) {
                case (4):
                    state++;
                    break;
                case (5):
                    state--;
                    break;
            }
        }
        if ((i == Input.KEY_RIGHT)) {
            switch (state) {
                case (4):
                    state++;
                    break;
                case (5):
                    state--;
                    break;
            }
        }
        if ((i == Input.KEY_UP)) {
            switch (state) {
                case (1):
                    break;
                case (2):
                    state--;
                    break;
                case (3):
                    state--;
                    break;
                case (4):
                    state = 3;
                    break;
                case (5):
                    state = 3;
                    break;
                default:
                    state--;
                    break;
            }
        }
        if ((i == Input.KEY_ENTER)) {
            switch (state) {
                case (1):
                    newGame();
                    break;
                case (2):
                    continueGame();
                    break;
                case (3):
                    exitGame();
                    break;
                case (4):
                    SetMusic();
                    break;
                case (5):
                    SetSound();
                    break;
                default:
                    break;
            }
        }
    }

    private void newGame() {
        try {
            Level1.reinit();
        } catch (SlickException e) {
            e.printStackTrace();
        }
        stateBasedGame.enterState(LudumDare.GAMEPLAYSTATE);
        select.play();
        bgm.stop();
        music_started = false;
        first_try = false;
    }

    private void exitGame() {
        gameContainer.exit();
        select.play();
    }

    private void SetMusic() {
        if (LudumDare.MusicOn) {
            gameContainer.setMusicOn(false);
            LudumDare.MusicOn = false;
        } else {
            gameContainer.setMusicOn(true);
            LudumDare.MusicOn = true;
            select.play();
        }
    }

    private void SetSound() {
        if (LudumDare.SoundOn) {
            gameContainer.setSoundOn(false);
            LudumDare.SoundOn = false;
        } else {
            gameContainer.setSoundOn(true);
            LudumDare.SoundOn = true;
            select.play();
        }
    }

    private void continueGame() {
        if (first_try) {
            error.play();
        } else {
            stateBasedGame.enterState(LudumDare.GAMEPLAYSTATE);
            select.play();
            bgm.stop();
            music_started = false;
        }
    }

}
