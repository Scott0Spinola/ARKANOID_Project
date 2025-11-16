package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.ad.game.Animator;

public abstract class Brick {

    protected int posX, posY;
    protected Animator animator;
    protected Rectangle boundingBox;
    protected boolean collided;
    protected int points;


    public Brick(SpriteBatch batch, int posX, int posY, String texturePath ) {
        this.posX = posX;
        this.posY = posY;
        this.points = 100;
        this.animator = new Animator(batch, texturePath, 2, 1);
        this.collided = false;
    }

    public void create() {
        animator.create();
        boundingBox = new Rectangle(posX, posY, animator.getWidth(), animator.getHeight());
    }

    public void render() {
        if (!collided) {
            animator.render(posX, posY);
        }
        if (boundingBox != null) {
            boundingBox.setPosition(posX, posY);
        }
    }



    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public int getPoints() {
        return points;
    }

    public boolean isCollided() {
        return collided;
    }

    public int getX() { return posX; }
    public int getY() { return posY; }


    public abstract boolean onCollision();
}
