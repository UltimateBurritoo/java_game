public class PlayerEntity extends EntityBase{
    // temporary until adding entity stats
    float speed = 150;
    SpritesheetRenderer spritesheet;
    public PlayerEntity()
    {
        super(new Vector2(200,200),new SpritesheetRenderer("player",10,1,new SpriteAnimation[]{
                new SpriteAnimation(0,0,24,true), // idle front,
                new SpriteAnimation(1,1,24,true), // idle back
                new SpriteAnimation(2,6,12,true), // walk front
                new SpriteAnimation(6,10,12,true) // walk back
        }));
        spritesheet = (SpritesheetRenderer)getRenderer();
        friction = 1200;
    }
    boolean faceBack = false;
    public void tick(float dt) {
        super.tick(dt);
        spritesheet.tick(dt);
        Vector2 v = new Vector2();
        if(GameWindow.getPressedKey(Keybinds.KEY_LEFT)) v.setX(-1);
        if(GameWindow.getPressedKey(Keybinds.KEY_RIGHT)) v.setX(1);
        if(GameWindow.getPressedKey(Keybinds.KEY_UP)) v.setY(-1);
        if(GameWindow.getPressedKey(Keybinds.KEY_DOWN)) v.setY(1);
        if(!v.isZero()) setVelocity(v.normalized().multiplied(speed));

        if(v.getX() != 0) spritesheet.setFlipped(v.getX() < 0);
        if(v.getY() < 0) faceBack = true;
        else if (!v.isZero()) faceBack = false;
        spritesheet.playAnimation((v.isZero() ? 0 : 2) + (faceBack ? 1 : 0));

        GameWindow.viewportPosition.lerp(position.subtracted(new Vector2((float)Game.window.getPixelWidth()/2.0f,(float)Game.window.getPixelHeight()/2.0f)),5.0f*dt);

    }
}
