
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

/**
 * Generator.java
 * 
 * Generate a random set of vertices
 *
 * @author Chet Mancini
 * Wheaton College, CS 445, Fall 2008
 * Convex Hull Project
 * Dec 4, 2008
 */
public class Generator {

    /**
     * Generate a random set of vertices.
     * @param num the number of vertices.
     * @param xMax the max x value for vertices.
     * @param yMax the max y value for vertices.
     * @return the generated Vector of vertices.
     */
    public static Vector<Vertex> generateVertices(int num, int xMax, int yMax){
        Vector<Vertex> toReturn = new Vector<Vertex>();
        Random rand = new Random();
        for(int i=0;i<num;i++){
            Vertex toAdd = new Vertex(20+rand.nextInt(xMax-40), 20+rand.nextInt(yMax-40));
            toReturn.add(toAdd);
        }
        Collections.sort(toReturn);
        toReturn.elementAt(0).setName("P" + 0);
        for(int i=1;i<toReturn.size();i++){
            toReturn.elementAt(i).setName("");
        }
        return toReturn;
    }
}
