package convexhull;

/**
 * @author Chet Mancini
 */
public class Arc {

    private double x;

    private double y;

    private int radius;

    private double angle1;

    private double angle2;

    public Arc(double x, double y, int radius, double angle1, double angle2) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.angle1 = angle1;
        this.angle2 = angle2;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public double getAngle1() {
        return angle1;
    }

    public double getAngle2() {
        return angle2;
    }
}
