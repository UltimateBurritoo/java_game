public class PlayerEntity extends LivingEntity{

    public static final float invinciblityTime = 0.5f;
    public PlayerEntity()
    {
        super(new Vector2(160,160),new Vector2(12,12),new SpritesheetRenderer("player",10,1,new SpriteAnimation[]{
                new SpriteAnimation(0,0,24,true), // idle front,
                new SpriteAnimation(1,1,24,true), // idle back
                new SpriteAnimation(2,6,12,true), // walk front
                new SpriteAnimation(6,10,12,true) // walk back
        }),new EntityAttributes(100,175,20));
    }
    boolean faceBack = false;
    public void tick(float dt) {
        super.tick(dt);
        Vector2 v = new Vector2();
        if(GameWindow.getPressedKey(Keybinds.KEY_A)) v.setX(-1);
        if(GameWindow.getPressedKey(Keybinds.KEY_D)) v.setX(1);
        if(GameWindow.getPressedKey(Keybinds.KEY_W)) v.setY(-1);
        if(GameWindow.getPressedKey(Keybinds.KEY_S)) v.setY(1);
        if(!v.isZero()) setVelocity(v.normalized().multiplied(getAttributes().getSpeed()));

        if(v.getX() != 0) spritesheet.setFlipped(v.getX() < 0);
        if(v.getY() < 0) faceBack = true;
        else if (!v.isZero()) faceBack = false;
        spritesheet.playAnimation((v.isZero() ? 0 : 2) + (faceBack ? 1 : 0));

        GameWindow.setLightPosition(position);
        GameWindow.viewportPosition.lerp(position.subtracted(new Vector2((float)Game.window.getPixelWidth()/2.0f,(float)Game.window.getPixelHeight()/2.0f)),5.0f*dt);

    }
    public void damage(float hp)
    {
        super.damage(hp);
        iFrames = invinciblityTime;
    }
    public void click(int button, Vector2 position)
    {
        if(button == 1)
        {
            System.out.println(position);
        }
    }
    public void kill()
    {
        // when you die you are dead
        GameWindow.gameOver = true;
    }
}
