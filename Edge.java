/**
 * Edge.java
 * <p/>
 * Models an edge between two vertices.
 *
 * @author Chet Mancini Wheaton College, CS 445, Fall 2008 Convex Hull Project Dec 4, 2008
 */
public class Edge {

    /**
     * One end of the vertex.
     */
    private Vertex vertex1;

    /**
     * the other vertex end.
     */
    private Vertex vertex2;

    /**
     * Constructor
     *
     * @param v1 the first vertex.
     * @param v2 the second vertex.
     */
    public Edge(Vertex v1, Vertex v2) {
        vertex1 = v1;
        vertex2 = v2;
    }

    /**
     * Get the first vertex.
     *
     * @return the first vertex.
     */
    public Vertex getFirst() {
        return vertex1;
    }

    /**
     * Get the second vertex.
     *
     * @return the second vertex.
     */
    public Vertex getSecond() {
        return vertex2;
    }
}
