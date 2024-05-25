public abstract class Projectile extends EntityBase{
    float rotation;
    float lifetime = 3;
    float time;

    public Projectile(Vector2 position, GameRenderer r, float rotation)
    {
        super(position,r);
        this.rotation = rotation;
    }
    public Projectile(Vector2 position, GameRenderer r, float rotation, float speed)
    {
        super(position,r);
        this.rotation = rotation;
        this.velocity = new Vector2((float)Math.cos(rotation)*speed,(float)Math.sin(rotation)*speed);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getLifetime() {
        return lifetime;
    }

    public void setLifetime(float lifetime) {
        this.lifetime = lifetime;
    }

    public void tick(float dt)
    {
        super.tick(dt);
        time += dt;
        if(time > lifetime)
        {
            kill();
        }
    }
}
