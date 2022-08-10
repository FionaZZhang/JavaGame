import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * The class of Bomb,
 * which has the same effect to pirates but will cause damage to the sailor if the sailor collides with a bomb.
 */
public class Bomb extends Block{
    private final static Image IMAGE = new Image("res/bomb.png");
    private final static Image EXPLODE_IMAGE = new Image("res/explosion.png");
    private final static String NAME = "Block";
    private final static int DAMAGE = 10;
    private final static int HERTZ = 60;
    private final static double COOLDOWN = 0.5;
    private int timer = 0;
    private boolean isExplode = false;
    private boolean isActive = true;

    /**
     * Constructor method to create a bomb.
     * @param x The x position of the bomb.
     * @param y The y position of the bomb.
     */
    public Bomb(int x, int y) {
        super(x, y);
        setCurrImage(IMAGE);
        setName(NAME);
    }

    /**
     * The update method will render the bomb if is has not exploded,
     * turn the direction of pirates who collides with the bomb,
     * and explode and cause damage to the sailor if the sailor collides with the bomb.
     * @param sailor
     * @param pirates
     */
    public void update(Sailor sailor, ArrayList<Pirate> pirates) {
        if (isExplode()) {
            timer++;
        }
        if (isActive) {
            render(getX(), getY());
            checkCollisions(sailor);
            super.checkCollisions(sailor);
            for (Pirate pirate : pirates) {
                super.checkCollisions(pirate);
            }
        }

    }

    /**
     * Minus the health of the character.
     * @param character The bomb inflicts damage to this character.
     */
    public void attack(Character character) {
        character.isAttacked(DAMAGE);
        System.out.println("Bomb inflicts " + DAMAGE + " damage points on Sailor. Sailor's current health: "
                + (int) character.getHealth().getCurrHealth() + "/" + (int) character.getHealth().getMaxHealth());
    }

    /**
     * Check if the bomb is exploding.
     * @return returns whether the bomb is exploding.
     */
    public boolean isExplode() {
        if (timer > COOLDOWN * HERTZ) {
            timer = 0;
            isActive = false;
            isExplode = false;
        }
        return isExplode;
    }

    /**
     * Check if the sailor collides with the bomb,
     * and inflict damage to the sailor if it collides with the bomb.
     */
    @Override
    public boolean checkCollisions(Sailor sailor){
        Rectangle sailorBox =
                sailor.getCurrentImage().getBoundingBoxAt(new Point(sailor.getCenter().x, sailor.getCenter().y));
        if (!isExplode && sailorBox.intersects(getBoundingBox())) {
            isExplode = true;
            setCurrImage(EXPLODE_IMAGE);
            attack(sailor);
            return true;
        }
        return false;
    }
}
