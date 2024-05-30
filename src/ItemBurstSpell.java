public class ItemBurstSpell extends InventoryItem{
    public ItemBurstSpell()
    {
        super("Burst Shot", new SpriteRenderer("burst_shot"));
        setCooldown(1f);
        continuousUse = false;
    }
    public float getDamage()
    {
        return 24*(float)Math.pow(1.3f,getTier());
    }
    public void useItem(PlayerEntity player) {
        for (int i = 0; i < 5; i++) {
            BurstProjectile b = new BurstProjectile(player.getPosition().copy(),player.getPointingAngle()+ (float)(Math.random()*1.2-0.6));
            b.setDamage(getDamage());
            b.setKnockback(10);
            GameWindow.queueSpawn(b);
        }

    }
}
