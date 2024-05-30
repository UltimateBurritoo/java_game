public class ItemHealthPotion extends InventoryItem{
    public ItemHealthPotion()
    {
        super("Health Potion",new SpriteRenderer("healing_potion"));
    }
    public float getDamage()
    {
        return 50*(float)Math.pow(1.2f,getTier());
    }
    public void useItem(PlayerEntity player) {
        player.heal(getDamage());
    }
}
