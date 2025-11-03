package game;

import city.cs.engine.UserView;
import java.awt.*;

/**
 * Draws the player's health bar on the screen.
 * Updates visually when the player takes damage.
 * Shows how much health the player has left during the game.
 */

//Healthbar class to represent a health bar
public class Healthbar {
    private int maxHealth; //Maximum health of the player
    private int currentHealth; //Current health of the player
    private int width; //Width of the health bar
    private int height; //Height of the health bar
    private Color color; //Colour of the health bar

    /**
     * Constructor for the Healthbar.
     * Sets the maximum health, dimensions, and the colour for the bar.
     *
     * @param maxHealth the maximum health value.
     * @param width the width of the health bar.
     * @param height the height of the health bar.
     * @param color the colour used to fill the health bar.
     */
    public Healthbar(int maxHealth, int width, int height, Color color) {
        this.maxHealth = maxHealth; //Set the maximum health
        this.currentHealth = maxHealth; //Set the current health to maximum
        this.width = width; //Set the width of the health bar
        this.height = height; //Set the height of the health bar
        this.color = color; //Set the colour of the health bar
    }
    /**
     * Updates the current health value.
     * Makes sure the health never goes below 0 or above the maximum.
     *
     * @param health the new health value to set.
     */
    public void setHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(health, maxHealth)); // Ensure health stays within bounds
    }
    /**
     * Draws the health bar on the screen.
     * Fills the health bar based on current health and draws the border.
     *
     * @param g the Graphics2D object used to draw.
     * @param x the x-coordinate on the screen where the health bar starts.
     * @param y the y-coordinate on the screen where the health bar starts.
     */
    public void draw(Graphics2D g, int x, int y) {
        //Draw the background (empty health bar)
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);

        //Draw the filled health bar
        int filledWidth = (int) ((double) currentHealth / maxHealth * width);
        g.setColor(color);
        g.fillRect(x, y, filledWidth, height);

        //Draw the border
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
    }
}
