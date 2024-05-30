public class BurstProjectile extends Projectile{
    public BurstProjectile(Vector2 position, float rotation)
    {
        super(position,new SpritesheetRenderer("burst_shot_projectile",5,1,new SpriteAnimation[]{
                new SpriteAnimation(0,4,20,false)
        }),rotation,400,true);
        lifetime = 0.25f;
        piercing = true;
    }

}
