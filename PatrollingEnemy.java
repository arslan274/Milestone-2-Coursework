package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Patrolling enemy class that moves back and forth between two points.
 * Implements StepListener to update its position every step.
 * The enemy moves left and right between leftLimit and rightLimit at a specified speed.
 */

// Enemy that patrols horizontally between two points
public class PatrollingEnemy extends DynamicBody implements StepListener {
    private static final float SPEED = 5f;
    private float leftLimit;
    private float rightLimit;
    private boolean movingRight = true;

    /**
     * Constructor for the patrolling enemy.
     *
     * @param world      the world where the enemy exists
     * @param shape      the shape of the enemy (used for collision)
     * @param x          the initial x position of the enemy
     * @param y          the initial y position of the enemy
     * @param leftLimit  the furthest left x-position the enemy can patrol to
     * @param rightLimit the furthest right x-position the enemy can patrol to
     */

    //Constructor for the patrolling enemy
    public PatrollingEnemy(World world, Shape shape, float x, float y, float leftLimit, float rightLimit) {
        //Call the parent constructor with the given shape
        super(world, shape);
        //Set the initial position of the enemy
        setPosition(new Vec2(x, y));
        //Add a box shape for collision detection
        addImage(new BodyImage("data/enemy-new.png", 0.5f));
        //Set the limits for patrolling
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
        //Add this enemy to the physics world
        world.addStepListener(this);
    }

    /**
     * Called before each physics step.
     * Handles the enemy movement logic to patrol between the two limits.
     *
     * @param e the StepEvent triggered each simulation step
     */

    @Override


    public void preStep(StepEvent e) {
        Vec2 pos = getPosition();

        //Check if the enemy is moving right or left and update its velocity accordingly
        if (movingRight) {
            setLinearVelocity(new Vec2(SPEED, 0));
            if (pos.x >= rightLimit) {
                movingRight = false;
            }
        } else {
            setLinearVelocity(new Vec2(-SPEED, 0));
            if (pos.x <= leftLimit) {
                movingRight = true;
            }
        }
    }

    /**
     * Called after each physics step.
     * Not used here, but required by the StepListener interface.
     *
     * @param e the StepEvent triggered each simulation step
     */

    @Override
    public void postStep(StepEvent e) {
        // Not needed
    }
}
