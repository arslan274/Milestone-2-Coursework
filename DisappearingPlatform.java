package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * A special platform that flickers and disappears when the player touches it.
 * Gives the player a chance to jump off before the platform fully vanishes.
 * Adds an extra challenge to the level.
 */

public class DisappearingPlatform extends StaticBody implements StepListener {
    private static final int FLICKER_DURATION = 90;  // Total flicker time
    private int flickerCounter = -1;
    private boolean visible = true;

    private SolidFixture fixture;
    private BodyImage platformImage = new BodyImage("data/platform.jpg", 1f);  // Adjust your image path and scale

    public DisappearingPlatform(World world, float width, float height, float x, float y) {
        super(world);
        fixture = new SolidFixture(this, new BoxShape(width, height));
        setPosition(new Vec2(x, y));

        addImage(platformImage);  // Attach image initially

        addCollisionListener(new CollisionListener() {
            @Override
            public void collide(CollisionEvent e) {
                if (e.getOtherBody() instanceof DynamicBody && !(e.getOtherBody() instanceof Collectible)) {
                    if (flickerCounter == -1) {
                        flickerCounter = 0;
                        getWorld().addStepListener(DisappearingPlatform.this);
                    }
                }
            }
        });
    }

    /**
     * Logic that handles the flickering effect and eventual disappearance of the platform.
     * Runs every game step after the player touches the platform.
     * @param stepEvent Event triggered on each simulation step.
     */

    @Override
    public void preStep(StepEvent stepEvent) {
        if (flickerCounter >= 0) {
            flickerCounter++;

            if (flickerCounter % 10 == 0) {  // Flicker every 10 frames
                if (visible) {
                    fixture.destroy();             // Disable collision
                    removeAllImages();             // Remove the image
                } else {
                    fixture = new SolidFixture(this, new BoxShape(1.5f, 0.25f));  // Re-add collision
                    addImage(platformImage);      // Re-add image
                }
                visible = !visible;
            }

            if (flickerCounter >= FLICKER_DURATION) {
                destroy();  // Fully disappear
            }
        }
    }

    /**
     * Logic that handles the flickering effect and eventual disappearance of the platform.
     * Runs every game step after the player touches the platform.
     * @param stepEvent Event triggered on each simulation step.
     */

    @Override
    public void postStep(StepEvent stepEvent) {
        // No post-step logic needed
    }
}