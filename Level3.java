package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Third and final level.
 * Includes disappearing platforms, patrolling enemies, and moving platforms.
 * Win condition is triggered when the player reaches a certain height.
 */

public class Level3 extends GameLevel {

    //fields
    private Enemy enemy;
    private PatrollingEnemy patrolEnemy;

    /**
     * Sets up Level 3 by adding platforms, walls, the player, enemies, disappearing platforms,
     * moving platforms, patrolling enemies, and collectibles.
     * This level is designed to be the hardest, with more obstacles and hazards.
     */
    @Override
    protected void populate() {
        //Platform data: {width, height, x, y}
        float[][] platformData = {
                {30, 0.5f, 0f, -11.5f},
                {2, 0.5f, 0, -9},
                {5, 0.5f, -3, 15},
                {5, 0.5f, 0, 48},
                {5, 0.5f, 0, 55},
                {5, 0.5f, 0, 62},

        };
        //Create platforms using the data array
        for (float[] data : platformData) {
            new Platform(this, data[0], data[1], data[2], data[3]);
        }

        //Disappearing platform (adds difficulty)
        new DisappearingPlatform(this, 1f, 0.5f, -3, 17);
        new DisappearingPlatform(this, 1f, 0.5f, -10, 22);
        new DisappearingPlatform(this, 1f, 0.5f, 0, 28);
        new DisappearingPlatform(this, 1f, 0.5f, 10, 35);

        //Collectibles for Level 3
        collectibleManager.spawnCollectible(0, -8);
        collectibleManager.spawnCollectible(-3, 18);
        collectibleManager.spawnCollectible(-10, 23);
        collectibleManager.spawnCollectible(-10, 3);
        collectibleManager.spawnCollectible(0, 29);
        collectibleManager.spawnCollectible(10, 36);

        //Side walls
        new Platform(this, 0.5f, 100, -15.5f, 0);
        new Platform(this, 0.5f, 100, 15.5f, 0);

        //Player (student)
        Shape studentShape = new BoxShape(0.25f, 0.25f);
        student = new DynamicBody(this, studentShape);
        student.setPosition(new Vec2(13, -10));
        student.setAngularVelocity(0);
        student.addImage(new BodyImage("data/sprite-new2.png", 0.5f));

        //Static enemy on the ground level
        Shape enemyShape = new BoxShape(0.25f, 0.25f);
        enemy = new Enemy(this, enemyShape, -1, -10);

        //Moving Platforms
        new MovingPlatform(this, 3, 0.5f, new Vec2(-10, -5), new Vec2(-10, 7), 0.05f);
        new MovingPlatform(this, 2, 0.5f, new Vec2(11, 1), new Vec2(9, 13), 0.1f);
        new MovingPlatform(this, 1, 0.5f, new Vec2(-9, 42), new Vec2(7, 42), 0.05f);

        //Patrolling enemy on the upper platform
        Shape patrolEnemyShape = new BoxShape(0.25f, 0.25f);
        patrolEnemy = new PatrollingEnemy(
                this,
                patrolEnemyShape,
                -3, 16,         //Start position (on the patrolling platform)
                -7f, 1f         //Patrol limits (left and right edges of the platform)
        );
        patrolEnemy = new PatrollingEnemy(
                this,
                patrolEnemyShape,
                0, 49,         //Start position (on the patrolling platform)
                -4.5f, 4.5f //Patrol limits (left and right edges of the platform)
        );
        patrolEnemy = new PatrollingEnemy(
                this,
                patrolEnemyShape,
                0, 56,         //Start position (on the patrolling platform)
                -4.5f, 4.5f //Patrol limits (left and right edges of the platform)
        );
        patrolEnemy = new PatrollingEnemy(
                this,
                patrolEnemyShape,
                0, 63,         //Start position (on the patrolling platform)
                -4.5f, 4.5f //Patrol limits (left and right edges of the platform)
        );
    }
    /**
     * Returns the background image used for Level 3.
     * This helps differentiate each level visually.
     *
     * @return the file path of the Level 3 background image.
     */
    @Override
    public String getBackgroundImage() {
        return "data/background3.jpg";
    }
    /**
     * Returns the enemy object for this level.
     * Used by the Game class to control enemy behavior like following the player.
     *
     * @return the static enemy on the ground in Level 3.
     */
    @Override
    public Enemy getEnemy() {
        return enemy;
    }
}
