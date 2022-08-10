import bagel.*;

/**
 * The class of the Potion.
 */
public class Potion extends Item{
    private final static String NAME = "Potion";
    private final static Image IMAGE = new Image("res/items/potion.png");
    private final static Image ICON = new Image("res/items/potionIcon.png");
    private final static int BOOST = 25;
    private boolean isActive = true;

    /**
     * Constructor of the Potion.
     * @param x x location.
     * @param y y location.
     */
    public Potion(int x, int y) {
        super(IMAGE, ICON, NAME, x, y);
    }

    /**
     * Update method of the Potion.
     * @param sailor Current sailor.
     */
    public void update(Sailor sailor) {
        if (isActive) {
            checkCollisions(sailor);
            render(getX(), getY());
        }
    }

    /**
     * Check if the sailor is picking up the Potion,
     * boost the health of the sailor if it picks up the potion.
     * @param sailor Current sailor.
     * @return
     */
    @Override
    public boolean checkCollisions(Sailor sailor) {
        if (super.checkCollisions(sailor)) {
            sailor.boostHealth(BOOST);
            System.out.println("Sailor finds Potion. Sailor's current health: "
                    + (int) sailor.getHealth().getCurrHealth() + "/" + (int) sailor.getHealth().getMaxHealth());
            isActive = false;
            return true;
        }
        return false;
    }
}
