package com.xatulu.game.GameStates;

import com.xatulu.game.Entities.Player;
import com.xatulu.game.Levels.Level;
import com.xatulu.game.MinimalJump;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Pauseable;

import java.util.Random;

/**
 * User: Patrick
 * Date: 27.04.13
 * Time: 05:06
 */
public class Level1 extends BasicGameState implements KeyListener, Pauseable {

    public static final int SIZE = 32;
    private static int jumpheight = 0;
    private final Level level = null;
    private final Random random = new Random(System.currentTimeMillis());
    public Level level1;
    private int stateID = 1;
    private static int offsetX = 0;
    private static int offsetY = 0;
    private boolean music_playing = false;
    private boolean left, right, up;
    private Music[] bgm = null;
    private int current_song;
    private Sound jump = null;
    private static Player player = null;
    private Image controls;
    private static GameContainer gameContainer;
    private static StateBasedGame stateBasedGame;

    public Level1() {
        this.stateID = MinimalJump.GAMEPLAYSTATE;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        SpriteSheet sheet = new SpriteSheet("graphics/SpriteSheet.png", 32, 32);
        player = new Player(96, 255, sheet.getSprite(0, 10), sheet.getSprite(0, 10).getFlippedCopy(true, false), new Animation(new Image[]{sheet.getSprite(0, 10), sheet.getSprite(1, 10), sheet.getSprite(2, 10), sheet.getSprite(3, 10), sheet.getSprite(4, 10), sheet.getSprite(5, 10), sheet.getSprite(6, 10), sheet.getSprite(7, 10)}, 250), new Animation(new Image[]{sheet.getSprite(0, 10).getFlippedCopy(true, false), sheet.getSprite(1, 10).getFlippedCopy(true, false), sheet.getSprite(2, 10).getFlippedCopy(true, false), sheet.getSprite(3, 10).getFlippedCopy(true, false), sheet.getSprite(4, 10).getFlippedCopy(true, false), sheet.getSprite(5, 10).getFlippedCopy(true, false), sheet.getSprite(6, 10).getFlippedCopy(true, false), sheet.getSprite(7, 10).getFlippedCopy(true, false)}, 250));
        bgm = new Music[]{
                new Music("music/level2.ogg"),
                new Music("music/level3.ogg"),
                new Music("music/level4.ogg"),
                new Music("music/level5.ogg"),
                new Music("music/level6.ogg")
        };
        controls = new Image("graphics/controls.png");
        jump = new Sound("sounds/jump.wav");
        level1 = new Level(new TiledMap("graphics/level1.tmx"));
        Level1.gameContainer = gameContainer;
        Level1.stateBasedGame = stateBasedGame;
    }

    public static void reinit() throws SlickException {
        SpriteSheet sheet = new SpriteSheet("graphics/SpriteSheet.png", 32, 32);
        player = new Player(96, 255, sheet.getSprite(0, 10), sheet.getSprite(0, 10).getFlippedCopy(true, false), new Animation(new Image[]{sheet.getSprite(0, 10), sheet.getSprite(1, 10), sheet.getSprite(2, 10), sheet.getSprite(3, 10), sheet.getSprite(4, 10), sheet.getSprite(5, 10), sheet.getSprite(6, 10), sheet.getSprite(7, 10)}, 500), new Animation(new Image[]{sheet.getSprite(0, 10).getFlippedCopy(true, false), sheet.getSprite(1, 10).getFlippedCopy(true, false), sheet.getSprite(2, 10).getFlippedCopy(true, false), sheet.getSprite(3, 10).getFlippedCopy(true, false), sheet.getSprite(4, 10).getFlippedCopy(true, false), sheet.getSprite(5, 10).getFlippedCopy(true, false), sheet.getSprite(6, 10).getFlippedCopy(true, false), sheet.getSprite(7, 10).getFlippedCopy(true, false)}, 500));
        offsetX = 0;
        offsetY = 0;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setBackground(Color.white);
        graphics.setColor(Color.black);
        level1.getMap().render(offsetX, offsetY, 1);
        level1.getMap().render(offsetX, offsetY, 2);
        Image temp = controls.getScaledCopy(0.8f);
        temp.setRotation(15);
        temp.draw(64 + offsetX, 75 + offsetY);

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
        startMusic();
        float dx = 0;
        float dy = 0;
        if (jumpheight < -96) {
            up = false;
        }
        if (left) {
            dx -= 1;
        }
        if (right) {
            dx += 1;
        }
        if (up) {
            dy -= 1;
        }
        if ((dx != 0) || (dy != 0)) {
            player.move(dx * i * 0.2f, dy * i * 0.5f, offsetX, offsetY, this.level);
            jumpheight += dy * i * 0.3f;
        }
        if (player.validLocation(player.getX(), player.getY() + i * 0.6f, offsetX, offsetY, this.level)) {
            player.setY((int) ((double) player.getY() + i * 0.275));
        }


        if (!up) {
            jumpheight = 0;
        }
        updateCamera();
        if (player.isReachedGoal()) {
            stateBasedGame.enterState(MinimalJump.GOALSTATE);
            bgm[current_song].stop();
            music_playing = false;
        }
    }

