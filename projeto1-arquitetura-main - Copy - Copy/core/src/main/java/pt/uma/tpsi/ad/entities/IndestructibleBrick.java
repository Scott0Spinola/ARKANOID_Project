package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IndestructibleBrick extends Brick {

    public IndestructibleBrick(SpriteBatch batch, int posX, int posY) {
        super(batch, posX, posY, "indestructible_brick.png");
        this.points = 0;
    }

    @Override
    public boolean onCollision() {
        return false;
    }
}
