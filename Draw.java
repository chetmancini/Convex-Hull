
/**
 * Draw.java 
 * Compilation:  javac Draw.java
 *
 * Singleton
 *
 * A class to handle drawing.  Double buffered to reduce flicker.
 * Allows saving the displayed graphic as an image.
 *
 *
 * Original Authors: Copyright © 2007, Robert Sedgewick and Kevin Wayne.
 * Edited by Chet Mancini
 * Originally retrieved from:
 * http://www.cs.princeton.edu/introcs/35purple/Draw.java.html
 *
 * Wheaton College, CS 445, Fall 2008
 * Convex Hull Project
 * Dec 4, 2008
 */

import javax.imageio.ImageIO;

public class Draw implements ActionListener {

    /**
     * Draw is a singleton.  This is the instance.
     */
    private static Draw instance = null;

    /**
     * The default pen color for drawing.
     */
    public final Color DEFAULT_PEN_COLOR = Color.BLACK;

    /**
     * The default background color.
     */
    public final Color DEFAULT_CLEAR_COLOR = Color.WHITE;

    /**
     * The current pen color.
     */
    private Color penColor;

    /**
     * The default dimensions for the window.
     */
    private static final int DEFAULT_SIZE = 512;

    /**
     * The default width.
     */
    private int width = DEFAULT_SIZE;

    /**
     * The default height.
     */
    private int height = DEFAULT_SIZE;

    /**
     * The default pen radius.
     */
    private static final double DEFAULT_PEN_RADIUS = 0.002;

    /**
     * The current pen radius.
     */
    private double penRadius;

    /**
     * Draw immediately or wait for show?
     */
    private boolean defer = false;

    /**
     * Coordinate min-max values
     */
    private double xmin, ymin, xmax, ymax;

