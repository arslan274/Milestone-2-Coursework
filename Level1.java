package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * First level of the game.
 * Introduces basic platforming and an enemy.
 * Simple layout designed to ease the player into the game.
 */

public class Level1 extends GameLevel {
    /**
     * Populates the level with platforms, walls, the player, one enemy, and collectibles.
     * Sets up the main environment for Level 1.
     */

    @Override
    protected void populate() {
        //Platforms layout data: {width, height, x, y}
        float[][] platformData = {
                {30, 0.5f, 0f, -11.5f},
                {3, 0.5f, -8, -4f},
                {1, 0.5f, 10, -7f},
                {2, 0.5f, 5, 3f},
                {1, 0.5f, -8, 7f},
                {3, 0.5f, 0, 14f},
                {2, 0.5f, 5, 20f},
                {10, 0.5f, -5, 25f},
                {4, 0.5f, 11, 25f}
        };
        //Create platforms based on the data
        for (float[] data : platformData) {
            new Platform(this, data[0], data[1], data[2], data[3]);
        }

        //Add side walls to prevent the player from falling out of the level
        new Platform(this, 0.5f, 60, -15.5f, 0);
        new Platform(this, 0.5f, 60, 15.5f, 0);

        //Add the player to the level
        Shape studentShape = new BoxShape(0.25f, 0.25f);
        student = new DynamicBody(this, studentShape);
        student.setPosition(new Vec2(4, -5));
        student.setAngularVelocity(0);
        student.addImage(new BodyImage("data/sprite-new2.png", 0.5f));

        //Add an enemy near the ground platform
        Shape enemyShape = new BoxShape(0.25f, 0.25f);
        enemy = new Enemy(this, enemyShape, -14, -11);

        //Spawn collectibles across the level
        collectibleManager.spawnCollectible(5, 4);
        collectibleManager.spawnCollectible(-7, -3);
        collectibleManager.spawnCollectible(-8, 8);
        collectibleManager.spawnCollectible(0, 15);
        collectibleManager.spawnCollectible(7, 21);
    }

    /**
     * Returns the file path for the background image of this level.
     *
     * @return the path to the background image as a String.
     */
    @Override
    public String getBackgroundImage() {
        return "data/background.jpg";
    }
}
