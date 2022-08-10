import bagel.*;
import bagel.util.*;

/**
 * The class of all items, including Elixir, Potion, Sword, and Treasure.
 */
public abstract class Item implements Renderable {
    private Image currImage;
    private Image iconImage;
    private String name;
    private int x;
    private int y;

    /**
     * The constructor of an item.
     * @param currImage Item image.
     * @param iconImage Item image that will be rendered after picking up.
     * @param name Name of the item.
     * @param x x location.
     * @param y y location.
     */
    public Item(Image currImage, Image iconImage, String name, int x, int y) {
        this.currImage = currImage;
        this.iconImage = iconImage;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * Update method of the item.
     * @param sailor Current sailor.
     */
    public abstract void update(Sailor sailor);

    /**
     * Set the name of the item.
     * @param name Item name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name of the item.
     * @return Item name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get item image.
     * @return Item image.
     */
    public Image getCurrImage() {
        return this.currImage;
    }

    /**
     * Get current x position of the item.
     * @return x position of item.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get current y position of the item.
     * @return y position of item.
     */
    public int getY() {return this.y; }

    /**
     * Render the item at given x and y.
     * @param x x location.
     * @param y y location.
     */
    public void render(double x, double y) {
        getCurrImage().drawFromTopLeft(x,y);
    }

    /**
     * Get icon image of the item.
     * @return Item icon image.
     */
    public Image getIcon() {
        return iconImage;
    }

    /**
     * Check whether the sailor collides with the item,
     * sailor picks the item up it it collides with the item.
     * @param sailor Current sailor.
     * @return Return true if the sailor picks up the item, false otherwise.
     */
    public boolean checkCollisions(Sailor sailor) {
        Rectangle sailorBox = sailor.getBoundingBox();
        if (sailorBox.intersects(getBoundingBox())) {
            sailor.pickUp(this);
            return true;
        }
        return false;
    }

    /**
     * Get a bounding box of the item image.
     * @return Rectangular bounding box of the item.
     */
    public Rectangle getBoundingBox() {
        return currImage.getBoundingBoxAt(getCenter());
    }

    /**
     * Get the center of the item.
     * @return Center point of the item.
     */
    public Point getCenter() {
        return new Point(x + currImage.getWidth()/2, y + currImage.getHeight()/2);
    }
}
