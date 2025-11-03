package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Represents a collectible item (like a coin) in the game.
 * When the player touches it, they gain points and the collectible plays a sound.
 * Each collectible has its own image and position.
 */

public class Collectible extends DynamicBody {
    private static SoundClip collectSound;  // Sound effect

    static {
        try {
            //Load the sound effect for collecting the item
            collectSound = new SoundClip("data/collectible2.wav");
        } catch (Exception e) {
            //Handle the exception if the sound file is not found
            System.out.println("Error loading coin sound: " + e);
        }
    }

    //Constructor for the collectible object
    public Collectible(World world, float width, float height, float x, float y) {
        //Call the parent constructor with the world and shape
        super(world, new BoxShape(width, height));
        //Set the position of the collectible
        setPosition(new Vec2(x, y));
        //Add an image to the collectible object
        addImage(new BodyImage("data/collectible.png", 2 * height));
    }

    /*
     * Method to handle collision with the player.
     * When the player collects the item, it plays a sound and destroys itself.
     */

    // Method to play the sound when collected
    public void playCollectSound() {
        //Play the sound effect when the collectible is collected
        if (collectSound != null) {
            collectSound.play();
        }
    }
}