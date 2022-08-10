import bagel.*;

/**
 * The class of the treasure.
 */
public class Treasure extends Item{
    private final static String NAME = "Treasure";
    private final static Image IMAGE = new Image("res/treasure.png");

    /**
     * Constructor of the treasure.
     * @param x x location.
     * @param y y location.
     */
    public Treasure(int x, int y) {
        super(IMAGE, IMAGE, NAME, x, y);
    }

    /**
     * Performs a state update.
     * @param sailor Current sailor.
     */
    public void update(Sailor sailor) {
        checkCollisions(sailor);
        render(getX(), getY());
    }

    /**
     *  Check if the sailor reaches the treasure,
     *  set the sailor to win if it reaches the treasure.
     * @param sailor Current sailor.
     * @return True if the sailor reaches the treasure, false otherwise.
     */
    @Override
    public boolean checkCollisions(Sailor sailor) {
        if (super.checkCollisions(sailor)) {
            sailor.setWin();
            return true;
        }
        return false;
    }
}
