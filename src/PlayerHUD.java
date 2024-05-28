import java.awt.*;

public class PlayerHUD extends UIElement{
    PlayerEntity player;
    float healthShown;
    public PlayerHUD(PlayerEntity p)
    {
        player = p;
        healthShown = player.getCurrentHP();
    }
    public void render(int x, int y, Graphics2D g) {
        if(!isVisible() || GameWindow.isGameOver()) return;

        // health bar
        g.drawImage(AssetLoader.getSprite("healthbar_empty"),4,16,null);
        healthShown = (player.getCurrentHP() - healthShown) * 0.3f + healthShown;
        g.setClip(4,16,(int)(96 * (healthShown / player.attributes.getMaxHP())),8);
        g.drawImage(AssetLoader.getSprite("healthbar_full"),4,16,null);
        g.setClip(0,0,GameWindow.pixelWidth,GameWindow.pixelHeight);
        g.setColor(Color.white);
        g.drawString("HP: " + (int)player.getCurrentHP() + " / " + (int)player.getAttributes().getMaxHP(),4,36);

        PlayerInventory inventory = player.getInventory();
        // inventory
        for(int i = 0; i < PlayerInventory.slotCount; i++)
        {
            if(i==inventory.getSelectedSlot())
            {
                g.drawImage(AssetLoader.getSprite("selected_inventory_slot"),5+i*18,48,null);
            }
            else
            {
                g.drawImage(AssetLoader.getSprite("inventory_slot"),5+i*18,48,null);
            }
            InventoryItem item = inventory.getItem(i);
            if(item != null) {
                item.renderer.render(5 + i * 18, 48, g);
            }
        }
        g.setColor(Color.WHITE);
        if(inventory.getHeldItem() != null)
        {
            g.drawString(inventory.getHeldItem().toString(),5,80);
        }
        g.setColor(new Color(1,0,0,0.3f*((float)player.getiFrames() / PlayerEntity.invincibilityTime)));
        g.fillRect(0,0,GameWindow.pixelWidth,GameWindow.pixelHeight);
    }
}
