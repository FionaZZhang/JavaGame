import bagel.*;

/**
 * This is the class of Blackbeard.
 */
public class Blackbeard extends Pirate {
    private final static int DAMAGE = 20;
    private final static int ATTACK_RANGE = 200;
    private final static int MAXHEALTH_INI = 90;
    private final static int HEALTH_SIZE = 15;
    private final static int HEALTH_OFFSET = 6;
    private final static double PROJECTILE_SPEED = 0.8;
    private final static double SPEED_LOW = 0.2;
    private final static double SPEED_HIGH = 0.7;
    private final static double COOLDOWN = 1.5;
    private final static double INVINCIBLE_TIME = 1.5;
    private final static String NAME = "Blackbeard";
    private final static Image LEFTIMG = new Image("res/blackbeard/blackbeardLeft.png");
    private final static Image RIGHTIMG = new Image("res/blackbeard/blackbeardRight.png");
    private final static Image HITLEFT = new Image("res/blackbeard/blackbeardHitLeft.png");
    private final static Image HITRIGHT = new Image("res/blackbeard/blackbeardHitRight.png");
    private final static Image BULLET = new Image("res/blackbeard/blackbeardProjectile.png");

    /**
     * The constructor method of a Blackbeard.
     * @param x The x position.
     * @param y The y position.
     */
    public Blackbeard(int x, int y) {
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
}

