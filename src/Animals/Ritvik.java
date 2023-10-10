package Animals;

import Animals.*;
import Field.*;
import Graph.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * A simple model of a fox. Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kolling.  Modified by David Dobervich 2007-2022
 */
public class Ritvik extends Animal {
	// ----------------------------------------------------
	// Characteristics shared by all foxes (static fields).
	// ----------------------------------------------------
	
	// The food value of a single rabbit. In effect, this is the
	// number of steps a fox can go before it has to eat again.
	private static int BEAR_FOOD_VALUE = 500;
	// A shared random number generator to control breeding.

	// -----------------------------------------------------
	// Individual characteristics (attributes).
	// -----------------------------------------------------
	// The fox's food level, which is increased by eating rabbits.
	private int foodLevel;

	/**
	 * Create a fox. A fox can be created as a new born (age zero and not
	 * hungry) or with random age.
	 * 
	 * @param startWithRandomAge
	 *            If true, the fox will have random age and hunger level.
	 */
	public Ritvik(boolean startWithRandomAge) {
		age = 0;
		alive = true;
		BREEDING_AGE = 410;
		// The age to which a fox can live.
		MAX_AGE = 500;
		// The likelihood of a fox breeding.
		BREEDING_PROBABILITY = 0.1;
		// The maximum number of births.
		MAX_LITTER_SIZE = 1;
		if (startWithRandomAge) {
			age = (int)(Math.random()*MAX_AGE);
			foodLevel = (int)(Math.random()*BEAR_FOOD_VALUE);
		} else {
			// leave age at 0
			foodLevel = BEAR_FOOD_VALUE;
		}
	}

	/**
	 * This is what the fox does most of the time: it hunts for rabbits. In the
	 * process, it might breed, die of hunger, or die of old age.
	 * 
	 * @param currentField
	 *            The field currently occupied.
	 * @param updatedField
	 *            The field to transfer to.
	 * @param babyFoxStorage
	 *            A list to add newly born foxes to.
	 */
	public void act(Field currentField, Field updatedField, ArrayList<Animal> babyBearStorage) {
		incrementAge();
		incrementHunger();
		if (alive) {
			// New foxes are born into adjacent locations.
			int births = breed();
			for (int b = 0; b < births; b++) {
				Ritvik newRitvik = new Ritvik(false);
				newRitvik.setFoodLevel(this.foodLevel);
				babyBearStorage.add(newRitvik);
				Location loc = updatedField.randomAdjacentLocation(location);
				newRitvik.setLocation(loc);
				updatedField.put(newRitvik, loc);
			}
			// Move towards the source of food if found.
			Location newLocation = findFood(currentField, location);
			if (newLocation == null) { // no food found - move randomly
				newLocation = updatedField.freeAdjacentLocation(location);
			}
			if (newLocation != null) {
				setLocation(newLocation);
				updatedField.put(this, newLocation);
			} else {
				// can neither move nor stay - overcrowding - all locations
				// taken
				alive = false;
			}
		}
	}

	/**
	 * Make this fox more hungry. This could result in the fox's death.
	 */
	private void incrementHunger() {
		foodLevel--;
		if (foodLevel <= 0) {
			alive = false;
		}
	}

	/**
	 * Tell the fox to look for rabbits adjacent to its current location. Only
	 * the first live rabbit is eaten.
	 * 
	 * @param field
	 *            The field in which it must look.
	 * @param location
	 *            Where in the field it is located.
	 * @return Where food was found, or null if it wasn't.
	 */
	private Location findFood(Field field, Location location) {
		List<Location> adjacentLocations = field.adjacentLocations(location);

		for (Location where : adjacentLocations) {
			Object animal = field.getObjectAt(where);
			if (animal instanceof Bear) {
				Bear bear = (Bear) animal;
				if (bear.isAlive()) {
					bear.setEaten();
					foodLevel = BEAR_FOOD_VALUE;
					return where;
				}
			}
		}

		return null;
	}


	public void setFoodLevel(int fl) {
		this.foodLevel = fl;
	}

	/**
     * Tell the Bear that it's dead now :(
     */
    public void setEaten()
    {
        alive = false;
    }
}
