import java.awt.*;
import java.util.ArrayList;

public abstract class EntityBase {
    Vector2 position;
    Vector2 velocity;
    float friction = 1900;
    GameRenderer renderer;
    AABB collider;
    SpritesheetRenderer spritesheet;
    boolean tilemapCollision = true;
    boolean touchingTiles = false;

    float time;

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

    public EntityBase[] getTouching()
    {
        ArrayList<EntityBase> entities = new ArrayList<EntityBase>();
        for (EntityBase e : GameWindow.activeEntities)
        {
            if(e == this || e.getPosition().distanceSquared(getPosition()) >= Math.pow(Math.max(collider.getSize().getX(),collider.getSize().getY()),2.0)) continue;
            if(e.getCollider().isColliding(collider)) entities.add(e);
        }
        return entities.toArray(new EntityBase[]{});
    }

    public EntityBase[] getTouching(int limit)
    {
        ArrayList<EntityBase> entities = new ArrayList<EntityBase>();
        for (EntityBase e : GameWindow.activeEntities)
        {
            if(e == this || e.getPosition().distanceSquared(getPosition()) >= Math.pow(Math.max(collider.getSize().getX(),collider.getSize().getY()),2.0)) continue;
            if(e.getCollider().isColliding(collider)) entities.add(e);
            if(entities.size() >= limit) break;
        }
        return entities.toArray(new EntityBase[]{});
    }

    public EntityBase(Vector2 position)
    {
        this.position = position;
        this.velocity = new Vector2();
    }
    public EntityBase(Vector2 position, GameRenderer r)
    {
        this.position = position;
        this.velocity = new Vector2(0,0);
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
        this.velocity = new Vector2();
        this.collider = new AABB(position,size);
        renderer = r;
        if(r.getClass() == SpritesheetRenderer.class)
        {
            spritesheet = (SpritesheetRenderer)getRenderer();
        }
    }
    public void tick(float dt)
    {
        if(GameWindow.isGameOver()) return; // Do nothing if the player is dead
        time+=dt;
        touchingTiles = false;
        Vector2 motion = this.velocity.multiplied(dt);
        if(tilemapCollision && collider.moved(motion).collideTilemap(Game.getWindow().tilemap))
        {
            velocity.set(new Vector2());
            touchingTiles = true;
        }
        else
        {
            position.add(motion);
        }
        velocity.moveTowards(new Vector2(),dt*friction);
        collider.position.set(position);
        if(spritesheet != null)
        {
            spritesheet.tick(dt);
        }
    }
    public void kill()
    {
        GameWindow.queueKill(this);
    }
}
