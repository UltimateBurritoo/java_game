public class FlamethrowerProjectile extends Projectile{
    public FlamethrowerProjectile(Vector2 position, float rotation)
    {
        super(position,new SpritesheetRenderer("flamethrower_projectile",5,1,new SpriteAnimation[]{
                new SpriteAnimation(0,4,10,false)
        }),rotation,400,true);
        friction = 800;
        lifetime = 0.5f;
        tilemapCollision = false;
        piercing = true;
    }
}
