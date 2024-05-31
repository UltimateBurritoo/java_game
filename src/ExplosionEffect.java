public class ExplosionEffect extends EffectEntity{
    boolean dealtDamage = false;
    float damage = 100;
    float knockback = 250;
    float radius = 72;
    public ExplosionEffect(Vector2 position)
    {
        super(position,new SpritesheetRenderer("explosion",8,1,new SpriteAnimation[]
                {
                        new SpriteAnimation(0,7,24,false)
                }),0.35f);
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getKnockback() {
        return knockback;
    }

    public void setKnockback(float knockback) {
        this.knockback = knockback;
    }

    public void tick(float dt)
    {
        super.tick(dt);
        if (!dealtDamage)
        {
            GameWindow.addScreenShake(20);
            for (EntityBase entity : GameWindow.activeEntities)
            {
                if (entity instanceof LivingEntity && entity.getPosition().distance(getPosition()) <= radius)
                {
                    // damage all nearby entities with a linear distance falloff
                    LivingEntity e = (LivingEntity)entity;
                    float attenuation = Math.clamp(1f-(e.getPosition().distance(getPosition())/radius),0f,1f);
                    e.damage(damage*attenuation);
                    e.knockback(e.getPosition().subtracted(getPosition()).normalized().multiplied(knockback));
                }
            }
            dealtDamage = true;
        }
    }
}
