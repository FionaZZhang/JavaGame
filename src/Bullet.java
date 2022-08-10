import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The class of bullet;
 * it can move according to required path,
 * and inflics damage to the sailor.
 */
public class Bullet implements Renderable, Moveable {
    private String owner;
    private double damage;
    private double speed;
    private double x;
    private double y;
    private double rotation;
    private DrawOptions drawoptions = new DrawOptions();
    private double sailorX;
    private double sailorY;
    private double pirateX;
    private double pirateY;
    private double borderXLow;
    private double borderXHigh;
    private double borderYLow;
    private double borderYHigh;
    private boolean isActive = true;
    private Image image;

    /**
     * The constructor of a bullet.
     * @param image The image of the bullet.
     * @param sailorX The targeting x position.
     * @param sailorY The targeting y position.
     * @param pirateX The initial x position.
     * @param pirateY The initial y position
     * @param pirate The pirate firing the bullet.
     */
    public Bullet(Image image, double sailorX, double sailorY, double pirateX, double pirateY, Pirate pirate) {
        this.image = image;
        this.sailorX = sailorX;
        this.sailorY = sailorY;
        this.pirateX = pirateX;
        this.pirateY = pirateY;
        this.x = pirateX;
        this.y = pirateY;
        rotation = getRotation(sailorX, sailorY, x, y);
        this.borderXLow = pirate.getBorderYLow();
        this.borderXHigh = pirate.getBorderXHigh();
        this.borderYLow = pirate.getBorderYLow();
        this.borderYHigh = pirate.getBorderYHigh();
        this.damage = pirate.getDamage();
        this.speed = pirate.getProjectileSpeed();
    }

    /**
     * The update method of the bullet.
     * It will render the bullet,
     * move it along the desired path,
     * and inflicts damage to the sailor if it is shot.
     * @param sailor The current sailor.
     */
    public void update(Sailor sailor) {
        if (!isActive || isOutOfBound()) {
            return;
        }
        if ((sailorX >= pirateX) && (sailorY >= pirateY)) {
            move(Math.cos(rotation) * speed, Math.sin(rotation) * speed);
            drawoptions.setRotation(rotation);
        } else if ((sailorX <= pirateX) && (sailorY >= pirateY)) {
            move(-Math.cos(rotation) * speed, Math.sin(rotation) * speed);
            drawoptions.setRotation(Math.PI - rotation);
        } else if ((sailorX <= pirateX) && (sailorY <= pirateY)) {
            move(-Math.cos(rotation) * speed, -Math.sin(rotation) * speed);
            drawoptions.setRotation(Math.PI + rotation);
        } else if ((sailorX >= pirateX) && (sailorY <= pirateY)) {
            move(Math.cos(rotation) * speed, -Math.sin(rotation) * speed);
            drawoptions.setRotation(2 * Math.PI - rotation);
        }
        if (! checkCollisionsSailor(sailor)) {
            render(x, y);
        }
    }

    /**
     * Render the bullet.
     * @param x The x position.
     * @param y The y position.
     */
    public void render(double x, double y) {
        image.drawFromTopLeft(x, y, drawoptions);
    }

    /**
     * Move the bullet.
     * @param xMove The change in x.
     * @param yMove The change in y.
     */
    public void move(double xMove, double yMove){
        setCurr(x + xMove, y + yMove);
    }

    /**
     * Set current position.
     * @param x New x position.
     * @param y New y position.
     */
    public void setCurr(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Rotate the bullet towards target.
     * @param sailorX The targeting x location.
     * @param sailorY The targeting y location.
     * @param pirateX The initial x location.
     * @param pirateY The initial y location.
     * @return Return the rotation in radians.
     */
    public double getRotation(double sailorX, double sailorY, double pirateX, double pirateY) {
        double xDiff = Math.abs(sailorX - pirateX);
        double yDiff = Math.abs(sailorY - pirateY);
        rotation = Math.atan(yDiff/xDiff);
        return rotation;
    }

    /**
     * Check whether the bullet shoots the sailor,
     * inflict damage to the sailor if it does.
     * @param sailor The current sailor.
     * @return Return true if it shoots the sailor, false otherwise.
     */
    public boolean checkCollisionsSailor(Sailor sailor) {
        double distance = sailor.getCenter().distanceTo(getCenter());
        if (distance <= sailor.getCurrentImage().getHeight()/2) {
            attack(sailor);
            isActive = false;
            return true;
        }
        return false;
    }

    /**
     * Inflict damage to the character.
     * @param character The character that is attacked by the bullet.
     */
    public void attack(Character character) {
        character.isAttacked(damage);
        System.out.println(owner + " inflicts " + (int) damage + " damage points on Sailor. Sailor's current health: "
                + (int) character.getHealth().getCurrHealth() + "/" + (int) character.getHealth().getMaxHealth());
    }

    /**
     * Get the center point of the bullet.
     * @return Return the center point.
     */
    public Point getCenter() {
        return new Point(x + image.getWidth()/2, y + image.getHeight()/2);
    }

    /**
     * Check if the bullet is out of bound.
     * @return Return true if the bullet is out of bound, false otherwise.
     */
    public boolean isOutOfBound(){
        return ((double) y > (double) borderYHigh) || ((double) y < (double) borderYLow)
                || ((double) x < (double) borderXLow) || ((double) x > (double) borderXHigh);
    }

    /**
     * Get a bounding box of the bllet.
     * @return Return the rectangular bounding box.
     */
    public Rectangle getBoundingBox() {
        return image.getBoundingBoxAt(getCenter());
    }

    /**
     * Set the owner of the bullet (normal pirate or Blackbeard).
     * @param owner The name of the owner of the bullet.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Get the owner of the bullet
     * @return Return the name of the owner.
     */
    public String getOwner() {
        return owner;
    }
}
