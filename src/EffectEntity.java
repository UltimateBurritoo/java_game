public abstract class EffectEntity extends EntityBase{
    float lifetime;
    float time;
    public EffectEntity(Vector2 position, GameRenderer r, float lifetime)
    {
        super(position,r);
        this.lifetime = lifetime;
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
