package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerBrick extends Brick {

    public PowerBrick(SpriteBatch batch, int posX, int posY) {
        super(batch, posX, posY, "powerup_brick.png");
    }

    @Override
    public boolean onCollision() {
        if (!collided) {
            collided = true;
        }
        return true;
    }
}
