public class BulletProjectile extends Projectile{
    public BulletProjectile(Vector2 position, float rotation)
    {
        super(position,new SpriteRenderer("magic_bullet_projectile"),rotation,400,true);
        lifetime = 2;
    }

}
