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
        g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,12));
        // health bar
        g.drawImage(AssetLoader.getSprite("healthbar_empty"),4,16,null);
        healthShown = (player.getCurrentHP() - healthShown) * 0.3f + healthShown;
        g.setClip(4,16,(int)(96 * (healthShown / player.attributes.getMaxHP())),8);
        g.drawImage(AssetLoader.getSprite("healthbar_full"),4,16,null);
        g.setClip(0,0,GameWindow.pixelWidth,GameWindow.pixelHeight);
        g.setColor(Color.white);
        g.drawString("HP: " + (int)player.getCurrentHP() + " / " + (int)player.getAttributes().getMaxHP(),4,36);
        // cooldown bar
        if (player.getInventory().getHeldItem() != null)
        {
            float percentage = player.getItemCooldown() / player.getInventory().getHeldItem().getCooldown();
            if (percentage > 0)
            {
                g.setColor(Color.gray);
                g.fillRect(4,40,100,2);
                g.setColor(Color.white);
                g.fillRect(4,40,(int)(100 * Math.clamp(percentage,0f,1f)),2);
            }
        }

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
            g.drawString("[Q] to drop",5,96);
        }
        g.drawString("Floor " + (GameWindow.currentLevel+1), 5,GameWindow.pixelHeight-24);
        g.setColor(new Color(1,0,0,0.3f*((float)player.getiFrames() / PlayerEntity.invincibilityTime)));
        g.fillRect(0,0,GameWindow.pixelWidth,GameWindow.pixelHeight);
    }
}
