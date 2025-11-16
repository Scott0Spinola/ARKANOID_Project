package pt.uma.tpsi.ad.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundManagement {

    private Texture background;
    private Sprite sprite;
    SpriteBatch spriteBatch;

    public BackgroundManagement(SpriteBatch batch) {
        this.spriteBatch = batch;
        background = new Texture("lavaMap.png");
        sprite = new Sprite(background);
        sprite.setPosition(0, 0);
    }

    public void render() {
        sprite.draw(spriteBatch);
    }

}
