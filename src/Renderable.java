import bagel.util.Rectangle;

/**
 * Interface of all things that can be rendered.
 */
public interface Renderable {
    /**
     * Render the thing at given position.
     * @param x x position.
     * @param y y position.
     */
    public void render(double x, double y);

    /**
     * Get a bounding box of the image.
     * @return Rectangulr bounding box.
     */
    public Rectangle getBoundingBox();
}
