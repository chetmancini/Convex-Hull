
import java.awt.geom.Point2D;

/**
 * Vertex.java
 * <p/>
 * Class to represent a vertex on the graph.
 *
 * @author Chet Mancini Wheaton College, CS 445, Fall 2008 Convex Hull Project Dec 4, 2008
 */
public class Vertex extends Point2D implements Comparable<Vertex> {

    /**
     * The x coordinate of the vertex.
     */
    private double x;

    /**
     * The y coordinate of the vertex.
     */
    private double y;

    /**
     * The name of the vertex.
     */
    private String name;

    /**
     * Constructor
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
        //ellipse = new Ellipse2D.Double(x-2, y-2, 10, 10);

    }

    /**
     * Constructor
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param name the vertex name.
     */
    public Vertex(double x, double y, String name) {
        this.x = x;
        this.y = y;
        //ellipse = new Ellipse2D.Double(x-2, y-2, 10, 10);
        this.name = name;
    }

    /**
     * Return a string representation of the vertex.
     *
     * @return a string representation of the vertex.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Get the x coordinate.
     *
     * @return the x coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y coordinate.
     *
     * @return the y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Set the location of the vertex.
     *
     * @param x the new x coordinate.
     * @param y the new y coordinate.
     */
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the vertex's name.
     *
     * @param name the vertex's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the vertex name.
     *
     * @return the Vertex name.
     */
    public String getName() {
        return name;
    }

    /**
     * Implements comparable
     *
     * @return 1, -1, or 0 if the y coordinate is greater than, less, or equal to this vertex.
     */
    public int compareTo(Vertex o) {
        if (this.getY() > o.getY()) {
            return -1;
        }
        else if (this.getY() < o.getY()) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Calculate the polar angle of a vertex compared with a given vertex.
     *
     * @param other the other vertex to calculate polar angle to.
     * @return the polar angle (arctan).
     */
    public double polarAngleRad(Vertex other) {
        int size = Draw.getInstance().getSize();
        double thisx = x;
        double thisy = size - y;
        double ox = other.getX();
        double oy = size - other.getY();
        return Math.atan2(oy - thisy, ox - thisx);
    }

    /**
     * Take the cross product of three vertices
     *
     * @param p1 the first vertex.
     * @param p2 the second vertex.
     * @param p3 the third vertex.
     */
    public static double crossProduct(Vertex p1, Vertex p2, Vertex p3) {
        int size = Draw.getInstance().getSize();
        double p1x = p1.getX();
        double p1y = size - p1.getY();
        double p2x = p2.getX();
        double p2y = size - p2.getY();
        double p3x = p3.getX();
        double p3y = size - p3.getY();
        return ((p3x - p1x) * (p2y - p1y)) - ((p2x - p1x) * (p3y - p1y));
    }
}
