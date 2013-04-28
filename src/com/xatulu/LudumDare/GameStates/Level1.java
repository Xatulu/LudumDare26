package com.xatulu.LudumDare.GameStates;

import com.xatulu.LudumDare.Entities.Player;
import com.xatulu.LudumDare.Levels.Level;
import com.xatulu.LudumDare.LudumDare;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.util.Random;

/**
 * User: Patrick
 * Date: 27.04.13
 * Time: 05:06
 */
public class Level1 extends BasicGameState implements KeyListener {

    private final Level level = null;
    private int stateID = 1;
    private static int jumpheight = 0;
    private int offsetX = 0;
    private int offsetY = 0;
    private boolean music_playing = false;
    private boolean left, right, up;
    public Level level1;
    public static final int SIZE = 32;

    private final Random random = new Random(System.currentTimeMillis());

    private Music[] bgm = null;

    private Sound jump = null;

    private Player player = null;

    public Level1() {
        this.stateID = LudumDare.GAMEPLAYSTATE;
    }

    @Override
    public int getID() {
        return stateID;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        SpriteSheet sheet = new SpriteSheet("res/graphics/SpriteSheet.png", 32, 32);
        player = new Player(96, 255, sheet.getSprite(0, 0), sheet.getSprite(1, 0), new Animation(new Image[]{sheet.getSprite(0, 0), sheet.getSprite(2, 0)}, 250), new Animation(new Image[]{sheet.getSprite(1, 0), sheet.getSprite(3, 0)}, 250));
        bgm = new Music[]{
                new Music("res/music/level2.ogg"),
                new Music("res/music/level3.ogg"),
                new Music("res/music/level4.ogg"),
                new Music("res/music/level5.ogg"),
                new Music("res/music/level6.ogg")
        };
        jump = new Sound("res/sounds/jump.wav");
        level1 = new Level(new TiledMap("res/graphics/level1.tmx"));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setBackground(Color.white);
        graphics.setColor(Color.black);
        level1.getMap().render(offsetX, offsetY, 1);
        level1.getMap().render(offsetX, offsetY, 2);

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
            dy -= 3;
        }
        if ((dx != 0) || (dy != 0)) {
            player.move(dx * i * 0.4f, dy * i * 0.4f, offsetX, offsetY, this.level);
            jumpheight += dy * i * 0.4f;
        }
        if (player.validLocation(player.getX(), player.getY() + i * 0.4f, offsetX, offsetY, this.level)) {
            player.setY((int) ((double) player.getY() + i * 0.4));
        }
        if (!up) {
            jumpheight = 0;
        }
        updateCamera();
    }

    private void updateCamera() {
        if (LudumDare.WIDTH - player.getX() < 96) {
            offsetX -= 96 - (LudumDare.WIDTH - player.getX());
            player.setX(LudumDare.WIDTH - 96);
        }
        if (player.getX() < 96) {
            offsetX += 96 - player.getX();
            player.setX(96);
        }
        if (LudumDare.HEIGHT - player.getY() < 96) {
            offsetY -= 96 - (LudumDare.HEIGHT - player.getY());
            player.setY(LudumDare.HEIGHT - 96);
        }
        if (player.getY() < 96) {
            offsetY += 96 - player.getY();
            player.setY(96);
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
            bgm[random.nextInt(bgm.length - 1)].loop();
            music_playing = true;
        }
    }


}
