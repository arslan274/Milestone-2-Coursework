package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all the collectibles in the level.
 * Can spawn new collectibles and remove them when collected.
 * Keeps track of active collectibles using a list.
 */

//class to manage collectibles in the game
public class CollectibleManager {
    //Fields
    private World world;
    private List<Collectible> collectibles;
    //Constructor
    public CollectibleManager(World world) {
        this.world = world;
        this.collectibles = new ArrayList<>();
    }

     /*
      * Method to spawn a collectible at a specific position.
      * Creates a new Collectible object and adds it to the list.
      * The collectible is positioned at the specified coordinates.
      */

    //Method to spawn a collectible
    public void spawnCollectible(float x, float y) {
        Collectible collectible = new Collectible(world, 0.5f, 0.5f, x, y);
        collectibles.add(collectible); // Add the collectible to the list
    }
    /*
     * Method to handle the collection of a collectible.
     * When the player collects it, it plays a sound and removes itself from the world.
     * The collectible is removed from the list of active collectibles.
     */
    //Method to remove a collectible
    public void removeCollectible(Collectible collectible) {
        collectibles.remove(collectible);
        collectible.destroy(); // Remove the collectible from the world
    }

    //Method to get the list of collectibles
    public List<Collectible> getCollectibles() {
        return collectibles;
    }
}