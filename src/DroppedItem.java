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
        return Game.getWindow().getPlayer().getPosition().distance(getPosition()) < 48;
    }

    public void render(Graphics2D g2d) {
        super.render(g2d);
        if(inRange())
        {
            g2d.setColor(Color.WHITE);
            g2d.drawString("[E] Pick up item", GameWindow.pixelWidth/2,GameWindow.pixelHeight/2);
        }
    }

    public void tick(float dt)
    {
        super.tick(dt);
        if(item == null) kill();
    }
}
