import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The abstract class of all characters (sailor, pirates, Blackbeard).
 */
public abstract class Character implements Renderable, Moveable {
    private final static int HERTZ = 60;
    private Image currentImage;
    private double currentX;
    private double currentY;
    private double prevX;
    private double prevY;
    private double speed;
    private int damage;
    private int borderXLow;
    private int borderXHigh;
    private int borderYLow;
    private int borderYHigh;
    private int cooldownTimer = 0;
    private boolean isDead = false;
    private boolean isCooldown = false;
    private double cooldownTime;
    private Health health;

    /**
     * The constructor of a character.
     * @param x x position of the character.
     * @param y y position of the character.
     */
    public Character(int x, int y) {
        this.currentX = x;
        this.currentY = y;
        this.prevX = x;
        this.prevY = y;
    }

    /**
     * Get previous x position.
     * @return Previous x position.
     */
    public double getPrevX() {
        return prevX;
    }

    /**
     * Get previous y position.
     * @return Previous y position.
     */
    public double getPrevY() {
        return prevY;
    }

    /**
     * Get the health object of the character.
     * @return The health object.
     */
    public Health getHealth() {
        return health;
    }

    /**
     * Set the health object of the character.
     * @param health The health object.
     */
    public void setHealth(Health health) {
        this.health = health;
    }

    /**
     * Get current x position of the character.
     * @return Current x position.
     */
    public double getCurrentX() {
        return currentX;
    }

    /**
     * Get current y position of the character.
     * @return Current y position.
     */
    public double getCurrentY() {
        return currentY;
    }

    /**
     * Set position of the character.
     * @param currentX New x position.
     * @param currentY New y position.
     */
    public void setCurrent(double currentX, double currentY) {
        this.currentX = currentX;
        this.currentY = currentY;
    }

    /**
     * Set previous position of the character.
     * @param prevX Previous x position.
     * @param prevY Previous y position.
     */
    public void setPrev(double prevX, double prevY) {
        this.prevX = prevX;
        this.prevY = prevY;
    }

    /**
     * Get the current speed of the character.
     * @return Current speed.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Move the character by corresponding changes in x and y.
     * @param xMove Changes in x.
     * @param yMove Changes in y.
     */
    public void move(double xMove, double yMove){
        setCurrent(currentX + xMove, currentY + yMove);
    }

    /**
     * Move the character back to previous location.
     */
    public void moveBack(){
        setCurrent(getPrevX(), getPrevY());
    }

    /**
     * Get the damage point of the character.
     * @return The damage point of the character.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Set the damage point of the character.
     * @param damage The new damage point.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Set the speed of the character.
     * @param speed The new speed.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Set the current image of the character.
     * @param image The current image.
     */
    public void setCurrentImage(Image image) {
        this.currentImage = image;
    }

    /**
     * Get the current image of the character.
     * @return Current image.
     */
    public Image getCurrentImage() {
        return currentImage;
    }

    /**
     * Get the left border.
     * @return Left border.
     */
    public int getBorderXLow() {
        return borderXLow;
    }

    /**
     * Get the right border.
     * @return Right border.
     */
    public int getBorderXHigh() {
        return borderXHigh;
    }

    /**
     * Get the upper border.
     * @return Upper border.
     */
    public int getBorderYLow() {
        return borderYLow;
    }

    /**
     * Get the lower border.
     * @return Lower border.
     */
    public int getBorderYHigh() {
        return borderYHigh;
    }

    /**
     * Set the four borders.
     * @param borderXLow left border.
     * @param borderXHigh Right border.
     * @param borderYLow Upper border.
     * @param borderYHigh Lower border.
     */
    public void setBoundary(int borderXLow, int borderXHigh, int borderYLow, int borderYHigh) {
        this.borderXLow = borderXLow;
        this.borderXHigh = borderXHigh;
        this.borderYLow = borderYLow;
        this.borderYHigh = borderYHigh;
    }

    /**
     * Check if the character is out of bound.
     * @return Return true if the character is out of bound, false otherwise.
     */
    public boolean isOutOfBound(){
        return ((double) currentY > (double) borderYHigh) || ((double) currentY < (double) borderYLow)
                || ((double) currentX < (double) borderXLow) || ((double) currentX > (double) borderXHigh);
    }

    /**
     * Get a rectangular bounding box of the character image.
     * @return Rectangular bounding box.
     */
    public Rectangle getBoundingBox(){
        return currentImage.getBoundingBoxAt(getCenter());
    }

    /**
     * Get the center of the character image.
     * @return The center point of the character image.
     */
    public Point getCenter() {
        return new Point(getCurrentX() + currentImage.getWidth()/2, getCurrentY() + currentImage.getHeight()/2);
    }

    /**
     * Render the health of the character.
     */
    public void renderHealthPoints(){
        health.renderHealthPoints();
    }

    /**
     * Check if the character is dead.
     * @return Return true if the character has no health left, false otherwise.
     */
    public boolean isDead() {
        if (health.getCurrHealth() <= 0) {
            isDead = true;
        }
        return isDead;
    }

    /**
     * Get the maximum health of the character.
     * @return Maximum health capacity.
     */
    public double getMaxHealth() {
        return health.getMaxHealth();
    }

    /**
     * Get the refreshing rate in hertz.
     * @return Refreshing rate in hertz.
     */
    public int getHertz() {
        return HERTZ;
    }

    /**
     * Check if the character is in cooldown phase.
     * @return True if the character is in cooldown phase, false otherwise.
     */
    public boolean isCooldown() {
        if (cooldownTimer > (cooldownTime * getHertz())) {
            isCooldown = false;
            cooldownTimer = 0;
        }
        return isCooldown;
    }

    /**
     * Get the timer which counts cooldown time.
     * @return The value of the timer.
     */
    public int getCooldownTimer() {
        return cooldownTimer;
    }

    /**
     * Update the cooldown timer.
     * @param cooldownTimer The new value of the timer.
     */
    public void setCooldownTimer(int cooldownTimer) {
        this.cooldownTimer = cooldownTimer;
    }

    /**
     * Change the cooldown phase of the character.
     * @param cooldown Whether the character will be in cooldown phase.
     */
    public void setCooldown(boolean cooldown) {
        isCooldown = cooldown;
    }

    /**
     * Set the cooldown time length of the character.
     * @param cooldownTime Cooldown time length.
     */
    public void setCooldownTime(double cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    /**
     * Render the character from top left at a location.
     * @param x x position.
     * @param y y position.
     */
    public void render(double x, double y) {
        getCurrentImage().drawFromTopLeft(x, y);
    }

    /**
     * Behaviour of the character when it is attacked.
     * @param hurt The health value that the character will lose.
     */
    public abstract void isAttacked(double hurt);

}
