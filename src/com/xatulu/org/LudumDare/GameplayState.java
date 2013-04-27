package com.xatulu.org.LudumDare;

import com.xatulu.org.LudumDare.Entities.Player;
import com.xatulu.org.LudumDare.Intro.Text;
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
public class GameplayState extends BasicGameState{

    private int stateID = 1;
    private int elapsedTime = 0, jumptimer = 0;
    private int offsetX = 0;
    int music_track = 0;
    int offsetY = 0;
    boolean music_playing = false;
    private TiledMap level1;
    private boolean[][] blocked;
    private static final int SIZE = 32;
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
        player = new Player(75, 150, sheet.getSprite(0, 0), sheet.getSprite(1, 0), new Animation(new Image[]{sheet.getSprite(0, 0), sheet.getSprite(2, 0)}, 250), new Animation(new Image[]{sheet.getSprite(1, 0), sheet.getSprite(3, 0)}, 250));
        bgm = new Music[]{
                new Music("res/level1.ogg"),
                new Music("res/level2.ogg"),
                new Music("res/level3.ogg"),
                new Music("res/level4.ogg"),
                new Music("res/level5.ogg")
        };
        level1 = new TiledMap("res/testmap.tmx");
        jump = new Sound("res/jump.wav");

        buildCollision();
    }

    private void buildCollision() { //TODO Irgendwas stimmt hier nicht
        blocked = new boolean[level1.getWidth()][level1.getHeight()];
        for (int xAxis = 0; xAxis < level1.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < level1.getHeight(); yAxis++) {
                int tileID = level1.getTileId(xAxis, yAxis, 0);
                String value = level1.getTileProperty(tileID, "blocked", "false");
                if ("true".equals(value)) {
                    blocked[xAxis][yAxis] = true;
                }
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setBackground(Color.white);
        graphics.setColor(Color.black);
        level1.render(0 + offsetX, 0 + offsetY);

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
        startMusic();
        switch (currentState) {
            case LEVEL1_STATE:
                jumptimer += i;
                Input input = gameContainer.getInput();
                if (input.isKeyDown(Input.KEY_D)) {
                    player.setMoving(true);
                    player.setDir(1);
                    player.setLastdir(1);
                    if (!isBlocked(player.getX() - offsetX + i * 0.3f, player.getY())) {
                        offsetX -= i * 0.3;
                    }
                }
                if (input.isKeyDown(Input.KEY_A)) {
                    player.setMoving(true);
                    player.setDir(-1);
                    player.setLastdir(-1);
                    if (!isBlocked(player.getX() - offsetX - i * 0.3f, player.getY())) {
                        offsetX += i * 0.3;
                    }
                    player.setLastdir(-1);
                }
                if (input.isKeyDown(Input.KEY_W) && !player.isInAir() && (LudumDare.HEIGHT - player.getY() < 250)) {
                    player.setMoving(true);                                                    //TODO DoubleJumps entfernen

                    if (!isBlocked(player.getX() - offsetX, player.getY() - offsetY - i * 0.3f)) {
                        player.setY((int) ((double) player.getY() - i * 0.6));
                    }
                    if (isBlocked(player.getX() + offsetX, player.getY() + SIZE + i * 0.3f)){
                        player.setInAir(false);
                    }
                    if (LudumDare.HEIGHT - player.getY() > 220) {
                        player.setInAir(true);
                        jump.play();
                    }
                }

                if (!isBlocked(player.getX() + offsetX, player.getY() + SIZE + i * 0.3f)) {
                    player.setY((int) ((double) player.getY() + i * 0.3));
                }
            case EXIT_STATE:
                break;
        }
    }

    private void startMusic() {
        if(!music_playing){
            bgm[random.nextInt(bgm.length-1)].loop();
            music_playing=true;
        }
    }

    private void moveIntro(int i) {
        if (Text.intro0.getY() > 50) {
            Text.intro0.setY((int) (Text.intro0.getY() - i * 0.1));
            Text.intro1.setY((int) (Text.intro1.getY() - i * 0.1));
            Text.intro2.setY((int) (Text.intro2.getY() - i * 0.1));
        }
    }

    private boolean isBlocked(float x, float y) {
        int xBlock = (int) x / SIZE;
        int yBlock = (int) y / SIZE;
        return blocked[xBlock][yBlock];
    }
}
