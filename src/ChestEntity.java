import java.awt.*;

public class ChestEntity extends EntityBase{
    InventoryItem item;
    public ChestEntity(Vector2 position)
    {
        super(position,new SpriteRenderer("chest"));
        item = getRandomLoot();
    }
    public static InventoryItem getRandomLoot()
    {
        int itemIndex = (int)(Math.random() * 4);
        InventoryItem result;
        switch (itemIndex)
        {
            case 0: result = new ItemBulletSpell();
            case 1: result = new ItemFlamethrowerSpell();
            case 2: result = new ItemBurstSpell();
            case 3: result = new ItemHealthPotion();
            default: result = new ItemBulletSpell();
        }
        if (result != null) {
            result.setTier((int)Math.round(GameWindow.currentLevel + 1 + Math.random()*0.7f));
        }
        return null;
    }
    public void open()
    {
        GameWindow.queueSpawn(new PoofEffect(getPosition()));
        GameWindow.queueSpawn(new DroppedItem(getPosition(),item));
        System.out.println("killed");
        kill();
    }
    boolean inRange()
    {
        return Game.getWindow().getPlayer().getPosition().distance(getPosition()) < 32;
    }
    boolean isSafe()
    {
        for(EntityBase e : GameWindow.activeEntities)
        {
            if(!(e instanceof LivingEntity) || e instanceof PlayerEntity) continue;
            if(e.getPosition().distance(getPosition()) < 128f)
            {
                return false;
            }
        }
        return true;
    }

    public void render(Graphics2D g2d) {
        super.render(g2d);
        if(inRange())
        {
            if(isSafe())
            {
                g2d.setColor(Color.WHITE);
                g2d.drawString("[F] Open chest", GameWindow.pixelWidth/2,GameWindow.pixelHeight/2);
            }
            else {
                g2d.setColor(Color.RED);
                g2d.drawString("Enemies nearby, cannot open", GameWindow.pixelWidth/2,GameWindow.pixelHeight/2);

            }

        }
    }

    public void tick(float dt)
    {
        super.tick(dt);
        if(GameWindow.pressedKeys[Keybinds.KEY_F] && inRange() && isSafe()) open();
        if(item == null) kill();
    }
}
