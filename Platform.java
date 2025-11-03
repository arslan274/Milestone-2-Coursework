//platforms
package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Basic static platform that the player can stand on.
 * Can be used for the ground, walls, or normal platforms.
 */

//Platform class to create platforms in the game
public class Platform extends StaticBody {
    public Platform(World world, float width, float height, float x, float y) {
        super(world, new BoxShape(width, height));

        setPosition(new Vec2(x, y));

    }
}


