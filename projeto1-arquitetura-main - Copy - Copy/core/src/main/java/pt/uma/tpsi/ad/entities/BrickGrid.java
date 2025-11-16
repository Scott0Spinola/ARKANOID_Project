package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class BrickGrid extends ArrayList<Brick> {
    // Rendering/context
    private final SpriteBatch batch;

    // Grid config
    private int rows = 8;
    private int cols = 20;
    private int hSpacing = 6; // horizontal spacing between bricks
    private int vSpacing = 6; // vertical spacing between bricks
    private int topMargin = 64; // margin from the top of the screen

    private final Random random = new Random();

    public BrickGrid(SpriteBatch batch) {
        this.batch = batch;
    }

    // Create grid with default size
    public void create() {
        createGrid(rows, cols);
    }

    // Create grid with a specific size; centered at top of the screen
    public void createGrid(int rows, int cols) {
        this.clear();
        this.rows = rows;
        this.cols = cols;

        // Probe a brick to determine dimensions
        NormalBrick probe = new NormalBrick(batch, 0, 0);
        probe.create();
        int brickW = (int) probe.getBoundingBox().getWidth();
        int brickH = (int) probe.getBoundingBox().getHeight();

        int totalWidth = cols * brickW + (cols - 1) * hSpacing;
        int startX = (Gdx.graphics.getWidth() - totalWidth) / 2;
        int startYTop = Gdx.graphics.getHeight() - topMargin - brickH;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * (brickW + hSpacing);
                int y = startYTop - r * (brickH + vSpacing);
                Brick brick = randomBrick(x, y);
                brick.create();
                this.add(brick);
            }
        }
    }

    private Brick randomBrick(int x, int y) {
        double roll = random.nextDouble();
        if (roll < 0.7) {
            return new NormalBrick(batch, x, y);
        } else if (roll < 0.85) {
            return new StrongBrick(batch, x, y);
        } else if (roll < 0.95) {
            return new PowerBrick(batch, x, y);
        } else {
            return new IndestructibleBrick(batch, x, y);
        }
    }

    public boolean isWon() {
        if (this.isEmpty()) {
            return true;
        }
        for (Brick brick : this) {
            if (!(brick instanceof IndestructibleBrick)) {
                return false;
            }
        }
        return true;
    }

    // Draw all bricks
    public void render() {
        for (Brick b : this) {
            b.render();
        }
    }

    // Verify and manage collisions with the ball
    public int checkCollisions(Ball ball) {
        if (ball == null || ball.getBoundingBox() == null) return 0;
        Rectangle ballBox = ball.getBoundingBox();

        Iterator<Brick> it = this.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            Rectangle bb = brick.getBoundingBox();
            if (bb == null) continue;

            // verifica boundingBox
            if (ballBox.overlaps(bb)) {
                // chama isCollided
                if (!brick.isCollided()) {
                    // chama handleCollision (grid-level handler)
                    handleCollision(ball, brick);
                    // chama onCollision
                    boolean destroyed = brick.onCollision();
                    // se o brick deve ser destruído, removes via Iterator
                    if (destroyed) {
                        it.remove();
                        return brick.getPoints();
                    }
                }
                // Only process one brick per frame to keep physics stable
                break;
            }
        }
        return 0;
    }

    // Simple collision response to alter ball trajectory
    private void handleCollision(Ball ball, Brick brick) {
        Rectangle bb = brick.getBoundingBox();
        Rectangle b = ball.getBoundingBox();
        if (bb == null || b == null) return;

        // Compute penetration on each axis using centers and half-extents
        float ballCenterX = b.x + b.width / 2f;
        float ballCenterY = b.y + b.height / 2f;
        float brickCenterX = bb.x + bb.width / 2f;
        float brickCenterY = bb.y + bb.height / 2f;

        float dx = ballCenterX - brickCenterX;
        float dy = ballCenterY - brickCenterY;
        float px = (bb.width / 2f + b.width / 2f) - Math.abs(dx);
        float py = (bb.height / 2f + b.height / 2f) - Math.abs(dy);

        if (px < py) {
            // Side hit: invert horizontal
            ball.reverseDirectionX();
        } else if (py < px) {
            // Top/bottom hit: invert vertical
            ball.bounceVertical();
        } else {
            // Corner: invert both
            ball.reverseDirectionX();
            ball.bounceVertical();
        }
    }
}
