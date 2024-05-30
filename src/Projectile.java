import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Projectile extends EntityBase{
    float rotation;
    float lifetime = 3;
    boolean player;
    boolean piercing;

    float damage;
    float knockback;

    public Projectile(Vector2 position, GameRenderer r, float rotation,boolean player)
    {
        super(position,r);
        this.rotation = rotation;
        this.velocity = new Vector2((float)Math.cos(rotation)*100,(float)Math.sin(rotation)*100);
        friction = 0;
        this.player = player;
    }
    public Projectile(Vector2 position, GameRenderer r, float rotation, float speed, boolean player)
    {
        super(position,r);
        this.rotation = rotation;
        this.velocity = new Vector2((float)Math.cos(rotation)*speed,(float)Math.sin(rotation)*speed);
        friction = 0;
        this.player = player;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getKnockback() {
        return knockback;
    }

    public void setKnockback(float knockback) {
        this.knockback = knockback;
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
    public void render(Graphics2D g2d)
    {
        if(renderer == null) return;
        Vector2 p = position.subtracted(GameWindow.getViewportPosition());
        //g2d.translate(-collider.getSize().getX()/2,-collider.getSize().getY()/2);
        g2d.rotate(this.rotation,p.getX()+collider.getSize().getX()/2,p.getY()+collider.getSize().getY()/2);
        g2d.translate(p.getX(),p.getY());
        renderer.render(0,0, g2d);
        g2d.setTransform(new AffineTransform());

    }
    public void hit(LivingEntity e)
    {

    }
    public void tick(float dt)
    {
        super.tick(dt);
        EntityBase[] collisions = getTouching(1);
        if(collisions.length != 0 && collisions[0] instanceof LivingEntity && ((player && !(collisions[0] instanceof PlayerEntity)) || (!player && collisions[0] instanceof PlayerEntity)))
        {
            LivingEntity entity = (LivingEntity)collisions[0];
            entity.damage(damage);
            entity.knockback(velocity.normalized().multiplied(knockback));
            hit(entity);
            if(!piercing) kill();
        }
        if(touchingTiles) kill();
        if(time > lifetime)
        {
            kill();
        }
    }
}
