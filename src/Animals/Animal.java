package Animals;

import Field.Location;

public class Animal {

    protected static int BREEDING_AGE;
	// The age to which a fox can live.
	protected static int MAX_AGE;
	// The likelihood of a fox breeding.
	protected static double BREEDING_PROBABILITY;
	// The maximum number of births.
	protected static int MAX_LITTER_SIZE;
    
    protected int age;
    
    protected boolean alive;

    protected Location location;

    public Animal () {
        age = 0;
		alive = true;
	}

    public void Animal (boolean startWithRandomAge) {
        if (startWithRandomAge) {
			age = (int)(Math.random()*MAX_AGE);
		}
    }

    /**
	 * Increase the age. This could result in the fox's death.
	 */
	protected void incrementAge() {
		age++;
		if (age > MAX_AGE) {
			alive = false;
		}
	}

    /**
	 * Generate a number representing the number of births, if it can breed.
	 * 
	 * @return The number of births (may be zero).
	 */
	public int breed() {
		int numBirths = 0;
		if (canBreed() && Math.random() <= BREEDING_PROBABILITY) {
			numBirths = (int)(Math.random()*MAX_LITTER_SIZE) + 1;
		}
		return numBirths;
	}

    /**
	 * A animal can breed if it has reached the breeding age.
	 */
	public boolean canBreed() {
		return age >= BREEDING_AGE;
	}

    public boolean isAlive() {
		return alive;
	}

    /**
	 * Set the animal's location.
	 * 
	 * @param row
	 *            The vertical coordinate of the location.
	 * @param col
	 *            The horizontal coordinate of the location.
	 */
	public void setLocation(int row, int col) {
		this.location = new Location(row, col);
	}

    /**
	 * Set the animal's location.
	 * 
	 * @param location
	 *            The animal's location.
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
}
