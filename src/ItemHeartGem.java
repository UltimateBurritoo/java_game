public class ItemHeartGem extends InventoryItem{
    public ItemHeartGem()
    {
        super("Heart Gem",new SpriteRenderer("heart_gem"));
    }
    public void useItem(PlayerEntity player) {
        float percentage = player.getCurrentHP() / player.getAttributes().getMaxHP();
        // retain last health percentage
        player.getAttributes().setMaxHP(player.getAttributes().getMaxHP() * 1.25f);
        player.setCurrentHP(player.getAttributes().getMaxHP() * percentage);
        player.getInventory().consumeHeldItem();
    }
    public String toString()
    {
        return getDisplayName();
    }
}
