import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Level zero of the game, where the sailor needs to reach the ladder.
 */
public class LevelZero extends Level {
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static String CSV0 = "res/level0.csv";
    private final static String BLOCK_NAME = "Block";
    private final static String SAILOR_NAME = "Sailor";
    private final static String PIRATE_NAME = "Pirate";
    private final static String TOPLEFT_NAME = "TopLeft";
    private final static String BOTRIGHT_NAME = "BottomRight";
    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String ATTACK_MESSAGE = "PRESS S TO ATTACK";
    private final static String INSTRUCTION = "USE ARROW KEYS TO FIND LADDER";
    private final static String WIN_MESSAGE = "LEVEL COMPLETE!";
    private final static String END_MESSAGE = "GAME OVER";
    private final static int WIN_TIME = 3;
    private int borderXHigh;
    private int borderXLow;
    private int borderYLow;
    private int borderYHigh;
    private int winTimer;
    private Sailor sailor;
    ArrayList<Block> blocks = new ArrayList<Block>();
    ArrayList<Pirate> pirates = new ArrayList<Pirate>();

    /**
     * Constructor of level zero.
     */
    public LevelZero() {
        super();
        setStartMessage(START_MESSAGE);
        setAttackMessage(ATTACK_MESSAGE);
        setInstructionMessage(INSTRUCTION);
        setEndMessage(END_MESSAGE);
        setWinMessage(WIN_MESSAGE);
        setBackground(BACKGROUND_IMAGE);
        setCSV(CSV0);
        readCSV(getCSV());
    }

    /**
     * Method to read the csv file of this level.
     * @param fileName File name of the csv world file.
     */
    @Override
    public void readCSV(String fileName){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String text;
            int i = 0;
            while ((text = br.readLine()) != null) {
                String[] cells = text.split(",");
                if (cells[0].equals(SAILOR_NAME)) {
                    sailor = new Sailor(Integer.parseInt(cells[1]), Integer.parseInt(cells[2]));
                    sailor.setPrev(sailor.getCurrentX(), sailor.getCurrentY());
                } else if (cells[0].equals(BLOCK_NAME)) {
                    blocks.add(new Block(Integer.parseInt(cells[1]), Integer.parseInt(cells[2])));
                } else if (cells[0].equals(PIRATE_NAME)) {
                    pirates.add(new Pirate(Integer.parseInt(cells[1]), Integer.parseInt(cells[2])));
                } else if (cells[0].equals(TOPLEFT_NAME)) {
                    borderXLow = Integer.parseInt(cells[1]);
                    borderYLow = Integer.parseInt(cells[2]);
                } else if (cells[0].equals(BOTRIGHT_NAME)) {
                    borderXHigh = Integer.parseInt(cells[1]);
                    borderYHigh = Integer.parseInt(cells[2]);
                }
                i++;
            }
            sailor.setBoundary(borderXLow, borderXHigh, borderYLow, borderYHigh);
            for (Pirate pirate : pirates) {
                pirate.setBoundary(borderXLow, borderXHigh, borderYLow, borderYHigh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update method of level zero.
     * @param input Input of the player.
     */
    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        if (isGameEnd()){
            drawEndScreen();
        }
        if (isGameWin()) {
            if (winTimer < WIN_TIME * getHertz()) {
                winTimer++;
                drawWinScreen();
            }
        }
        if (!isGameOn()){
            drawStartScreen(input);
        } else if (isGameOn() && !isGameWin() && !isGameEnd()){
            drawBackground();
        }
        // when game is running
        if (isGameOn() && !isGameEnd() && !isGameWin()){
            for (Block block : blocks) {
                block.update(sailor, pirates);
            }
            for (Pirate pirate : pirates) {
                pirate.update(blocks, sailor);
            }
            sailor.update(input, blocks, pirates);
            if (sailor.isDead()){
                setGameEnd(true);
            }
            if (sailor.hasWon(0)){
                setGameWin(true);
            }
        }
    }

    /**
     * Check if the level has completed.
     * @return True if the level has completed, false otherwise.
     */
    public boolean isCompleted() {
        if (isGameWin() && (winTimer < WIN_TIME * getHertz())) {
            return false;
        }
        return isGameWin();
    }
}
