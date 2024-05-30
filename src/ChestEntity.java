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
        int itemIndex = (int)(Math.random() * 8); // numbers represents total number of entries into loot table
        //System.out.println(itemIndex);
        InventoryItem result;
        switch (itemIndex)
        {
            // double entries are common items because too lazy to implement weight system again
            case 0: {result = new ItemBulletSpell(); break;}
            case 1: {result = new ItemBulletSpell(); break;}
            case 2: {result = new ItemFlamethrowerSpell(); break;}
            case 3: {result = new ItemBurstSpell(); break;}
            case 4: {result = new ItemHealthPotion(); break;}
            case 5: {result = new ItemHealthPotion(); break;}
            case 6: {result = new ItemHeartGem(); break;}
            case 7: {result = new ItemSparkSpell(); break;}
            default: result = new ItemBulletSpell();
        }
        if (result != null) {
            result.setTier((int)Math.round(GameWindow.currentLevel + 1 + Math.random()*0.7f));
            return result;
        }
        return null;
    }
    public void open()
    {
        GameWindow.queueSpawn(new PoofEffect(getPosition()));
        GameWindow.queueSpawn(new DroppedItem(getPosition(),item));
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
