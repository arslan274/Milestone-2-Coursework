package game;

import city.cs.engine.*;

/**
 * Abstract class for all levels.
 * Each level must implement the populate() method to set up its platforms, enemies, and collectibles.
 * This makes it easy to create different levels while reusing code.
 */

public abstract class GameLevel extends World {
    protected DynamicBody student;
    protected Enemy enemy;
    protected CollectibleManager collectibleManager;
    /**
     * Constructor for the GameLevel class.
     * Creates the collectible manager and calls the populate method for the level.
     */
    public GameLevel() {
        collectibleManager = new CollectibleManager(this);
        populate();
    }
    /**
     * Method where the level's bodies, platforms, enemies, and collectibles should be created.
     * Needs to be implemented by each level (Level1, Level2, Level3).
     */
    protected abstract void populate();

    /**
     * Returns the file path of the background image for the level.
     * Each level should provide its own background image.
     * @return the file path of the background image.
     */
    public abstract String getBackgroundImage();

    /**
     * Gets the player (student) object in the level.
     * @return the player (student) body.
     */
    public DynamicBody getStudent() {
        return student;
    }
    /**
     * Gets the enemy object in the level.
     * @return the enemy body.
     */
    public Enemy getEnemy() {
        return enemy;
    }
    /**
     * Gets the collectible manager for the level.
     * @return the collectible manager.
     */
    public CollectibleManager getCollectibleManager() {
        return collectibleManager;
    }
}
