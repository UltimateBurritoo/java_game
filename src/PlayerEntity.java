import java.security.Key;

public class PlayerEntity extends LivingEntity{

    public static final float invincibilityTime = 0.5f;
    float itemCooldown;
    PlayerInventory inventory;
    int coins;
    public PlayerEntity()
    {
        super(new Vector2(160,160),new Vector2(12,12),new SpritesheetRenderer("player",10,1,new SpriteAnimation[]{
                new SpriteAnimation(0,0,24,true), // idle front,
                new SpriteAnimation(1,1,24,true), // idle back
                new SpriteAnimation(2,6,12,true), // walk front
                new SpriteAnimation(6,10,12,true) // walk back
        }),new EntityAttributes(100,175,20));
        inventory = new PlayerInventory();
        inventory.give(new ItemBurstSpell());
        hideHealth = true;
    }
    public PlayerInventory getInventory()
    {
        return inventory;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    boolean faceBack = false;
    public void tick(float dt) {
        super.tick(dt);
        itemCooldown -= dt;
        itemCooldown = Math.max(itemCooldown,0);
        // basic movement
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
        // inventory/items
        if(inventory.getHeldItem() != null && inventory.getHeldItem().continuousUse && Game.getWindow().getClicked()) click(1,Game.getWindow().getMousePos());
        if(GameWindow.getPressedKey(Keybinds.KEY_1)) inventory.setSelectedSlot(0);
        else if(GameWindow.getPressedKey(Keybinds.KEY_2)) inventory.setSelectedSlot(1);
        else if(GameWindow.getPressedKey(Keybinds.KEY_3)) inventory.setSelectedSlot(2);
        else if(GameWindow.getPressedKey(Keybinds.KEY_4)) inventory.setSelectedSlot(3);
        else if(GameWindow.getPressedKey(Keybinds.KEY_5)) inventory.setSelectedSlot(4);
        // drop item
        if(GameWindow.getPressedKey(Keybinds.KEY_Q) && inventory.getHeldItem() != null)
        {
            inventory.dropSlot(inventory.getSelectedSlot());
        }


        GameWindow.setLightPosition(position);
        GameWindow.viewportPosition.lerp(position.subtracted(new Vector2((float)Game.window.getPixelWidth()/2.0f,(float)Game.window.getPixelHeight()/2.0f)),5.0f*dt);

    }
    public void damage(float hp)
    {
        super.damage(hp);
        iFrames = invincibilityTime;
    }
    public float getPointingAngle()
    {
        Vector2 direction = (GameWindow.getViewportPosition().added(Game.getWindow().getMousePos())).subtracted(getPosition().added(8,24)).normalized();
        float angle = (float)Math.acos(direction.dotProduct(new Vector2(1,0))) * Math.signum(direction.getY());
        return angle;
    }
    public void click(int button, Vector2 position)
    {
        if(button == 1)
        {
            if(inventory.getHeldItem() != null && itemCooldown == 0)
            {
                inventory.getHeldItem().useItem(this);
                itemCooldown = inventory.getHeldItem().getCooldown();
            }
        }
    }
    public void kill()
    {
        // makes the game stop rather than deleting the player
        GameWindow.gameOver = true;
    }
}
