public class PlayerInventory {
    public final static int slotCount = 5;
    public InventoryItem[] items;
    public PlayerInventory()
    {
        items = new InventoryItem[slotCount];
    }
    public boolean give(InventoryItem item)
    {
        for (int i = 0; i < slotCount; i++)
        {
            if(items[i] == null)
            {
                items[i] = item;
                return true;
            }
        }
        return false;
    }
    public InventoryItem swapItem(int slot, InventoryItem item)
    {
        if(slot < 0 || slot >= slotCount) return null;
        InventoryItem temp = items[slot];
        items[slot] = item;
        return temp;
    }
}
