package convexhull;

import java.awt.Color;
import java.util.Vector;

/**
 * Graph.java
 * Singleton class to represent and manage a graph.
 * Contains vectors of vertices and edges as well as convenience methods for operating on them and interacting with the drawing
 * pane.
 *
 * @author Chet Mancini Wheaton College, CS 445, Fall 2008 Convex Hull Project Dec 4, 2008
 */
public class Graph {

    private static Graph graph;

    private Draw pane = Draw.getInstance();

    public Vector<Vertex> vertices;

    public Vector<Edge> edges;

    public Vector<Arc> arcs;

    /**
     * Vector of mini edges that go with the arcs.
     */
    public Vector<Edge> minis;

    /**
     * Constructor.
     */
    private Graph() {
        edges = new Vector<Edge>();
        vertices = new Vector<Vertex>();
        arcs = new Vector<Arc>();
        minis = new Vector<Edge>();
    }

    /**
     * Get the instance of graph.
     */
    public static Graph getInstance() {
        if (graph == null) {
            graph = new Graph();
        }
        return graph;
    }

    /**
     * Add a vertex to the graph, but don't display it.
     *
     * @param toAdd the vertex to add.
     */
    public void addVertex(Vertex toAdd) {
        vertices.add(toAdd);
        pane.pointNoShow(toAdd.getX(), toAdd.getY(), toAdd.getName());
    }

    /**
     * Add a vertex to the graph and display it.
     *
     * @param toAdd the vertex to add.
     */
    public void addDispVertex(Vertex toAdd) {
        vertices.add(toAdd);
        pane.point(toAdd.getX(), toAdd.getY(), toAdd.getName());
    }

    /**
     * Add an edge to the graph and display it.
     *
     * @param toAdd the edge to add.
     */
    public void addEdge(Edge toAdd) {
        edges.add(toAdd);
        pane.setPenRadius(.005);
        pane.line(toAdd.getFirst().getX(), toAdd.getFirst().getY(), toAdd.getSecond().getX(), toAdd.getSecond().getY());
    }

    /**
     * Add an arc.
     *
     * @param toAdd the arc to add.
     */
    public void addArc(Arc toAdd) {
        arcs.add(toAdd);
        if (toAdd.getAngle1() == 0) {
            Edge mini = new Edge(new Vertex(toAdd.getX() + 25, toAdd.getY()), new Vertex(toAdd.getX(), toAdd.getY()));
            minis.add(mini);
            pane.setPenRadius(.002);
            pane.line(mini.getFirst().getX(), mini.getFirst().getY(), mini.getSecond().getX(), mini.getSecond().getY());
        }
        else {
            Edge mini = new Edge(new Vertex(toAdd.getX() - 25, toAdd.getY()), new Vertex(toAdd.getX(), toAdd.getY()));
            minis.add(mini);
            pane.setPenRadius(.002);
            pane.line(mini.getFirst().getX(), mini.getFirst().getY(), mini.getSecond().getX(), mini.getSecond().getY());
        }
        pane.setPenRadius(.002);
        pane.arc(toAdd.getX(), toAdd.getY(), toAdd.getRadius(), toAdd.getAngle1(), toAdd.getAngle2());
    }

    /**
     * Draw a line on the graph, but don't add the edge.
     *
     * @param first one end vertex.
     * @param second other end vertex.
     */
    public void drawLine(Vertex first, Vertex second) {
        pane.setPenRadius(.005);
        pane.line(first.getX(), first.getY(), second.getX(), second.getY());
    }

    /**
     * Draw a line on the graph, but don't add the edge to the data structure.
     *
     * @param toWrite the edge to write out.
     */
    public void drawLine(Edge toWrite) {
        pane.setPenRadius(.005);
        pane.line(toWrite.getFirst().getX(), toWrite.getFirst().getY(), toWrite.getSecond().getX(), toWrite.getSecond().getY());
    }

    /**
     * Sort the vertices by polar coordinates compared to the origin point (lowest y value). This is used by both Graham's Scan and
     * Jarvis's March.
     */
    private void sortByPolar() {
        Vertex origin = lowest();

        for (int i = 1; i < vertices.size(); i++) {
            int minSoFar = i;
            double minAngle = origin.polarAngleRad(vertices.elementAt(i));
            for (int j = i + 1; j < vertices.size(); j++) {
                Vertex other = vertices.elementAt(j);
                double angle = origin.polarAngleRad(other);
                if (angle < minAngle) {
                    minSoFar = j;
                    minAngle = angle;
                }
            }
            Vertex toInsert = vertices.remove(minSoFar);
            vertices.insertElementAt(toInsert, i);
        }
    }

    /**
     * Label the vertices on the graph P1 through Pn, where is is the number of vertices - 1.
     */
    public void label() {
        pane.clearNoShow();
        sortByPolar();
        for (int i = 1; i < vertices.size(); i++) {
            vertices.elementAt(i).setName("P" + i);
        }
        drawVertices();
        pane.show();
    }

    /**
     * Get the vertex with the lowest y coordinate.
     *
     * @return the vertex with the lowest y coordinate.
     */
    public Vertex lowest() {
        return vertices.firstElement();
    }

    /**
     * Set the vertices of the graph to a given set.
     *
     * @param vertices the vertices to set as the current set.
     */
    public void setVertices(Vector<Vertex> vertices) {
        this.vertices = vertices;
    }

    /**
     * Display all the vertices currently in the data structure.
     */
    public void drawVertices() {
        pane.setPenColor(Color.BLACK);
        pane.setPenRadius(0.015);
        for (int i = 0; i < vertices.size(); i++) {
            Vertex toAdd = vertices.elementAt(i);
            pane.pointNoShow(toAdd.getX(), toAdd.getY(), toAdd.getName());
        }
    }

    /**
     * Display all the edges currently in the data structure.
     */
    public void drawEdges() {
        pane.setPenRadius(.005);
        for (int i = 0; i < edges.size(); i++) {
            Edge toAdd = edges.elementAt(i);
            pane.lineNoShow(toAdd.getFirst().getX(), toAdd.getFirst().getY(), toAdd.getSecond().getX(), toAdd.getSecond().getY());
        }
    }

    /**
     * Display all the arcs currently in the data structure.
     */
    public void drawArcs() {
        pane.setPenRadius(.002);
        for (int i = 0; i < arcs.size(); i++) {
            Arc toAdd = arcs.elementAt(i);
            pane.arcNoShow(toAdd.getX(), toAdd.getY(), toAdd.getRadius(), toAdd.getAngle1(), toAdd.getAngle2());
        }
    }

    /**
     * Display all the mini edges that go with the arcs in the data structure.
     */
    public void drawMinis() {
        pane.setPenRadius(.002);
        for (int i = 0; i < minis.size(); i++) {
            Edge toAdd = minis.elementAt(i);
            pane.lineNoShow(toAdd.getFirst().getX(), toAdd.getFirst().getY(), toAdd.getSecond().getX(), toAdd.getSecond().getY());
        }
    }

    /**
     * Display both edges and vertices, clearing the whole pane before drawing.
     */
    public void drawAll() {
        pane.clearNoShow();
        drawVertices();
        drawEdges();
        drawArcs();
        drawMinis();
        pane.show();
    }
}
