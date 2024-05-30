public class ExitEntity extends EntityBase{
    public ExitEntity(Vector2 position)
    {
        super(position,new Vector2(64,64), new SpriteRenderer("exit"));
    }
    public void tick(float dt)
    {
        super.tick(dt);
        if(Game.getWindow().getPlayer().getCollider().isColliding(getCollider()))
        {
            GameWindow.nextLevel();
        }
    }
}
