
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Algorithm.java
 * 
 * Abstract superclass for convex hull algorithms
 *
 * @author Chet Mancini
 * Wheaton College, CS 445, Fall 2008
 * Convex Hull Project
 * Dec 4, 2008
 */
public abstract class Algorithm {
    /**
     * Singleton instance of the graph.
     */
    protected Graph graph = Graph.getInstance();
    /**
     * Singleton instance of the drawing pane.
     */
    protected Draw pane = Draw.getInstance();
    
    /**
     * Pause for half a second.
     */
    protected void pause(){
        try{
            Thread.sleep(500);
        } catch(InterruptedException ex){
            Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /**
     * Pause for a given number of milliseconds.
     * @param millis the number of milliseconds to pause for.
     */
    protected void pause(long millis){
        try{
            Thread.sleep(millis);
        } catch(InterruptedException ex){
            Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Run the algorithm.
     */
    public abstract void runAlgorithm();
}
