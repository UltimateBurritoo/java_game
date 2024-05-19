import java.awt.*;

public abstract class EntityBase {
    Vector2 position;
    Vector2 velocity;
    float friction = 1000;
    GameRenderer renderer;

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getFriction() {
        return friction;
    }

    public GameRenderer getRenderer() {
        return renderer;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    public void render(Graphics2D g2d)
    {
        if(renderer == null) return;
        renderer.render(position.subtracted(GameWindow.getViewportPosition()), g2d);
    }


    public EntityBase(Vector2 position)
    {
        this.position = position;
        this.velocity = Vector2.zero;
    }
    public EntityBase(Vector2 position, GameRenderer r)
    {
        this.position = position;
        this.velocity = Vector2.zero;
        renderer = r;
    }
    public void tick(float dt)
    {
        this.position.add(this.velocity.multiplied(dt));
        this.velocity.moveTowards(Vector2.zero,dt*friction);
    }
}
