package pt.uma.tpsi.ad.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.ad.entities.Ball;
import pt.uma.tpsi.ad.entities.BrickGrid;
import pt.uma.tpsi.ad.entities.PlayerPaddle;


public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private BackgroundManagement backgroundManagement;
    private BitmapFont font;
    private PlayerPaddle player;
    private Ball ball;
    private BrickGrid brickGrid;
    private boolean gameOver;
    private boolean gameWon;
    private GlyphLayout layout;
    private int totalPoints;


    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1280, 720);
        batch = new SpriteBatch();
        player = new PlayerPaddle(batch);
        player.create();
        ball = new Ball(batch);
        ball.create();
        brickGrid = new BrickGrid(batch);
        brickGrid.create();
        backgroundManagement = new BackgroundManagement(batch);
        font = new BitmapFont(Gdx.files.internal("gamefont.fnt"),
            Gdx.files.internal("gamefont.png"), false);
        layout = new GlyphLayout();
        totalPoints = 0;
        gameWon = false;

    }

    @Override
    public void render() {

        // Render
        batch.begin();
        backgroundManagement.render();
        if (!gameOver && !gameWon) {
            player.render();
            ball.render();
            brickGrid.render();
            totalPoints += brickGrid.checkCollisions(ball);
            if (ball.getBoundingBox().overlaps(player.getBoundingBox())) {
                if (ball.getDirectiony() == -1) {
                    ball.reverseDirectionY();
                }
                if (ball.getDirectiony() == 1) {
                    ball.setAngleFromPaddleHit(player.getBoundingBox());
                }
            }
            if (ball.isOutOfBounds()) {
                gameOver = true;
            }
            if(brickGrid.isWon()){
                gameWon = true;
            }
        } else {
            // redraw static scene so player sees final state
            player.render();
            ball.render();
            brickGrid.render();
        }

        String scoreText = "Total Points: " + totalPoints;
        layout.setText(font, scoreText);
        float scoreX = Gdx.graphics.getWidth() - layout.width - 10;
        float scoreY = Gdx.graphics.getHeight() - 10;
        font.draw(batch, scoreText, scoreX, scoreY);

        if (gameOver) {
            String message = "GAME OVER";
            layout.setText(font, message);
            float x = (Gdx.graphics.getWidth() - layout.width) / 2f;
            float y = (Gdx.graphics.getHeight() + layout.height) / 2f;
            font.draw(batch, message, x, y);
        } else if (gameWon) {
            String message = "YOU WON";
            layout.setText(font, message);
            float x = (Gdx.graphics.getWidth() - layout.width) / 2f;
            float y = (Gdx.graphics.getHeight() + layout.height) / 2f;
            font.draw(batch, message, x, y);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (font != null) {
            font.dispose();
        }
    }

}
