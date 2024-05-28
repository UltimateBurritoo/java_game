public abstract class InventoryItem {
    GameRenderer renderer;
    String displayName;
    int tier=1;
    float cooldown;
    boolean continuousUse;
    public InventoryItem(String displayName, GameRenderer renderer)
    {
        this.displayName = displayName;
        this.renderer = renderer;
    }
    public void setTier(int tier)
    {
        this.tier = tier;
    }
    public int getTier()
    {
        return tier;
    }

    public float getDamage()
    {
        return 0;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public void useItem(PlayerEntity player){return;}
    public GameRenderer getRenderer() {
        return renderer;
    }

    public String getDisplayName() {
        return displayName;
    }


    public String toString()
    {
        return displayName + " - Tier " + tier;
    }
}
