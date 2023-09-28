package Animals;

import Animals.*;
import Field.*;
import Graph.*;

import java.util.ArrayList;

/**
 * The Fungi model. fungi can age, reproduce, and eat dead stuff.
 * 
 * @author Christopher Kao
 */
public class Fungi {

    // Class Attributes

    // The age at which a link becomes a spore
    private static int SPORE_AGE_THRESHOLD = 20;

    // The amount of links or food connected to a link until it becomes a spore
    // Calculated by the minimum length branch connected to it, branch ends at spore.
    private static int BRANCH_AMT_THRESHOLD = 10;

    // The discount on spore promotion threshold when there are three connections instead of two
    private static double THREE_BRANCH_DISCOUNT = 0.5;

    // How many steps a link can go without having another link connected to it (the end of the branch)
    private static int DEAD_LINK_THRESHOLD = 2;

    // How many steps a spore can go without having another link connected to it
    private static int DEAD_SPORE_THRESHOLD = 10;

    // Object Attributes

    // The age of a specific link or spore
    private int age;

    // The list of links connected to a spore
    private ArrayList<Fungi> linkList;

    // If the link is last in line
    private boolean lastLink;

    // If the fungi is a spore or not
    private boolean isSpore;

    // If the fungi is alive or not
    private boolean isAlive;

    // The location of the fungi
    private Location location;

    // Tracks how many steps being the lastLink
    private int deadTime;

    /**
     * creates a new Fungi
     * 
     * @param spore If true, start with age 20. Else 0.
     */
    public Fungi(boolean spore, int linkCount) {
        lastLink = true;
        isAlive = true;
        if (spore) {
            age = 20;
            deadTime = DEAD_SPORE_THRESHOLD;
        } else {
            age = 0;
            deadTime = DEAD_LINK_THRESHOLD;
        }
        linkList = new ArrayList<Fungi>();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocation(int row, int col) {
        Location location = new Location(row, col);
        setLocation(location);
    }

}