    /**
     * The default font for text.
     */
    private static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 14);

    /**
     * The current fond.
     */
    private Font font;

    /**
     * Double buffered images.
     */
    private final BufferedImage offscreenImage, onscreenImage;

    /**
     * Double buffered graphics.
     */
    private final Graphics2D offscreen, onscreen;    // the frame for drawing to the screen

    /**
     * The frame for drawing on screen.
     */
    private JFrame frame = new JFrame();

    /**
     * The label to draw in.
     */
    private JLabel draw;

    /**
     * Constructor.
     */
    private Draw() {
        this(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    /**
     * Constructor
     */
    private Draw(int width, int height) {
        this.width = width;
        this.height = height;
        if (width <= 0 || height <= 0) {
            throw new RuntimeException("Illegal dimension");
        }
        offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        offscreen = offscreenImage.createGraphics();
        onscreen = onscreenImage.createGraphics();
        offscreen.setColor(DEFAULT_CLEAR_COLOR);
        offscreen.fillRect(0, 0, width, height);
        setPenColor(DEFAULT_PEN_COLOR);
        setPenRadius(DEFAULT_PEN_RADIUS);
        setFont(DEFAULT_FONT);
        // add antialiasing
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        offscreen.addRenderingHints(hints);
        // the drawing panel
        ImageIcon icon = new ImageIcon(onscreenImage);
        draw = new JLabel(icon);
        frame.setContentPane(draw);
        // label cannot get keyboard focus
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
        // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window

        frame.setJMenuBar(createMenuBar());
        frame.pack();
        clear();
    }

    /**
     * Inititalize the window.
     *
     * @param algorithm the algorithm running.
     */
    public void initWindow(int side, String algorithm) {
        this.width = side;
        this.height = side;
        if (width <= 0 || height <= 0) {
            throw new RuntimeException("Illegal dimension");
        }
        offscreen.fillRect(0, 0, width, height);
        frame.setTitle(algorithm);
        frame.pack();
        frame.setVisible(true);
        clear();
    }

    /**
     * Create the menu bar
     *
     * @return the window's menu bar
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(" Save...   ");
        JMenuItem menuItem2 = new JMenuItem(" Exit      ");
        menuItem1.addActionListener(this);
        menuItem2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.add(menuItem1);
        menu.add(menuItem2);
        return menuBar;
    }

    /**
     * Get the size of the square
     *
     * @return the size of the sqare.
     */
    public int getSize() {
        return this.height;
    }

    /**
     * Get the jlabel
     *
     * @return the label.
     */
    public JLabel getJLabel() {
        return draw;
    }

    /**
     * Clear the screen for additional writing but don't show it yet.
     */
    public void clearNoShow() {
        defer = true;
        offscreen.setColor(DEFAULT_CLEAR_COLOR);
        offscreen.fillRect(0, 0, width, height);
        offscreen.setColor(penColor);
    }

    /**
     * Clear the screen.
     */
    public void clear() {
        offscreen.setColor(DEFAULT_CLEAR_COLOR);
        offscreen.fillRect(0, 0, width, height);
        offscreen.setColor(penColor);
        show();
    }

    /**
     * Set the pen radius
     *
     * @param r the new pen radius.
     */
    public void setPenRadius(double r) {
        penRadius = r * DEFAULT_SIZE;
        // BasicStroke stroke = new BasicStroke((float) penRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        BasicStroke stroke = new BasicStroke((float) penRadius);
        offscreen.setStroke(stroke);
    }

    /**
     * Set the current pen color
     *
     * @param color the color to set.
     */
    public void setPenColor(Color color) {
        penColor = color;
        offscreen.setColor(penColor);
    }

    /**
     * Set the current font.
     *
     * @param f the font to set.
     */
    public void setFont(Font f) {
        font = f;
    }

    /**
     * draw a line from (x0, y0) to (x1, y1)
     *
     * @param x0 first x
     * @param y0 first y
     * @param x1 second x
     * @param y1 second y
     */
    public void line(double x0, double y0, double x1, double y1) {
        defer = false;
        offscreen.draw(new Line2D.Double(x0, y0, x1, y1));
        show();
    }

    /**
     * draw a line from (x0, y0) to (x1, y1), but don't display it.
     *
     * @param x0 first x
     * @param y0 first y
     * @param x1 second x
     * @param y1 second y
     */
    public void lineNoShow(double x0, double y0, double x1, double y1) {
        defer = true;
        offscreen.draw(new Line2D.Double(x0, y0, x1, y1));
    }

    /**
     * draw one pixel at (x, y).
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    private void pixel(double x, double y) {
        offscreen.fillRect((int) Math.round(x), (int) Math.round(y), 1, 1);
    }

    /**
     * draw a point at (x, y).
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public void point(double x, double y) {
        defer = false;
        double r = penRadius;
        if (r <= 1) {
            pixel(x, y);
        }
        else {
            offscreen.fill(new Ellipse2D.Double(x - r / 2, y - r / 2, r, r));
        }
        show();
    }

    /**
     * draw point at (x, y) with the name as text.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param name the string name of the point.
     */
    public void point(double x, double y, String name) {
        defer = false;
        double r = penRadius;
        if (r <= 1) {
            pixel(x, y);
        }
        else {
            offscreen.fill(new Ellipse2D.Double(x - r / 2, y - r / 2, r, r));
        }
        show();
        text(x + 12, y + 12, name);
    }

    /**
     * draw point at (x, y), but don't show it
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param name the string name of the point.
     */
    public void pointNoShow(double x, double y, String name) {
        defer = true;
        double r = penRadius;
        if (r <= 1) {
            pixel(x, y);
        }
        else {
            offscreen.fill(new Ellipse2D.Double(x - r / 2, y - r / 2, r, r));
        }
        text(x + 12, y + 12, name);
    }

    /**
     * draw arc of radius r, centered on (x, y), from angle1 to angle2 (in degrees)
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param r the radius
     * @param angle1 the first angle
     * @param angle2 the second angle.
     */
    public void arc(double x, double y, double r, double angle1, double angle2) {
        defer = false;
        while (angle2 < angle1) {
            angle2 += 360;
        }
        double ws = 2 * r;
        double hs = 2 * r;
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        }
        else {
            offscreen.draw(new Arc2D.Double(x - ws / 2, y - hs / 2, ws, hs, angle1, angle2 - angle1, Arc2D.OPEN));
        }
        show();
    }

    /**
     * draw arc of radius r, centered on (x, y), from angle1 to angle2 (in degrees), but don't show it
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param r the radius
     * @param angle1 the first angle
     * @param angle2 the second angle.
     */
    public void arcNoShow(double x, double y, double r, double angle1, double angle2) {
        defer = true;
        while (angle2 < angle1) {
            angle2 += 360;
        }
        double ws = 2 * r;
        double hs = 2 * r;
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        }
        else {
            offscreen.draw(new Arc2D.Double(x - ws / 2, y - hs / 2, ws, hs, angle1, angle2 - angle1, Arc2D.OPEN));
        }
    }

    /**
     * write the given text string in the current font, center on (x, y)
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param s the string to write.
     */
    public void text(double x, double y, String s) {
        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        int ws = metrics.stringWidth(s);
        int hs = metrics.getDescent();
        offscreen.drawString(s, (float) (x - ws / 2.0), (float) (y + hs));
        show();
    }

    /**
     * Display the graphics on screen and pause.
     *
     * @param t the millisecond to pause for.
     */
    public void show(int t) {
        defer = true;
        onscreen.drawImage(offscreenImage, 0, 0, null);
        frame.repaint();
        try {
            Thread.sleep(t);
        }
        catch (InterruptedException e) {
            System.out.println("Error sleeping");
        }
    }

    /**
     * View on screen.
     */
    public void show() {
        if (!defer) {
            onscreen.drawImage(offscreenImage, 0, 0, null);
        }
        if (!defer) {
            frame.repaint();
        }
    }

    /**
     * Save to file
     *
     * @param filename the filenmae to save to.  Suffix must be png or jpg
     */
    public void save(String filename) {
        File file = new File(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        // png files
        if (suffix.toLowerCase().equals("png")) {
            try {
                ImageIO.write(offscreenImage, suffix, file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } // need to change from ARGB to RGB for jpeg
        // reference: http://archives.java.sun.com/cgi-bin/wa?A2=ind0404&L=java2d-interest&D=0&P=2727
        else if (suffix.toLowerCase().equals("jpg")) {
            WritableRaster raster = offscreenImage.getRaster();
            WritableRaster newRaster;
            newRaster = raster.createWritableChild(0, 0, width, height, 0, 0, new int[]{0, 1, 2});
            DirectColorModel cm = (DirectColorModel) offscreenImage.getColorModel();
            DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
                    cm.getRedMask(),
                    cm.getGreenMask(),
                    cm.getBlueMask());
            BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false, null);
            try {
                ImageIO.write(rgbBuffer, suffix, file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            JOptionPane.showMessageDialog(frame, "Invalid image file type: " + suffix + ".  Must end in .jpg or .png.",
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The action to run when the user presses Save.
     */
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(frame, "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    /**
     * Since this is a singleton, get the current instance
     *
     * @return the instance of Draw
     */
    public static Draw getInstance() {
        if (instance == null) {
            instance = new Draw();
        }
        return instance;
    }
}
