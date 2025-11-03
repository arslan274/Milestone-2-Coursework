package game;

import city.cs.engine.*;
import javax.swing.*;
import java.awt.*;
import org.jbox2d.common.Vec2;
import java.awt.event.KeyListener;

/**
 * Main game class that handles level transitions, camera, player health, scoring, and game over logic.
 * Controls the game loop and connects all other components like enemies, platforms, and collectibles.
 * Displays the health bar, score, and win/game over messages.
 */

public class Game {
    //Fields
    private GameLevel currentLevel;
    private UserView view;
    private Camera camera;
    private Healthbar healthBar;
    private int playerHealth = 100;
    private int score = 0;
    //SoundClip collectSound;
    private static SoundClip hurtSound;

    private boolean gameWon = false;


    static {
        //Load the hurt sound
        try {
            hurtSound = new SoundClip("data/hurt.wav");  // Adjust the filename to match your sound
        } catch (Exception e) {
            System.out.println("Error loading hurt sound: " + e);
        }
    }
    /**
     * Returns the current score of the player.
     * @return the current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Constructor for the Game class.
     * Initialises the game by creating the first level, setting up the view, and adding controls.
     * Sets up the camera to follow the player and handles level transitions.
     */
    public Game() {
        currentLevel = new Level1();
        currentLevel.start();

        DynamicBody student = currentLevel.getStudent();
        //Create the view
        view = new UserView(currentLevel, 800, 600) {
            //Override the paint method to draw the background and foreground
            @Override

            protected void paintBackground(Graphics2D g) {
                //Draw the background image
                Image background = new ImageIcon(currentLevel.getBackgroundImage()).getImage();

                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
            //Override the paint method to draw the foreground
            @Override

            //Method to draw the foreground
            protected void paintForeground(Graphics2D g) {
                //Draw the health bar
                healthBar.draw(g, 300, 20);
                //Draw the score
                g.setColor(Color.cyan);
                g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
                g.drawString("Score: " + score, 10, 40);

                //Draw the game win message
                if (gameWon) {
                    g.setFont(new Font("Times New Roman", Font.BOLD, 40));
                    g.setColor(Color.GREEN);
                    g.drawString("YOU WIN!", 300, 300);


                    }
            }
        };

        //set the health bar properties
        healthBar = new Healthbar(100, 200, 20, Color.GREEN);

        //camera follows user
        camera = new Camera(view, student, 0);


        //Set the view for the world
        JFrame frame = new JFrame("Arslan Shaikh");
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        view.setFocusable(true);
        view.requestFocus();

        PlayerControls controls = new PlayerControls(student);
        view.addKeyListener(controls);

        setupLevel(currentLevel);
    }


    //Method to set up the level
    private void setupLevel(GameLevel level) {

        //Set the world for the view
        DynamicBody student = level.getStudent();
        Enemy enemy = level.getEnemy();

        //Set the camera to follow the player
        camera = new Camera(view, student, 0);


        //Remove any existing key listeners and add the new one
        for (KeyListener kl : view.getKeyListeners()) {
            view.removeKeyListener(kl);
        }
        view.addKeyListener(new PlayerControls(student));


        level.addStepListener(new StepListener() {

            @Override

        //Method to update the camera and check for level transitions
            public void preStep(StepEvent stepEvent) {
                camera.update();
                Vec2 pos = student.getPosition();


                student.setAngularVelocity(0);

                float playerY = pos.y;

                //Check for level transitions
                if (playerY > 25 && currentLevel instanceof Level1) {
                    //Switch to Level 2
                    switchToLevel(new Level2());
                    currentLevel.start();

                //Check for level transitions
                } else if (playerY > 35 && currentLevel instanceof Level2) {
                    switchToLevel(new Level3()); // Add your Level3 logic
                    currentLevel.start();
                }

                //Pauses the game once won
                if (gameWon) {
                    currentLevel.stop();
                    return;
                }

                //Check for win condition
                if (playerY > 69 && currentLevel instanceof Level3 && !gameWon) {
                    gameWon = true;  // ✅ Trigger the win condition
                    System.out.println("Player has won the game!");
                }

                //Make the enemy follow the player
                if (enemy != null) {
                    enemy.followPlayer(student.getPosition());
                }
            }

            @Override
            public void postStep(StepEvent stepEvent) {}
        });

        student.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent e) {
                if (e.getOtherBody() instanceof Enemy || e.getOtherBody() instanceof PatrollingEnemy) {
                    playerHealth -= 25;
                    healthBar.setHealth(playerHealth);

                    if (hurtSound != null) {
                        hurtSound.play();
                    }

                    Vec2 currentVel = student.getLinearVelocity();
                    student.setLinearVelocity(new Vec2(currentVel.x, currentVel.y + 2));

                    if (playerHealth <= 0) {
                        showGameOver();
                    }
                }
            }
        });

        student.addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent e) {
                if (e.getOtherBody() instanceof Collectible) {
                    Collectible coin = (Collectible) e.getOtherBody();
                    score += 10;
                    coin.playCollectSound();  // ✅ Play the sound!
                    level.getCollectibleManager().removeCollectible((Collectible) e.getOtherBody());
                }
            }
        });
    }


    /**
     * Switches from the current level to a new level.
     * Stops the current level, starts the new one, resets the camera and controls.
     * @param newLevel the new level to switch to.
     */
    private void switchToLevel(GameLevel newLevel) {
        currentLevel.stop();
        currentLevel = newLevel;
        currentLevel.start();

        view.setWorld(currentLevel);
        setupLevel(currentLevel);
        view.repaint();
    }

    // ✅ Game Over Screen logic
    public void showGameOver() {
        currentLevel.stop();
        new GameOverView(currentLevel, this);
    }

    /**
     * Restarts the game back to Level 1.
     * Resets the score, health bar, camera, and controls.
     */
    public void restartGame() {
        currentLevel = new Level1();
        currentLevel.start();
        score = 0;
        playerHealth = 100;

        healthBar.setHealth(playerHealth);

        view.setWorld(currentLevel);
        camera = new Camera(view, currentLevel.getStudent(), 0);

        //Remove any existing key listeners and add the new one
        for (KeyListener kl : view.getKeyListeners()) {
            view.removeKeyListener(kl);
        }
        view.addKeyListener(new PlayerControls(currentLevel.getStudent()));

        setupLevel(currentLevel);
        view.repaint();
    }

    /**
     * Main entry point of the program.
     * Launches the game by creating a new Game instance.
     * @param args command-line arguments (not used).
     */

    public static void main(String[] args) {
        new Game();
    }
}
