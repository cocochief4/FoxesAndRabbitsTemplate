package Animals;

import Animals.*;
import Field.*;
import Graph.*;

import java.io.Serializable;
import java.util.List;

/**
 * A simple model of a fox. Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kolling.  Modified by David Dobervich 2007-2022
 */
public class Bear extends Animal{
	// ----------------------------------------------------
	// Characteristics shared by all foxes (static fields).
	// ----------------------------------------------------
	
	// The food value of a single rabbit. In effect, this is the
	// number of steps a fox can go before it has to eat again.
	private static int FOX_FOOD_VALUE = 50;
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
	public Bear(boolean startWithRandomAge) {
		super();
		Bear.MAX_AGE = 100;
		Bear.BREEDING_AGE = 10;
		Bear.BREEDING_PROBABILITY = 0.1;
		Bear.MAX_LITTER_SIZE = 1;
		Animal(startWithRandomAge);
		if (startWithRandomAge) {
			foodLevel = (int)(Math.random()*FOX_FOOD_VALUE);
		} else {
			foodLevel = FOX_FOOD_VALUE;
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
	public void hunt(Field currentField, Field updatedField, List<Bear> babyBearStorage) {
		incrementAge();
		incrementHunger();
		if (alive) {
			// New foxes are born into adjacent locations.
			int births = breed();
			for (int b = 0; b < births; b++) {
				Bear newBear = new Bear(false);
				newBear.setFoodLevel(this.foodLevel);
				babyBearStorage.add(newBear);
				Location loc = updatedField.randomAdjacentLocation(location);
				newBear.setLocation(loc);
				updatedField.put(newBear, loc);
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
			if (animal instanceof Fox) {
				Fox fox = (Fox) animal;
				if (fox.isAlive()) {
					fox.setEaten();
					foodLevel = FOX_FOOD_VALUE;
					return where;
				}
			}
		}

		return null;
	}

	public void setFoodLevel(int fl) {
		this.foodLevel = fl;
	}
}
