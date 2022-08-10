import bagel.*;

/**
 * The class of the levels.
 */
public abstract class Level {
    private final static int FONT_SIZE = 55;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);
    private final static int FONT_Y = 402;
    private final static int OFFSET = 70;
    private final static int HERTZ = 60;
    private String startMessage;
    private String attackMessage;
    private String instructionMessage;
    private String endMessage;
    private String winMessage;
    private String CSV;
    private Image background;
    private boolean gameOn = false;
    private boolean gameEnd = false;
    private boolean gameWin = false;

    /**
     * Default constructor of a level.
     */
    public Level() {
    }

    /**
     * Update method of the level.
     * @param input Input of the player.
     */
    public abstract void update(Input input);

    /**
     * Get the font of messages.
     * @return Font of the messages.
     */
    public Font getFONT() {
        return FONT;
    }

    /**
     * Get y position of the messages.
     * @return y position of the messages.
     */
    public int getFontY() {
        return FONT_Y;
    }

    /**
     * Get the refreshing rate in hertz.
     * @return Refreshing rate in hertz.
     */
    public int getHertz() {
        return HERTZ;
    }

    /**
     * Get the start instruction message of the level.
     * @return Start message of the level.
     */
    public String getStartMessage() {
        return startMessage;
    }

    /**
     * Set the start instruction message of the level.
     * @param startMessage Start message of the level.
     */
    public void setStartMessage(String startMessage) {
        this.startMessage = startMessage;
    }

    /**
     * Set the attack instruction message.
     * @return Attack message of the level.
     */
    public String getAttackMessage() {
        return attackMessage;
    }

    /**
     * Set the attack instruction message.
     * @param attackMessage Attack message of the level.
     */
    public void setAttackMessage(String attackMessage) {
        this.attackMessage = attackMessage;
    }

    /**
     * Get the instruction message of the level.
     * @return Instruction message of the leve.
     */
    public String getInstructionMessage() {
        return instructionMessage;
    }

    /**
     * Set the instruction message of the level.
     * Instruction message of the level.
     * @param instructionMessage
     */
    public void setInstructionMessage(String instructionMessage) {
        this.instructionMessage = instructionMessage;
    }

    /**
     * Get the message that will be shown when the sailor dies.
     * @return End message of the level.
     */
    public String getEndMessage() {
        return endMessage;
    }

    /**
     * Set the message that will be shown when the sailor dies.
     * @param endMessage End message of the level.
     */
    public void setEndMessage(String endMessage) {
        this.endMessage = endMessage;
    }

    /**
     * Get the message that will be shown when the sailor wins.
     * @return Win message.
     */
    public String getWinMessage() {
        return winMessage;
    }

    /**
     * Set the message that will be shown when the sailor wins.
     * @param winMessage Win message.
     */
    public void setWinMessage(String winMessage) {
        this.winMessage = winMessage;
    }

    /**
     * Get background image.
     * @return Background image.
     */
    public Image getBackground() {
        return background;
    }

    /**
     * Set background image.
     * @param background Background image.
     */
    public void setBackground(Image background) {
        this.background = background;
    }

    /**
     * Get the name of the csv world file.
     * @return Name of the csv world file.
     */
    public String getCSV() {
        return CSV;
    }

    /**
     * Set the name of the csv world file.
     * @param CSV Name of the csv world file.
     */
    public void setCSV(String CSV) {
        this.CSV = CSV;
    }

    /**
     * Check if the game is on.
     * @return True if the game is on, false otherwise.
     */
    public boolean isGameOn() {
        return gameOn;
    }

    /**
     * Check if the game has ended.
     * @return True if the game has ended, false otherwise.
     */
    public boolean isGameEnd() {
        return gameEnd;
    }

    /**
     * Change the status of the game.
     * @param gameEnd Whether the game has ended.
     */
    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    /**
     * Check if the sailor has won.
     * @return True if the sailor has won, false otherwise.
     */
    public boolean isGameWin() {
        return gameWin;
    }

    /**
     * Change the winning status of the game.
     * @param gameWin True if the sailor has won, false otherwise.
     */
    public void setGameWin(boolean gameWin) {
        this.gameWin = gameWin;
    }

    /**
     * Method to read the csv world file.
     * @param fileName File name of the csv world file.
     */
    public abstract void readCSV(String fileName);

    /**
     * Render the background image.
     */
    public void drawBackground() {
        getBackground().draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
    }

    /**
     * Render the starting screen.
     * @param input Input of the player.
     */
    public void drawStartScreen(Input input){
        getFONT().drawString(getStartMessage(), (Window.getWidth()/2.0 - (getFONT().getWidth(getStartMessage())/2.0)),
                getFontY() - OFFSET);
        getFONT().drawString(getAttackMessage(), (Window.getWidth()/2.0 - (getFONT().getWidth(getAttackMessage())/2.0)),
                getFontY());
        getFONT().drawString(getInstructionMessage(), (Window.getWidth()/2.0
                        - (getFONT().getWidth(getInstructionMessage())/2.0)),
                (getFontY() + OFFSET));
        if (input.wasPressed(Keys.SPACE)){
            gameOn = true;
        }
    }

    /**
     * Draw the game-over screen.
     */
    public void drawEndScreen(){
        getFONT().drawString(getEndMessage(),
                (Window.getWidth()/2.0 - (getFONT().getWidth(getEndMessage())/2.0)), getFontY());
    }

    /**
     * Draw the screen after sailor has won.
     */
    public void drawWinScreen() {
        getFONT().drawString(getWinMessage(),
                (Window.getWidth()/2.0 - (getFONT().getWidth(getWinMessage())/2.0)), getFontY());
    }
}
