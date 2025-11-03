package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Controls the camera to follow the player smoothly.
 * Keeps the player centered on the screen with a small offset.
 * Simple but effective way to track the player's movement through the levels.
 */

//Camera class to control the view
public class Camera {
    //Fields
    private UserView view; // The UserView to control
    private DynamicBody target; // The player (sprite) to follow
    private float offsetY; // Vertical offset to keep the player centered
    //Constructor
    public Camera(UserView view, DynamicBody target, float offsetY) {
        this.view = view;
        this.target = target;
        this.offsetY = offsetY;
    }

    /**
     * Updates the camera position to follow the player.
     * Keeps the player centered on the screen vertically.
     */

    //Updates the camera position to follow the player
    public void update() {
        //Retrieves the player's current position
        Vec2 playerPosition = target.getPosition();

        //Calculates the new centre for the view
        float centerX = view.getCentre().x; //X position fixed
        float centerY = playerPosition.y + offsetY; //Adjusts the Y position based on the player

        //Sets the new centre for the view
        view.setCentre(new Vec2(centerX, centerY));
    }
}