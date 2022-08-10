import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2022
 *
 * Please fill your name below
 * Junfei Zhang 1255069
 */
public class ShadowPirate extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private int level = 0;
    private LevelZero level0 = new LevelZero();
    private LevelOne level1 = new LevelOne();

    /**
     * Constructor of the game.
     */
    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
    }

    /**
     * Method used to read file and create objects
     */
    public void readCSV(String fileName) {}

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     * @param input Input of the player.
     */
    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        if (input.wasPressed(Keys.W)) {
            level = 1;
        }
        if (level == 0) {
            level0.update(input);
            if (level0.isCompleted()) {
                level = 1;
            }
        } else {
            level1.update(input);
            if (level1.isGameWin()) {
                level1.drawWinScreen();
            }
        }
    }
}
