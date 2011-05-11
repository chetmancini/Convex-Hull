
import java.awt.Color;
import java.util.Vector;

/**
 * GrahamScan.java
 * 
 * Implements Graham's Scan for finding the convex hull.
 *
 * @author Chet Mancini
 * Wheaton College, CS 445, Fall 2008
 * Convex Hull Project
 * Dec 4, 2008
 */
public class GrahamScan extends Algorithm{

    /**
     * A stack of vertices for building the hull.
     */
    private GrahamStack stack = new GrahamStack();

    /**
     * A set of temporary edges for drawing.
     */
    Vector<Edge> tempEdges = new Vector<Edge>();

    /**
     * Constructor.
     */
    public GrahamScan(){
    }

    /**
     * Draw all the temporary edges to the screen.
     */
    private void drawTempEdges(){
        for(int i = 0; i < tempEdges.size(); i++){
            pane.setPenColor(Color.RED);
            graph.drawLine(tempEdges.elementAt(i));
        }
    }

    /**
     * Add a temp edge connection the top item of the stack and the 2nd highest element on the stack.
     */
    private void addTempEdge(){
        Edge toWrite = new Edge(stack.top(), stack.nextToTop());
        tempEdges.add(toWrite);
        pane.setPenColor(Color.RED);
        graph.drawLine(toWrite);
        pause();
    }

    /**
     * Draw the final convex hull.
     */
    private void drawFinal(){
        Vertex[] vertArray = new Vertex[stack.size()];
        for(int i = 0; i < vertArray.length; i++){
            vertArray[i] = stack.pop();
        }
        pane.setPenRadius(.005);
        pane.setPenColor(Color.BLACK);
        for(int i = vertArray.length - 1; i > 0; i--){
            graph.addDispEdge(new Edge(vertArray[i], vertArray[i - 1]));
        }
        graph.addDispEdge(new Edge(vertArray[0], vertArray[vertArray.length - 1]));
    }

    @Override
    /**
     * Run the algorithm
     * set the pen radius and label the points.
     * Push the first three items onto the stack.
     * Loop through all the items, first doing:
     * while the stack length is >= 2) and  the cross product of the current point and top two stack items
     * is >= 0, pop items from the stack.
     * and then pushing the current item onto the stack.
     */
    public void runAlgorithm(){
        pause();
        graph.label();
        pane.setPenRadius(.005);
        stack.push(graph.vertices.firstElement());
        stack.push(graph.vertices.elementAt(1));
        addTempEdge();
        stack.push(graph.vertices.elementAt(2));
        addTempEdge();
        for(int i = 3; i < graph.vertices.size(); i++){
            while(stack.size() >= 2 && Vertex.crossProduct(stack.nextToTop(), stack.top(), graph.vertices.elementAt(i)) >= 0){
                stack.pop();
                tempEdges.removeElementAt(tempEdges.size() - 1);
                graph.drawAll();
                drawTempEdges();
                pause(250);
            }
            stack.push(graph.vertices.elementAt(i));
            addTempEdge();
            pause(400);
        }
        drawFinal();
    }
}
