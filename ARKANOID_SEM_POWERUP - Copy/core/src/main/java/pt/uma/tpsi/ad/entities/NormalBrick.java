package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NormalBrick extends Brick {

    public NormalBrick(SpriteBatch batch, int posX, int posY) {
        super(batch, posX, posY, "normal_brick.png");
    }

    @Override
    public boolean onCollision() {
        if (!collided) {
            collided = true;
        }
        return collided;
    }
}
