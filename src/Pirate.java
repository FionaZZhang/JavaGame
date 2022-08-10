import java.util.ArrayList;
import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The class of all pirates.
 */
public class Pirate extends Character {
    private final static int DAMAGE = 10;
    private final static int ATTACK_RANGE = 100;
    private final static int MAXHEALTH_INI = 45;
    private final static int HEALTH_SIZE = 15;
    private final static int HEALTH_OFFSET = 6;
    private final static int UP = 1;
    private final static int RIGHT = 2;
    private final static int DOWN = 3;
    private final static int LEFT = 4;
    private final static double COOLDOWN = 3000 / 1000;
    private final static double INVINCIBLE_TIME = 1500 / 1000;
    private final static double PROJECTILE_SPEED = 0.4;
    private final static double SPEED_LOW = 0.2;
    private final static double SPEED_HIGH = 0.7;
    private final static String NAME = "Pirate";
    private final static Image LEFTIMG = new Image("res/pirate/pirateLeft.png");
    private final static Image RIGHTIMG = new Image("res/pirate/pirateRight.png");
    private final static Image HITLEFT = new Image("res/pirate/pirateHitLeft.png");
    private final static Image HITRIGHT = new Image("res/pirate/pirateHitRight.png");
    private final static Image BULLET = new Image("res/pirate/pirateProjectile.png");
    private Image leftImg;
    private Image rightImg;
    private Image hitLeftImg;
    private Image hitRightImg;
    private Image bulletImg;
    private int currentDirection;
    private int attackRange;
    private boolean isInvincible;
    private int invincibleTimer = 0;
    private double projectileSpeed;
    private String name;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    /**
     * Constructor of the pirates.
     * @param x x position.
     * @param y y position.
     */
    public Pirate(int x, int y) {
        super(x, y);
        setSpeed(getRandomSpeed());
        setRandomDirection();
        setDamage(DAMAGE);
        setHealth(new Health(MAXHEALTH_INI, x, y - HEALTH_OFFSET, HEALTH_SIZE));
        setCurrentImage(RIGHTIMG);
        setCooldownTime(COOLDOWN);
        setAttackRange(ATTACK_RANGE);
        setImages(LEFTIMG, RIGHTIMG, HITLEFT, HITRIGHT, BULLET);
        setProjectileSpeed(PROJECTILE_SPEED);
        setName(NAME);
    }

    /**
     * Update method of pirates.
     * @param blocks All blocks.
     * @param sailor Current sailor.
     */
    public void update(ArrayList<Block> blocks, Sailor sailor) {
        if (isOutOfBound()) {
            moveBack();
            turnDirection();
        }
        if (isInvincible()) {
            invincibleTimer += 1;
        }
        if (isCooldown()) {
            setCooldownTimer(getCooldownTimer() + 1);
        }
        move();
        for (Bullet bullet : bullets) {
            bullet.update(sailor);
        }
        if (!isDead()) {
            renderHealthPoints();
            render(getCurrentX(), getCurrentY());
            checkCollisionsSailor(sailor);
        }
    }

