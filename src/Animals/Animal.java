package Animals;

import Field.Location;

public class Animal {
    // ----------------------------------------------------
	// Characteristics shared by all animals (static fields).
	// ----------------------------------------------------
	protected static int BREEDING_AGE;
	// The age to which an animal can live.
	protected static int MAX_AGE;
	// The likelihood of an animal breeding.
	protected static double BREEDING_PROBABILITY;
	// The maximum number of births.
	protected static int MAX_LITTER_SIZE;

    // -----------------------------------------------------
    // Individual characteristics (attributes).
    // -----------------------------------------------------
    // The animal's age.
    protected int age;
    
    // Whether the animal is alive or not.
    protected boolean alive;
    
    // The animal's position
    protected Location location;

    /**
     * Create a new animal. An animal is created
     * at age zero
     * 
     */
    public Animal()
    {
        age = 0;
        alive = true;
    }

    /**
     * Checks if the animal starts with a
     * random age.
     * 
     * @param startWithRandomAge If true, the age is random.
     */
    public void startWithRandomAge(boolean startWithRandomAge) {
        if (startWithRandomAge) {
            age = (int)(Math.random()*MAX_AGE);
        }
    }

    /**
     * Increase the age.
     * This could result in the animal's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            alive = false;
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && Math.random() <= BREEDING_PROBABILITY) {
            births = (int)(Math.random()*MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Set the animal's location.
     * @param row The vertical coordinate of the location.
     * @param col The horizontal coordinate of the location.
     */
    public void setLocation(int row, int col)
    {
        this.location = new Location(row, col);
    }

    /**
     * Set the animal's location.
     * @param location The animal's location.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }
}   
