import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Level one of the game, which will be played after level zero.
 * The sailor need to reach the treasure.
 */
public class LevelOne extends Level {
    private final static Image BACKGROUND_IMAGE = new Image("res/background1.png");
    private final static String CSV1 = "res/level1.csv";
    private final static String BOMB_NAME = "Block";
    private final static String SAILOR_NAME = "Sailor";
    private final static String PIRATE_NAME = "Pirate";
    private final static String BLACKBEARD_NAME = "Blackbeard";
    private final static String ELIXIR_NAME = "Elixir";
    private final static String POTION_NAME = "Potion";
    private final static String SWORD_NAME = "Sword";
    private final static String TREASURE_NAME = "Treasure";
    private final static String TOPLEFT_NAME = "TopLeft";
    private final static String BOTRIGHT_NAME = "BottomRight";
    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String ATTACK_MESSAGE = "PRESS S TO ATTACK";
    private final static String INSTRUCTION = "FIND THE TREASURE";
    private final static String WIN_MESSAGE = "YOU CAN FINISH THE ESSAY!";
    private final static String END_MESSAGE = "GAME OVER";
    private int borderXHigh;
    private int borderXLow;
    private int borderYLow;
    private int borderYHigh;
    private Sailor sailor;
    private Treasure treasure;
    ArrayList<Block> bombs = new ArrayList<>();
    ArrayList<Pirate> pirates = new ArrayList<>();
    ArrayList<Item> items = new ArrayList<>();


    /**
     * Constructor of level one.
     */
    public LevelOne() {
        super();
        setStartMessage(START_MESSAGE);
        setAttackMessage(ATTACK_MESSAGE);
        setInstructionMessage(INSTRUCTION);
        setEndMessage(END_MESSAGE);
        setWinMessage(WIN_MESSAGE);
        setBackground(BACKGROUND_IMAGE);
        setCSV(CSV1);
        readCSV(CSV1);
    }

    /**
     * Method to read the csv world file.
     * @param fileName File name of the csv world file.
     */
    @Override
    public void readCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] cells = text.split(",");
                if (cells[0].equals(SAILOR_NAME)) {
                    sailor = new Sailor(Integer.parseInt(cells[1]), Integer.parseInt(cells[2]));
                    sailor.setPrev(sailor.getCurrentX(), sailor.getCurrentY());
                } else if (cells[0].equals(BOMB_NAME)) {
                    bombs.add(new Bomb(Integer.parseInt(cells[1]), Integer.parseInt(cells[2])));
                } else if (cells[0].equals(PIRATE_NAME)) {
                    pirates.add(new Pirate(Integer.parseInt(cells[1]), Integer.parseInt(cells[2])));
                } else if (cells[0].equals(TOPLEFT_NAME)) {
                    borderXLow = Integer.parseInt(cells[1]);
                    borderYLow = Integer.parseInt(cells[2]);
                } else if (cells[0].equals(BOTRIGHT_NAME)) {
                    borderXHigh = Integer.parseInt(cells[1]);
                    borderYHigh = Integer.parseInt(cells[2]);
                } else if (cells[0].equals(ELIXIR_NAME)) {
                    items.add(new Elixir(Integer.parseInt(cells[1]), Integer.parseInt(cells[2])));
                } else if (cells[0].equals(POTION_NAME)) {
                    items.add(new Potion(Integer.parseInt(cells[1]), Integer.parseInt(cells[2])));
                } else if (cells[0].equals(SWORD_NAME)) {
                    items.add(new Sword(Integer.parseInt(cells[1]), Integer.parseInt(cells[2])));
                } else if (cells[0].equals(BLACKBEARD_NAME)) {
                    pirates.add(new Blackbeard(Integer.parseInt(cells[1]), Integer.parseInt(cells[2])));
                } else if (cells[0].equals(TREASURE_NAME)) {
                    treasure = new Treasure(Integer.parseInt(cells[1]), Integer.parseInt(cells[2]));
                }
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
     * Update method of level one.
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
            drawWinScreen();
        }
        if (!isGameOn()){
            drawStartScreen(input);
        } else if (isGameOn() && !isGameWin() && !isGameEnd()){
            drawBackground();
        }
        // when game is running
        if (isGameOn() && !isGameEnd() && !isGameWin()){
            for (Block bomb : bombs) {
                bomb.update(sailor, pirates);
            }
            for (Pirate pirate : pirates) {
                pirate.update(bombs, sailor);
            }
            for (Item item: items) {
                item.update(sailor);
            }
            sailor.update(input, bombs, pirates);
            treasure.update(sailor);
            if (sailor.isDead()){
                setGameEnd(true);
            }
            if (sailor.hasWon(1)){
                setGameWin(true);
            }
        }
    }
}
