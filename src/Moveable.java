/**
 * Interface of all things that can move.
 */
public interface Moveable {
    /**
     * Move the object by certain x and y value.
     * @param xMove Change in x.
     * @param yMove Change in y.
     */
    public void move(double xMove, double yMove);

    /**
     * Check if the object is out of bound.
     * @return True if the object is out of bound, false otherwise.
     */
    public boolean isOutOfBound();
}
