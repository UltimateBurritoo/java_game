public class PlayerEntity extends EntityBase{
    // temporary until adding entity stats
    float speed = 200;
    public PlayerEntity()
    {
        super(new Vector2(200,200),new SpriteRenderer("test_guy"));
        friction = 700;
    }

    public void tick(float dt) {
        super.tick(dt);
        Vector2 v = new Vector2();
        if(GameWindow.getPressedKey(Keybinds.KEY_LEFT)) v.setX(-1);
        if(GameWindow.getPressedKey(Keybinds.KEY_RIGHT)) v.setX(1);
        if(GameWindow.getPressedKey(Keybinds.KEY_UP)) v.setY(-1);
        if(GameWindow.getPressedKey(Keybinds.KEY_DOWN)) v.setY(1);
        if(!v.isZero()) setVelocity(v.normalized().multiplied(speed));

        GameWindow.viewportPosition.lerp(position.subtracted(new Vector2((float)Game.window.getPixelWidth()/2.0f,(float)Game.window.getPixelHeight()/2.0f)),5.0f*dt);

    }
}
