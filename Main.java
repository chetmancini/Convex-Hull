
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 * Main.java
 * 
 * Program to run the algorithms.
 *
 * @author Chet Mancini
 * Wheaton College, CS 445, Fall 2008
 * Convex Hull Project
 * Dec 4, 2008
 */
public class Main{
    public static void main(String[] args){
        Draw pane = Draw.getInstance();
        Graph graph = Graph.getInstance();
        Algorithm alg = new GrahamScan();
        int numVertices = 10;
        String val = JOptionPane.showInputDialog("Number of Vertices", "10");
        try{
            numVertices = Integer.parseInt(val);
        } catch(NumberFormatException nfe){
            System.exit(0);
        }
        int size = 512;
        Object[] possibleValues = {"Graham's Scan", "Jarvis's March"};
        Object selectedValue = JOptionPane.showInputDialog(null, "Choose one", "Input", JOptionPane.PLAIN_MESSAGE, null, possibleValues, possibleValues[0]);
        if(selectedValue.equals("Graham's Scan")){
        } else if(selectedValue.equals("Jarvis's March")){
            alg = new JarvisMarch();
        } else{
            System.exit(0);
        }
        pane.initWindow(size, (String) selectedValue);
        graph.setVertices(Generator.generateVertices(numVertices, size, size));
        pane.setPenRadius(.015);
        pane.setPenColor(Color.BLACK);
        graph.drawVertices();
        alg.runAlgorithm();
    }
}