    private void updateCamera() {
        if (MinimalJump.WIDTH - player.getX() < 256) {
            offsetX -= 256 - (MinimalJump.WIDTH - player.getX());
            player.setX(MinimalJump.WIDTH - 256);
        }
        if (player.getX() < 256) {
            offsetX += 256 - player.getX();
            player.setX(256);
        }
        if (MinimalJump.HEIGHT - player.getY() < 164) {
            offsetY -= 164 - (MinimalJump.HEIGHT - player.getY());
            player.setY(MinimalJump.HEIGHT - 164);
        }
        if (player.getY() < 164) {
            offsetY += 164 - player.getY();
            player.setY(164);
        }
    }

    @Override
    public void keyPressed(int i, char c) {
        if ((i == Input.KEY_LEFT)) {
            left = true;
            player.setMoving(true);
            player.setDir(-1);
            player.setLastdir(-1);
        }
        if ((i == Input.KEY_RIGHT)) {
            right = true;
            player.setMoving(true);
            player.setDir(1);
            player.setLastdir(1);
        }
        if ((i == Input.KEY_UP) && player.isInAir()) {
            up = true;
            jump.play();
            player.setMoving(true);
            player.setInAir(true);
        }
        if ((i == Input.KEY_ESCAPE)) {
            if (isRenderPaused() || isUpdatePaused()) {
                unpauseRender();
                unpauseUpdate();
                bgm[current_song].resume();
            } else {
                pauseRender();
                pauseUpdate();
                bgm[current_song].pause();
            }
        }
        if ((i == Input.KEY_F5)) {
            if (MinimalJump.MusicOn) {
                MinimalJump.MusicOn = false;
                gameContainer.setMusicOn(false);
            } else {
                MinimalJump.MusicOn = true;
                gameContainer.setMusicOn(true);
            }
        }
        if ((i == Input.KEY_F6)) {
            if (MinimalJump.SoundOn) {
                MinimalJump.SoundOn = false;
                gameContainer.setSoundOn(false);
            } else {
                MinimalJump.SoundOn = true;
                gameContainer.setSoundOn(true);
            }
        }
        if ((i == Input.KEY_F2)) {
            stateBasedGame.enterState(MinimalJump.MAINMENUSTATE);
            bgm[current_song].stop();
            music_playing = false;
        }
        if ((i == Input.KEY_F10)) {
            try {
                reinit();
            } catch (SlickException e) {
                e.printStackTrace();
            }
            stateBasedGame.enterState(MinimalJump.DUMMYSTATE);
        }
    }

    @Override
    public void keyReleased(int i, char c) {
        if ((i == Input.KEY_LEFT)) {
            left = false;
            player.setMoving(false);
        }
        if ((i == Input.KEY_RIGHT)) {
            right = false;
            player.setMoving(false);
        }
        if ((i == Input.KEY_UP)) {
            up = false;
            player.setMoving(false);
        }
    }

    private void startMusic() {
        if (!music_playing) {
            current_song = random.nextInt(bgm.length - 1);
            bgm[current_song].loop();
            music_playing = true;
        }
    }
}