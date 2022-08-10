import bagel.*;
import bagel.util.Colour;

/**
 * The class of the health bar of all characters.
 */
public class Health {
    private final static DrawOptions COLOUR = new DrawOptions();
    private final static int ORANGE_BOUNDARY = 65;
    private final static int RED_BOUNDARY = 35;
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);
    private int fontsize;
    private Font FONT;
    private double maxHealth;
    private double currentHealth;
    private double x;
    private double y;

    /**
     * The constructor of the health class.
     * @param maxHealth Maximum health.
     * @param x x location of the health bar.
     * @param y y location of the health bar.
     * @param fontSize Fontsize to render the health value.
     */
    public Health(double maxHealth, double x, double y, int fontSize) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.x = x;
        this.y = y;
        this.fontsize = fontSize;
        this.FONT = new Font("res/wheaton.otf", fontsize);
        COLOUR.setBlendColour(GREEN);
    }

    /**
     * Decrease the health by certain value.
     * @param hurt The value that will be deducted from current health value.
     */
    public void minusHealth(double hurt) {
        this.currentHealth -= hurt;
    }

    /**
     * Increase the health by certain value.
     * @param boost The value that will be added to current health value.
     */
    public void boostHealth(double boost) {
        currentHealth += boost;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    /**
     * Increase the maximum capacity of the health.
     * @param boost The value that will be added to maximum health capacity.
     */
    public void boostMaxHealth(double boost) {
        maxHealth += boost;
    }

    /**
     * Get the maximum health value capacity.
     * @return Maximum health value.
     */
    public double getMaxHealth() {
        return maxHealth;
    }

    /**
     * Set the location of the health bar.
     * @param x x location of the health bar.
     * @param y y location of the health bar.
     */
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Render the health bar.
     */
    public void renderHealthPoints(){
        double percentageHP = ((double) currentHealth/maxHealth) * 100;
        if (percentageHP <= RED_BOUNDARY){
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY){
            COLOUR.setBlendColour(ORANGE);
        } else {
            COLOUR.setBlendColour(GREEN);
        }
        FONT.drawString(Math.round(percentageHP) + "%", x, y, COLOUR);
    }

    /**
     * Get current health value.
     * @return Current health value.
     */
    public double getCurrHealth() {
        return currentHealth;
    }
}
