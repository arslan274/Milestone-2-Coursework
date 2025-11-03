package game;

import city.cs.engine.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Displays the Game Over screen when the player dies.
 * Shows the player's final score and lets them restart the game by pressing 'R'.
 * Simple but clear way to handle losing the game.
 */

public class GameOverView extends UserView {
    //Fields
    private JFrame frame;
    private Game game;

    /**
     * Constructor for the GameOverView class.
     * Sets up the JFrame and adds a key listener to handle restarting the game.
     *
     * @param world The game world.
     * @param game  The main game instance.
     */
    public GameOverView(World world, Game game) {
        super(world, 800, 600);
        this.game = game;

        //Setup Game Over JFrame
        frame = new JFrame("Game Over");
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        //Key listener to restart the game
        this.addKeyListener(new KeyAdapter() {
            @Override
            //Key pressed event handler
            public void keyPressed(KeyEvent e) {
                //Check if 'R' key is pressed
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    frame.dispose();
                    game.restartGame();
                }
            }
        });
        //Set focusable to receive key events
        setFocusable(true);
        requestFocus();
    }
    /**
     * Paints the background of the Game Over screen.
     * Fills the screen with a black color.
     *
     * @param g The Graphics2D object used for painting.
     */
    @Override
    protected void paintBackground(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    /**
     * Paints the foreground of the Game Over screen.
     * Displays the game over message, score, and restart instructions.
     *
     * @param g The Graphics2D object used for painting.
     */
    @Override
    protected void paintForeground(Graphics2D g) {
        //Draw the game over message
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 48));
        g.drawString("GAME OVER ☹", 250, 300);
        //Draw the score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 24));
        g.drawString("Your Score: " + game.getScore(), 250, 400);
        //Draw the restart instructions
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        g.drawString("Press 'R' to Restart", 250, 350);
    }
}
