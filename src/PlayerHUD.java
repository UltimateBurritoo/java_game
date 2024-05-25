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

        // inventory
        for(int i = 0; i < 4; i++)
        {
            g.drawImage(AssetLoader.getSprite("inventory_slot"),5+i*18,48,null);
        }

        g.setColor(new Color(1,0,0,0.3f*((float)player.getiFrames() / LivingEntity.invincibilityFrames)));
        g.fillRect(0,0,GameWindow.pixelWidth,GameWindow.pixelHeight);
    }
}
