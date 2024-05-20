import java.awt.*;

public abstract class EntityBase {
    Vector2 position;
    Vector2 velocity;
    float friction = 1000;
    GameRenderer renderer;
    AABB collider;
    SpritesheetRenderer spritesheet;

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

    public AABB getCollider() {
        return collider;
    }

    public void setCollider(AABB collider) {
        this.collider = collider;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    public void render(Graphics2D g2d)
    {
        if(renderer == null) return;
        renderer.render(position.subtracted(GameWindow.getViewportPosition()), g2d);
        //collider.debugDraw((int)-GameWindow.getViewportPosition().getX(),(int)-GameWindow.getViewportPosition().getY(),g2d);
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
        this.collider = new AABB(position,new Vector2(16,16));
        renderer = r;
        if(r.getClass() == SpritesheetRenderer.class)
        {
            spritesheet = (SpritesheetRenderer)getRenderer();
        }
    }
    public EntityBase(Vector2 position, Vector2 size, GameRenderer r)
    {
        this.position = position;
        this.velocity = Vector2.zero;
        this.collider = new AABB(position,size);
        renderer = r;
        if(r.getClass() == SpritesheetRenderer.class)
        {
            spritesheet = (SpritesheetRenderer)getRenderer();
        }
    }
    public void tick(float dt)
    {
        Vector2 motion = this.velocity.multiplied(dt);
        if(collider.moved(motion).collideTilemap(Game.getWindow().tilemap))
        {
            velocity.set(Vector2.zero);
        }
        else
        {
            position.add(motion);
        }
        velocity.moveTowards(Vector2.zero,dt*friction);
        collider.position.set(position);
        if(spritesheet != null)
        {
            spritesheet.tick(dt);
        }
    }
}
