package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * PlayerControls class to handle player movement and jumping.
 * Implements KeyAdapter to listen for key events.
 * Uses a collision listener to detect when the player is on the ground.
 */

//PlayerControls class to handle player movement
public class PlayerControls extends KeyAdapter {
   //Fields
    private DynamicBody student;
    private boolean isJumping;
    private boolean isGrounded;
    //Sound effect for jumping
    private static SoundClip jumpSound;

    static {
        try {
            jumpSound = new SoundClip("data/jump2.wav");  // Make sure the sound file is in your data folder!
        } catch (Exception e) {
            System.out.println("Error loading jump sound: " + e);
        }
    }

    //Constructor
    public PlayerControls(DynamicBody student) {
        //Initialise fields
        this.student = student;
        this.isJumping = false; //Track the jump state
        this.isGrounded = false; //Track if player is on the ground
        addCollisionListener(); //Add collision listener to detect when the player hits the ground
    }

    /**
     * Called when a key is pressed.
     * Handles player movement (left/right) and jumping.
     *
     * @param e the key event triggered when a key is pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //Move left
        if (e.getKeyCode() == KeyEvent.VK_A) {
            student.setLinearVelocity(new Vec2(-10, student.getLinearVelocity().y)); // Moves sprite to the left
        }
        //Move right
        if (e.getKeyCode() == KeyEvent.VK_D) {
            student.setLinearVelocity(new Vec2(10, student.getLinearVelocity().y)); // Moves sprite to the right
        }
        //Jump if not already jumping and on the ground
        if (e.getKeyCode() == KeyEvent.VK_W && !isJumping && isGrounded) {
            //Apply an impulse to the student to make it jump
            student.applyImpulse(new Vec2(0, 3));
            //Set the jump state to true
            isJumping = true;
            if (jumpSound != null) {
                //Play the jump sound
                jumpSound.play();
            }
        }
    }
    /**
     * Called when a key is released.
     * Stops the horizontal movement when left or right key is released.
     *
     * @param e the key event triggered when a key is released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        //Stops movement when the A or D key is released
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
            student.setLinearVelocity(new Vec2(0, student.getLinearVelocity().y)); // Stops horizontal movement
        }
    }

    /**
     * Adds a collision listener to the player.
     * Detects when the player is touching platforms so that jumping is only possible when grounded.
     */
    private void addCollisionListener() {
        student.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent e) {
                //Check if the player is colliding with a platform, moving platform or disappearing platform
                if (e.getOtherBody() instanceof Platform ||
                        //
                        e.getOtherBody() instanceof MovingPlatform ||
                        e.getOtherBody() instanceof DisappearingPlatform) {
                    //If the player is colliding with a platform, set isGrounded to true
                    isGrounded = true;
                    //If the player is not jumping, set isJumping to false
                    isJumping = false;
                }
                }

        });
    }

    /**
     * Method to check whether the player is grounded.
     * If not grounded, marks the player as jumping.
     */
    public void checkForGrounded() {
        if (!isGrounded) {
            isJumping = true;  //Start the jump if not grounded
        }
    }
}
