import bagel.*;

/**
 * The class of the Elixir.
 */
public class Elixir extends Item{
    private final static String NAME = "Elixir";
    private final static Image IMAGE = new Image("res/items/elixir.png");
    private final static Image ICON = new Image("res/items/elixirIcon.png");
    private final static int BOOST = 35;
    private boolean isActive = true;

    /**
     * The constructor of Elixir.
     * @param x x location of the Elixir.
     * @param y y location of the Elixir.
     */
    public Elixir(int x, int y) {
        super(IMAGE, ICON, NAME, x, y);
    }

    /**
     * The update method of the Elixir.
     * @param sailor The current sailor.
     */
    public void update(Sailor sailor) {
        if (isActive) {
            checkCollisions(sailor);
            render(getX(), getY());
        }
    }

    /**
     * Check whether the sailor picks up the Elixir,
     * boost the maximum health capacity of the sailor
     * and set the health of the sailor to maximum after picking up.
     * @param sailor The current sailor.
     * @return Return true if the sailor picks up the Elixir, false otherwise.
     */
    @Override
    public boolean checkCollisions(Sailor sailor) {
        if (super.checkCollisions(sailor)) {
            sailor.boostMaxHealth(BOOST);
            sailor.boostHealth(sailor.getMaxHealth());
            System.out.println("Sailor finds Elixir. Sailor's current health: "
                    + (int) sailor.getHealth().getCurrHealth() + "/" + (int) sailor.getHealth().getMaxHealth());
            isActive = false;
            return true;
        }
        return false;
    }
}
