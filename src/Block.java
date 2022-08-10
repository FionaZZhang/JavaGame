import bagel.Image;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * This is the class of all blocks,
 * which is responsible for checking the collisions of Sailor and pirates,
 * and implement their reactions.
 */
public class Block extends Stationary{
    private final static Image IMAGE = new Image("res/block.png");
    private final static String NAME = "Block";

    /**
     * The constructor method to create a block.
     * @param x The x position.
     * @param y The y posiiton.
     */
    public Block(int x, int y) {
        super(IMAGE, NAME, x, y);
    }

    /**
     * The update function of Block,
     * it will render the image of a block,
     * and implement corresponding effects to sailor and pirates if a collision occurs.
     * @param sailor The current sailor.
     * @param pirates All pirates in current level.
     */
    public void update(Sailor sailor, ArrayList<Pirate> pirates) {
        checkCollisions(sailor);
        for (Pirate pirate : pirates) {
            checkCollisions(pirate);
        }
        render(getX(), getY());
    }

    /**
     * The method to check whether the sailor is colliding with a block.
     * If it is, move back the sailor.
     * @param sailor The sailor.
     * @return Return true if the sailor collides with the block, false otherwise.
     */
    public boolean checkCollisions(Sailor sailor){
        Rectangle sailorBox = sailor.getCurrentImage().getBoundingBoxAt(sailor.getCenter());
        if (sailorBox.intersects(getBoundingBox())) {
            sailor.moveBack();
            return true;
        }
        return false;
    }

    /**
     * Check if a pirate is colliding with the block.
     * If it is, change the direction of the pirate.
     * @param pirate A pirate.
     * @return Return true if the pirate collides with the block, false otherwise.
     */
    public boolean checkCollisions(Pirate pirate){
        Rectangle pirateBox = pirate.getCurrentImage().getBoundingBoxAt(pirate.getCenter());
        if (pirateBox.intersects(getBoundingBox())) {
            pirate.turnDirection();
            pirate.move();
            return true;
        }
        return false;
    }
}
