public abstract class InventoryItem {
    GameRenderer renderer;
    String displayName;
    public InventoryItem(String displayName, GameRenderer renderer)
    {
        this.displayName = displayName;
        this.renderer = renderer;
    }
    public void useItem(EntityBase e){return;}
    public GameRenderer getRenderer() {
        return renderer;
    }

    public String getDisplayName() {
        return displayName;
    }
}
