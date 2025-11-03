package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Basic enemy that follows the player horizontally.
 * Moves left or right depending on where the player is.
 * Doesn't jump or fly—just simple ground chasing.
 */

//Enemy class to represent the enemy object
public class Enemy extends DynamicBody {
    // Speed of the enemy
    private static final float SPEED = 7.5f;

    // Constructor for the enemy object
    public Enemy(World world, Shape shape, float x, float y) {
        // Create the enemy object with a custom shape
        super(world, shape);

        // Set the position of the enemy
        setPosition(new Vec2(x, y));


        // Add an image to the enemy object (scale the image to fit)
        addImage(new BodyImage("data/enemy-new.png", 0.5f)); // Scale the image to fit 5x5 pixels


    }

    /**
     * Makes the enemy follow the player horizontally by adjusting its velocity.
     * @param playerPosition The current position of the player.
     **/
    public void followPlayer(Vec2 playerPosition) {
        Vec2 enemyPosition = getPosition();
        Vec2 direction = new Vec2(playerPosition.x - enemyPosition.x, 0); // Still only horizontal following
        direction.normalize();

        float currentY = getLinearVelocity().y; // Keep current vertical velocity
        Vec2 newVelocity = new Vec2(direction.x * SPEED, currentY);
        setLinearVelocity(newVelocity); // Apply new horizontal, keep vertical
    }

}