public class SparkProjectile extends Projectile{
    public SparkProjectile(Vector2 position, float rotation)
    {
        super(position,new SpritesheetRenderer("electric_spark_projectile",6,1,new SpriteAnimation[]{
                new SpriteAnimation(0,5,12,false)
        }),rotation,400,true);
        collider = new AABB(0,0,32,32);
        friction = 100;
        lifetime = 0.5f;
        tilemapCollision = false;
        piercing = true;
    }
    public void hit(LivingEntity e)
    {
        setVelocity(new Vector2((float)Math.random()-0.5f,(float)Math.random()-0.5f).normalized().multiplied(getVelocity().magnitude()));
    }
}
