public class ItemHealthPotion extends InventoryItem{
    int uses;
    public ItemHealthPotion()
    {
        super("Health Potion",new SpriteRenderer("healing_potion"));
        uses = 3;
    }

    public int getUses() {
        return uses;
    }
    public float getDamage()
    {
        return 25*(float)Math.pow(1.5f,getTier());
    }
    public void useItem(PlayerEntity player) {
        player.heal(getDamage());
        uses--;
        if(uses <= 0)
        {
            player.inventory.consumeHeldItem();
        }
    }
    public String toString()
    {
        return super.toString() + " (" + uses + " Uses)";
    }
}
