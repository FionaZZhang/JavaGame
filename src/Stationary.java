import bagel.*;
import bagel.util.*;

/**
 * Class of stationary objects, including blocks and bombs.
 */
public abstract class Stationary implements Renderable {
    private Image currImage;
    private String name;
    private int x;
    private int y;

    /**
     * Constructor of the staionary object.
     * @param currImage Current image.
     * @param name Name of the object.
     * @param x x location.
     * @param y y location.
     */
    public Stationary(Image currImage, String name, int x, int y) {
        this.currImage = currImage;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * Set the current image of the object.
     * @param currImage Current image.
     */
    public void setCurrImage(Image currImage) {
        this.currImage = currImage;
    }

    /**
     * Set the name of the object.
     * @param name Object name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the current image of the object.
     * @return Current image.
     */
    public Image getCurrImage() {
        return this.currImage;
    }

    /**
     * Get the name of the object.
     * @return Object name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get current x position.
     * @return x position.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get current y position.
     * @return y position.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Render the object.
     * @param x x position.
     * @param y y position.
     */
    public void render(double x, double y) {
        currImage.drawFromTopLeft(x,y);
    }

    /**
     * Get the center point of the object.
     * @return Center point of the object.
     */
    public Point getCenter() {
        return new Point(getX() + getCurrImage().getWidth() / 2, getY() + getCurrImage().getHeight() / 2);
    }

    /**
     * Get the bounding box of the object.
     * @return Rectangular bounding box of the object.
     */
    public Rectangle getBoundingBox(){
        return getCurrImage().getBoundingBoxAt(getCenter());
    }

}
