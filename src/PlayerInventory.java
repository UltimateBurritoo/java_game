public class PlayerInventory {
    public final static int slotCount = 5;
    InventoryItem[] items;
    int selectedSlot;
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

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public InventoryItem getItem(int index)
    {
        return items[index];
    }

    public InventoryItem getHeldItem()
    {
        return items[selectedSlot];
    }

    public void dropSlot(int slot)
    {
        GameWindow.queueSpawn(new DroppedItem(Game.getWindow().getPlayer().getPosition().copy(),getItem(slot)));
        items[slot] = null;
    }
    public boolean isFull()
    {
        for (int i = 0; i < slotCount; i++)
        {
            if(items[i] == null)
            {
                return false;
            }
        }
        return true;
    }
    public void consumeHeldItem()
    {
        items[selectedSlot] = null;
    }
}
