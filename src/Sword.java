import bagel.*;

/**
 * Class of the Sword.
 */
public class Sword extends Item{
    private final static String NAME = "Sword";
    private final static Image IMAGE = new Image("res/items/sword.png");
    private final static Image ICON = new Image("res/items/swordIcon.png");
    private final static int DAMAGE_BOOST = 15;
    private boolean isActive = true;

    /**
     * Constructor of the sword.
     * @param x x location.
     * @param y y location.
     */
    public Sword(int x, int y) {
        super(IMAGE, ICON, NAME, x, y);
    }

    /**
     * Performs a state update.
     * @param sailor Current sailor.
     */
    public void update(Sailor sailor) {
        if (isActive) {
            checkCollisions(sailor);
            render(getX(), getY());
        }
    }

    /**
     * Check if the sailor is picking up the sword,
     * boost the damage value of the sailor if it picks up the sword.
     * @param sailor Current sailor.
     * @return True if the sailor is picking up the sword, false otherwise.
     */
    @Override
    public boolean checkCollisions(Sailor sailor) {
        if (super.checkCollisions(sailor)) {
            sailor.setDamage(sailor.getDamage() + DAMAGE_BOOST);
            System.out.println("Sailor finds Sword. Sailor's damage points increased to " + (int) sailor.getDamage());
            isActive = false;
            return true;
        }
        return false;
    }

}
