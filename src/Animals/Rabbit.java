package Animals;

import Animals.*;
import Field.*;
import Graph.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kolling.  Modified by David Dobervich 2007-2022
 */
public class Rabbit extends Animal{

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param startWithRandomAge If true, the rabbit will have a random age.
     */
    public Rabbit(boolean startWithRandomAge)
    {
        super();
        BREEDING_AGE = 2;
        MAX_AGE = 10;
        BREEDING_PROBABILITY = 0.1; //0.15
        MAX_LITTER_SIZE = 2;
        startWithRandomAge(startWithRandomAge);
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param updatedField The field to transfer to.
     * @param babyRabbitStorage A list to add newly born rabbits to.
     */
    @Override
    public void act(Field currentField, Field updatedField, ArrayList<Animal> babyRabbitStorage)
    {
        incrementAge();
        if(alive) {
            int births = breed();
            for(int b = 0; b < births; b++) {
                Rabbit newRabbit = new Rabbit(false);
                babyRabbitStorage.add(newRabbit);
                Location loc = updatedField.randomAdjacentLocation(location);
                newRabbit.setLocation(loc);
                updatedField.put(newRabbit, loc);
            }
            Location newLocation = updatedField.freeAdjacentLocation(location);
            // Only transfer to the updated field if there was a free location
            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.put(this, newLocation);
            }
            else {
                // can neither move nor stay - overcrowding - all locations taken
                alive = false;
            }
        }
    }
    
    

    /**
     * Tell the rabbit that it's dead now :(
     */
    public void setEaten()
    {
        alive = false;
    }
    
    
}
