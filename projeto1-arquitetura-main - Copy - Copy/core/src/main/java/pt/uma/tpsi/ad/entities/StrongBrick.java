package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StrongBrick extends Brick {
    private int lives = 2;

    public StrongBrick(SpriteBatch batch, int posX, int posY) {
        super(batch, posX, posY, "strong_brick.png");
    }

    @Override
    public boolean onCollision() {
        if (collided) return true;
        lives--;
        if (lives <= 0) {
            collided = true;
        }
        return collided;
    }
}
