package convexhull;

/**
 * @author chet
 */
public class Arc {

    private double x;

    private double y;

    /**
     * The radius.
     */
    private int r;

    /**
     * The first angle.
     */
    private double angle1;

    /**
     * The second angle.
     */
    private double angle2;

    /**
     * Constructor
     */
    public Arc(double x, double y, int r, double angle1, double angle2) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.angle1 = angle1;
        this.angle2 = angle2;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Getter method for radius.
     */
    public int getR() {
        return r;
    }

    public double getAngle1() {
        return angle1;
    }

    public double getAngle2() {
        return angle2;
    }
}