    /**
     * Set the speed of the bullet that is fired by this pirate.
     * @param projectileSpeed Speed of the bullet.
     */
    public void setProjectileSpeed(double projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    /**
     * Set the images of the pirate.
     * @param leftImg Image that is rendered when the pirate is facing left.
     * @param rightImg Image that is rendered when the pirate is facing right.
     * @param hitLeftImg Image that is rendered when the pirate is invincible and facing left.
     * @param hitRightImg Image that is rendered when the pirate is invincible and facing right.
     * @param bulletImg Image of the bullet.
     */
    public void setImages(Image leftImg, Image rightImg, Image hitLeftImg, Image hitRightImg, Image bulletImg) {
        this.leftImg = leftImg;
        this.rightImg = rightImg;
        this.hitLeftImg = hitLeftImg;
        this.hitRightImg = hitRightImg;
        this.bulletImg = bulletImg;
    }

    /**
     * Get a random speed of the pirate.
     * @return Random speed of the pirate.
     */
    public double getRandomSpeed() {
        return (double) ((Math.random() * (SPEED_HIGH - SPEED_LOW)) + SPEED_LOW);
    }

    /**
     * Render the health value of the pirate.
     */
    public void renderHealthPoints() {
        getHealth().setLocation(getCurrentX(), getCurrentY() - HEALTH_OFFSET);
        getHealth().renderHealthPoints();

    }

    /**
     * Set the direction of the pirate randomly.
     */
    public void setRandomDirection() {
        currentDirection = (int) ((Math.random() * (LEFT - UP)) + UP);
        if (currentDirection == LEFT) {
            setCurrentImage(getLeftImg());
        } else {
            setCurrentImage(getRightImg());
        }
    }

    /**
     * Turn the pirate to an opposite direction.
     */
    public void turnDirection() {
        if (currentDirection == UP) {
            currentDirection = DOWN;
        } else if (currentDirection == RIGHT) {
            currentDirection = LEFT;
        } else if (currentDirection == DOWN) {
            currentDirection = UP;
        } else {
            currentDirection = RIGHT;
        }
        if (currentDirection == LEFT) {
            if (isInvincible()) {
                setCurrentImage(getHitLeftImg());
            } else {
                setCurrentImage(getLeftImg());
            }
        } else {
            if (isInvincible()) {
                setCurrentImage(getHitRightImg());
            } else {
                setCurrentImage(getRightImg());
            }
        }
    }

    /**
     * Inflicts damage to the pirate.
     * @param hurt The health value that the pirate will lose.
     */
    public void isAttacked(double hurt) {
        if (!isInvincible) {
            getHealth().minusHealth(hurt);
            isInvincible = true;
            System.out.println("Sailor inflicts " + (int) hurt + " damage points on " + this.getName() + ". "
                    + this.getName() + "'s current health: " + (int) getHealth().getCurrHealth() + "/"
                    + (int) getHealth().getMaxHealth());
        }
    }

    /**
     * CHeck if the pirate is in invincible state.
     * @return True if the pirate is currently invincible, false otherwise.
     */
    public boolean isInvincible() {
        if (invincibleTimer > (INVINCIBLE_TIME * getHertz())) {
            invincibleTimer = 0;
            isInvincible = false;
            if (getCurrentImage() == getHitLeftImg()) {
                setCurrentImage(getLeftImg());
            } else if (getCurrentImage() == getHitRightImg()){
                setCurrentImage(getRightImg());
            }
        }
        if (isInvincible) {
            if (getCurrentImage() == getLeftImg()) {
                setCurrentImage(getHitLeftImg());
            } else if (getCurrentImage() == getRightImg()){
                setCurrentImage(getHitRightImg());
            }
        }
        return isInvincible;
    }

    /**
     * Get a bounding box of the attack range of the pirate.
     * @return Rectangular bounding box of the attack range.
     */
    public Rectangle getAttackBox(){
        Rectangle attackBox = new Rectangle(getCenter().x - attackRange/2,
                getCenter().y - attackRange/2, attackRange, attackRange);
        return attackBox;
    }

    /**
     * Check if the sailor is in the attack range of the pirate,
     * attack the sailor if it is in the attack range.
     * @param sailor Current sailor.
     */
    public void checkCollisionsSailor(Sailor sailor) {
        Rectangle sailorBox =
                sailor.getCurrentImage().getBoundingBoxAt(new Point(sailor.getCenter().x, sailor.getCenter().y));
        if (sailorBox.intersects(getAttackBox())) {
            attack(sailor);
        }
    }

    /**
     * Fire a bullet towards the sailor.
     * @param sailor Current sailor.
     */
    public void attack(Sailor sailor) {
        Bullet bullet = new Bullet(getBulletImg(), sailor.getCurrentX(), sailor.getCurrentY(),
                this.getCurrentX(), this.getCurrentY(), this);
        if (! isCooldown()) {
            bullet.setOwner(getName());
            bullets.add(bullet);
            setCooldown(true);
        }
    }

    /**
     * Set the attack range.
     * @param attackRange Attack range.
     */
    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    /**
     * Get the left image.
     * @return Left image.
     */
    public Image getLeftImg() {
        return leftImg;
    }

    /**
     * Get the right image.
     * @return Right image.
     */
    public Image getRightImg() {
        return rightImg;
    }

    /**
     * Get the invincible left image.
     * @return Invincible left image.
     */
    public Image getHitLeftImg() {
        return hitLeftImg;
    }

    /**
     * Get the invincible right image.
     * @return Invincible right image.
     */
    public Image getHitRightImg() {
        return hitRightImg;
    }

    /**
     * Get the image of the bullet.
     * @return Bullet image.
     */
    public Image getBulletImg() {
        return bulletImg;
    }

    /**
     * Get the speed of the bullet.
     * @return Speed of the bullet.
     */
    public double getProjectileSpeed() { return projectileSpeed; }

    /**
     * Move the pirate to next position.
     */
    public void move() {
        if (currentDirection == UP) {
            setPrev(getCurrentX(), getCurrentY());
            super.move(0, -getSpeed());
        } else if (currentDirection == RIGHT) {
            setPrev(getCurrentX(), getCurrentY());
            super.move(getSpeed(), 0);
        } else if (currentDirection == DOWN) {
            setPrev(getCurrentX(), getCurrentY());
            super.move(0, getSpeed());
        } else {
            setPrev(getCurrentX(), getCurrentY());
            super.move(-getSpeed(), 0);
        }
    }

    /**
     * Get the name of the pirate ("Blackbeard" or "Pirate").
     * @return Pirate name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the pirate ("Blackbeard" or "Pirate").
     * @param name Pirate name.
     */
    public void setName(String name) {
        this.name = name;
    }

}
