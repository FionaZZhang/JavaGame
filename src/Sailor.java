import bagel.*;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * Class of the sailor.
 */
public class Sailor extends Character{
    private final static Image SAILOR_LEFT = new Image("res/sailor/sailorLeft.png");
    private final static Image SAILOR_RIGHT = new Image("res/sailor/sailorRight.png");
    private final static Image HITLEFT = new Image("res/sailor/sailorHitLeft.png");
    private final static Image HITRIGHT = new Image("res/sailor/sailorHitRight.png");
    private final static int DAMAGE = 15;
    private final static int SPEED = 2;
    private final static int MAXHEALTH_INI = 100;
    private final static int HEALTHX = 10;
    private final static int HEALTHY = 25;
    private final static int HEALTH_SIZE = 30;
    private final static double ATTACK_TIME = 1;
    private final static double COOLDOWN = 2;
    private final static int WINX = 990;
    private final static int WINY = 630;
    private final static int ITEMS_OFFSET = 5;
    private boolean isAggressive;
    private int timer = 0;
    private boolean win = false;
    private ArrayList<Item> items = new ArrayList<Item>();

    /**
     * Constructor of the sailor.
     * @param x x position.
     * @param y y position.
     */
    public Sailor(int x, int y) {
        super(x, y);
        setDamage(DAMAGE);
        setSpeed(SPEED);
        setHealth(new Health(MAXHEALTH_INI, HEALTHX, HEALTHY, HEALTH_SIZE));
        setCurrentImage(SAILOR_RIGHT);
        setCooldownTime(COOLDOWN);
    }

    /**
     * Method that performs state update.
     * @param input Input of the player.
     * @param blocks ALl blocks.
     * @param pirates ALl pirates.
     */
    public void update(Input input, ArrayList<Block> blocks, ArrayList<Pirate> pirates){
        if (isAggressive()) {
            timer += 1;
        } else if (isCooldown()) {
            setCooldownTimer(getCooldownTimer() + 1);
        }
        if (isOutOfBound()) {
            moveBack();
        }
        if (input.isDown(Keys.UP)){
            setPrev(getCurrentX(), getCurrentY());
            move(0, -getSpeed());
        } else if (input.isDown(Keys.DOWN)){
            setPrev(getCurrentX(), getCurrentY());
            move(0, getSpeed());
        } else if (input.isDown(Keys.LEFT)){
            setPrev(getCurrentX(), getCurrentY());
            move(-getSpeed(),0);
            if (isAggressive()) {
                setCurrentImage(HITLEFT);
            } else {
                setCurrentImage(SAILOR_LEFT);
            }
        } else if (input.isDown(Keys.RIGHT)){
            setPrev(getCurrentX(), getCurrentY());
            move(getSpeed(),0);
            if (isAggressive()) {
                setCurrentImage(HITRIGHT);
            } else {
                setCurrentImage(SAILOR_RIGHT);
            }
        }
        if (input.wasPressed(Keys.S)) {
            if (!isCooldown()) {
                isAggressive = true;
            }
        }
        int space = HEALTHY + 2 * ITEMS_OFFSET;
        for (Item item : items) {
            item.getIcon().drawFromTopLeft(HEALTHX, space);
            space += ITEMS_OFFSET + item.getIcon().getHeight();
        }
        render(getCurrentX(), getCurrentY());
        checkCollisionsPirates(pirates);
        renderHealthPoints();
    }

    /**
     * Method that checks if sailor has reached the ladder.
     * @param level current level of the game.
     * @return Return true if the sailor has won, false otherwise.
     */
    public boolean hasWon(int level){
        if (level == 0) {
            return (getCurrentX() >= WINX) && (getCurrentY() > WINY);
        }
        return win;
    }

    /**
     * Set the sailor to winning state.
     */
    public void setWin() {
        win = true;
    }

    /**
     * Check if the sailor is attacking.
     * @return True if the sailor is attacking, false otherwise.
     */
    public boolean isAggressive() {
        if (timer > (ATTACK_TIME * getHertz())) {
            isAggressive = false;
            timer = 0;
            setCooldown(true);
            if (getCurrentImage() == HITLEFT) {
                setCurrentImage(SAILOR_LEFT);
            } else if  (getCurrentImage() == HITRIGHT){
                setCurrentImage(SAILOR_RIGHT);
            }
        }
        if (isAggressive) {
            if (getCurrentImage() == SAILOR_LEFT) {
                setCurrentImage(HITLEFT);
            } else if (getCurrentImage() == SAILOR_RIGHT){
                setCurrentImage(HITRIGHT);
            }
        }
        return isAggressive;
    }

    /**
     * Check if the sailor overlaps the pirates.
     * @param pirates All pirates in current level.
     * @return Return true if the sailor overlaps with a pirate, false otherwise.
     */
    public boolean checkCollisionsPirates(ArrayList<Pirate> pirates){
        Rectangle sailorBox = getCurrentImage().getBoundingBoxAt(getCenter());
        for (Pirate current : pirates) {
            Rectangle pirateBox = current.getBoundingBox();
            if (sailorBox.intersects(pirateBox)) {
                if (isAggressive) {
                    current.isAttacked(getDamage());
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Increase the health value of the sailor.
     * @param boost Value that will be increased.
     */
    public void boostHealth(double boost) {
        getHealth().boostHealth(boost);
    }

    /**
     * Increase the maximum health capacity of the sailor.
     * @param boost Value that will be increased.
     */
    public void boostMaxHealth(int boost) {
        getHealth().boostMaxHealth(boost);
    }

    /**
     * Add an item to the items list if the sailor picks it up.
     * @param item The item that the sailor picks up.
     */
    public void pickUp(Item item) {
        items.add(item);
    }

    /**
     * Deduct health value from the sailor's health.
     * @param hurt The health value that the sailor will lose.
     */
    public void isAttacked(double hurt) {
        getHealth().minusHealth(hurt);
    }

}
