package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.ad.game.Animator;

public class PlayerPaddle {
    private Animator animator;
    private int posX, posY;
    private Rectangle boundingBox;


    public PlayerPaddle(SpriteBatch batch) {
        animator = new Animator(batch, "full_paddle.png", 6, 1);
    }

    public void create() {
        animator.create();
        posX = (Gdx.graphics.getWidth() / 2) - this.animator.getWidth() / 2;
        posY = this.animator.getHeight();
        boundingBox = new Rectangle(posX, posY, animator.getWidth(), animator.getHeight());
    }

    public void render() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            posX -= 3;
            if (posX < 0) posX = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            posX += 3;
            if (posX > Gdx.graphics.getWidth() - animator.getWidth()) {
                posX = Gdx.graphics.getWidth() - animator.getWidth();
            }
        }
        boundingBox.setPosition(posX, posY);
        animator.render(posX, posY);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }
}

