package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.ad.game.Animator;

public class Ball {


    private Animator animator;
    private int posX, posY;
    private Rectangle boundingBox;
    private int directionY;
    private int directionX;
    private int angle;
    private boolean outOfbounds;


    public Ball(SpriteBatch batch) {
        animator = new Animator(batch, "fireball.png", 2, 2);
        this.directionY = 1;
        this.directionX = 1;
        this.angle = 2;
    }

    public void create() {
        animator.create();
        posX = (Gdx.graphics.getWidth() / 2) - this.animator.getWidth() / 2;
        posY = this.animator.getHeight() * 2;
        boundingBox = new Rectangle(posX, posY, animator.getWidth(), animator.getHeight());
    }

    public void render() {
        if (!outOfbounds) {
            posY += (angle * directionY);
            posX += (angle * directionX);

            if (posY > Gdx.graphics.getHeight() - animator.getHeight()) {
                directionY = -1;
            } else if (posY < -animator.getHeight()) {
                outOfbounds = true;
            }

            if (posX > Gdx.graphics.getWidth() - animator.getWidth()) {
                directionX = -1;
            } else if (posX < 0) {
                directionX = 1;
            }
        }

        boundingBox.setPosition(posX, posY);
        animator.render(posX, posY);
    }

    public void setAngleFromPaddleHit(Rectangle paddle) {
        float paddleThird = paddle.width / 3;
        float paddleLeft = paddle.x;
        float paddleMiddle = paddle.x + paddleThird;
        float paddleRight = paddle.x + 2 * paddleThird;

                int ballCenterX = posX + animator.getWidth() / 2;

        if (ballCenterX >= paddleLeft && ballCenterX < paddleMiddle) {
            // Hit left side
            directionX = -1; // Go left
            angle = 2; // Steeper angle
        } else if (ballCenterX >= paddleMiddle && ballCenterX < paddleRight) {
            // Hit middle
            directionX = 0; // Go straight up
            angle = 2;
        } else {
            // Hit right side
            directionX = 1; // Go right
            angle = 2; // Steeper angle
        }
        // The ball should always go up after hitting the paddle
        directionY = 1;
    }




    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public boolean isOutOfBounds() {
        return outOfbounds;
    }

    public int getDirectiony() {
        return directionY;
    }

    public int getDirectionx() {
        return directionX;
    }

    public void reverseDirectionY() {
        this.directionY *= -1;
    }

    public void reverseDirectionX() {
        this.directionX *= -1;
    }

    // Helper for grid collisions to invert vertical trajectory
    public void bounceVertical() {
        reverseDirectionY();
    }

    // Force the ball to move downward (used for brick collisions when simplifying response)
    public void forceDown() {
        this.directionY = -1;
    }
}
