package com.xatulu.LudumDare;

import com.xatulu.LudumDare.Entities.Player;
import com.xatulu.LudumDare.Intro.Text;
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
public class GameplayState extends BasicGameState implements KeyListener {

    private int stateID = 1;
    public int elapsedTime = 0;
    public static int jumpheight = 0;
    public static int jumptimer = 0;
    public int offsetX = 0;
    int music_track = 0;
    public int offsetY = 0;
    boolean music_playing = false;
    private TiledMap level1;
    public static boolean[][] blocked;
    public static boolean saved = false;
    private boolean left, right, up;
    private boolean down = true;
    public static final int SIZE = 32;

    Random random = new Random(System.currentTimeMillis());

    Music[] bgm = null;

    Sound jump = null;

    private Player player = null;

    public static Font font = null;

    private enum STATES {LEVEL1_STATE, EXIT_STATE}

    private STATES currentState = null;

    private SpriteSheet sheet = null;

    public GameplayState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.enter(gameContainer, stateBasedGame);
        currentState = STATES.LEVEL1_STATE;
    }

    @Override
    public int getID() {
        return stateID;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        font = new AngelCodeFont("res/font.fnt", "res/font_0.png");
        sheet = new SpriteSheet("res/SpriteSheet.png", 32, 32);
        player = new Player(320, 150, sheet.getSprite(0, 0), sheet.getSprite(1, 0), new Animation(new Image[]{sheet.getSprite(0, 0), sheet.getSprite(2, 0)}, 250), new Animation(new Image[]{sheet.getSprite(1, 0), sheet.getSprite(3, 0)}, 250));
        bgm = new Music[]{
                new Music("res/level1.ogg"),
                new Music("res/level2.ogg"),
                new Music("res/level3.ogg"),
                new Music("res/level4.ogg"),
                new Music("res/level5.ogg")
        };
        level1 = new TiledMap("res/level1.tmx");
        jump = new Sound("res/jump.wav");

        buildCollision();
    }

    private void buildCollision() {
        blocked = new boolean[level1.getWidth()][level1.getHeight()];
        for (int xAxis = 0; xAxis < level1.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < level1.getHeight(); yAxis++) {
                int tileID = level1.getTileId(xAxis, yAxis, 0);
                if (tileID == 7) {
                    blocked[xAxis][yAxis] = true;
                }
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setBackground(Color.white);
        graphics.setColor(Color.black);
        level1.render(0 + offsetX, 0 + offsetY, 1);
        level1.render(0 + offsetX, 0 + offsetY, 2);

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
        Input input = gameContainer.getInput();
        elapsedTime += i;
        startMusic();
        switch (currentState) {
            case LEVEL1_STATE:
                float dx = 0;
                float dy = 0;
                if (jumpheight<-96){
                    up=false;
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
                    player.move(dx * i * 0.4f, dy * i * 0.4f, offsetX, offsetY);
                    jumpheight+=dy * i * 0.4f;
                }
                if (player.validLocation(player.getX(), player.getY() + i * 0.4f, offsetX, offsetY)) {
                    player.setY((int) ((double) player.getY() + i * 0.4));
                }
                if (!up) {
                    jumpheight = 0;
                }
                updateCamera();
            case EXIT_STATE:
                break;
        }
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
        }
        if ((i == Input.KEY_RIGHT)) {
            right = true;
            player.setMoving(true);
            player.setDir(1);
        }
        if ((i == Input.KEY_UP) && !player.isInAir()) {
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
            player.setLastdir(-1);
        }
        if ((i == Input.KEY_RIGHT)) {
            right = false;
            player.setMoving(false);
            player.setLastdir(1);
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
