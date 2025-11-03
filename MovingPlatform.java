package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Moving platform class that moves between two points.
 * Implements StepListener to update its position every step.
 * The platform moves back and forth between startPos and endPos at a specified speed.
 */

public class MovingPlatform extends StaticBody implements StepListener {
    private Vec2 startPos;
    private Vec2 endPos;
    private float speed;
    private boolean forward = true;

    /**
     * Constructor to create a moving platform.
     *
     * @param world     the world where the platform exists
     * @param width     the width of the platform
     * @param height    the height of the platform
     * @param startPos  the starting position of the platform
     * @param endPos    the ending position of the platform
     * @param speed     the speed at which the platform moves
     */
    //Constructor to create a moving platform
    public MovingPlatform(World world, float width, float height, Vec2 startPos, Vec2 endPos, float speed) {
        //Call the parent constructor with a box shape
        super(world, new BoxShape(width, height));
        this.startPos = startPos;
        this.endPos = endPos;
        this.speed = speed;

        setPosition(startPos);
        world.addStepListener(this);
    }

    /**
     * Called before each physics step.
     * Handles moving the platform towards its current target position.
     * Once it reaches the target, it switches direction to move back.
     *
     * @param stepEvent the event triggered at each simulation step
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        Vec2 current = getPosition();
        Vec2 target = forward ? endPos : startPos;
        Vec2 direction = target.sub(current);
        //Check if the platform is close to the target position
        if (direction.length() < 0.1f) {
            forward = !forward;

        } else {
            //Normalise the direction vector and scale it by speed
            Vec2 moveStep = direction.clone();
            moveStep.normalize();
            moveStep = moveStep.mul(speed);
            setPosition(current.add(moveStep));
        }
    }

    /**
     * Called after each physics step.
     * Not used here, but required by the StepListener interface.
     *
     * @param stepEvent the event triggered at each simulation step
     */

    @Override
    public void postStep(StepEvent stepEvent) {
        // Not used
    }
}
