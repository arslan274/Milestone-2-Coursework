package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Second level of the game.
 * Adds moving platforms and multiple patrolling enemies for extra difficulty.
 * More complex layout than Level 1 to keep the challenge going.
 */

public class Level2 extends GameLevel {

    private Enemy enemy;

    /**
     * Populates Level 2 with platforms, walls, the player, one static enemy, two patrolling enemies,
     * moving platforms, and collectibles.
     * Sets up the world and adds extra features compared to Level 1 for added challenge.
     */

    @Override
    public void populate() {
        //Platform data: {width, height, x, y}
        float[][] platformData = {

                //{width, height, x, y}
                {30, 0.5f, 0f, -11.5f},     // Ground
                {2, 0.5f, -6, -5},          // Small platform
                {2, 0.5f, 6, 2},            // Small platform
                {1, 0.5f, -6, 6},           // Tight jump
                {2f, 0.5f, 0, 25},           // Wider rest
                {1, 0.5f, -6, 30},          // Jump back across
                {3, 0.5f, 6, 35},           // Final platform

        };
        //Create platforms using the data array
        for (float[] data : platformData) {
            new Platform(this, data[0], data[1], data[2], data[3]);
        }

        //Collectibles
        collectibleManager.spawnCollectible(-6, 31);
        collectibleManager.spawnCollectible(-6, -4);
        collectibleManager.spawnCollectible(-6, 7);
        collectibleManager.spawnCollectible(6, 20);

        //Walls
        new Platform(this, 0.5f, 100, -15.5f, 0);
        new Platform(this, 0.5f, 100, 15.5f, 0);

        //Add player
        Shape studentShape = new BoxShape(0.25f, 0.25f);
        student = new DynamicBody(this, studentShape);
        student.setPosition(new Vec2(4, -5));
        student.setAngularVelocity(0);
        student.addImage(new BodyImage("data/sprite-new2.png", 0.5f));

        //Add enemy
        Shape enemyShape = new BoxShape(0.25f, 0.25f);
        enemy = new Enemy(this, enemyShape, 2, -10);

        //Moving Platforms
        new MovingPlatform(this, 2, 0.5f, new Vec2(-4, 12), new Vec2(4, 12), 0.1f); // horizontal
        new MovingPlatform(this, 2, 0.5f, new Vec2(6, 18), new Vec2(6, 24), 0.05f); // vertical

        //Patrolling enemies
        Shape patrolEnemyShape = new BoxShape(0.25f, 0.25f);

        //Place the patrolling enemy on the platform at y = 25
        PatrollingEnemy patrolEnemy = new PatrollingEnemy(
                this,          //world
                patrolEnemyShape,     //shape
                0, 26,            //start position
                -2f, 2f               //movement limits
        );
        //Place the second patrolling enemy on the platform at y = 35
        PatrollingEnemy patrolEnemy2 = new PatrollingEnemy(
                this,
                patrolEnemyShape,
                6, 2.5f,           // Position on the second platform
                4f, 8f             // Patrol limits, matching the width of the platform
        );

    }

    /**
     * Returns the file path for the background image used in Level 2.
     *
     * @return the path to the background image as a String.
     */
    public String getBackgroundImage() {
        return "data/background1.jpg";
    }

    /**
     * Returns the main enemy instance for this level.
     * Used by the Game class to control enemy behavior like following the player.
     *
     * @return the main enemy object in Level 2.
     */
    @Override
    public Enemy getEnemy() {
        return enemy;
    }
}
