import java.awt.*;

public class DroppedItem extends EntityBase{
    InventoryItem item;
    public DroppedItem(Vector2 position, InventoryItem item)
    {
        super(position, new Vector2(16,16), item.getRenderer());
        this.item = item;
    }
    boolean inRange()
    {
        return Game.getWindow().getPlayer().getPosition().distance(getPosition()) < 32;
    }

    public void render(Graphics2D g2d) {
        super.render(g2d);
        if(inRange())
        {
            if(Game.getWindow().getPlayer().getInventory().isFull())
            {
                g2d.setColor(Color.RED);
                g2d.drawString("Inventory full", GameWindow.pixelWidth/2,GameWindow.pixelHeight/2);
            }
            else
            {
                g2d.setColor(Color.WHITE);
                g2d.drawString("[E] Take "+item.toString(), GameWindow.pixelWidth/2,GameWindow.pixelHeight/2);
            }
        }
    }

    public void tick(float dt)
    {
        super.tick(dt);
        if(GameWindow.pressedKeys[Keybinds.KEY_E] && inRange() && !Game.getWindow().getPlayer().getInventory().isFull())
        {
            if(Game.getWindow().getPlayer().getInventory().give(item)) kill();
        }
        if(item == null) kill();
    }
}
