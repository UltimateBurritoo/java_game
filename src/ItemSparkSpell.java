public class ItemSparkSpell extends InventoryItem{
    public ItemSparkSpell()
    {
        super("Electric Spark", new SpriteRenderer("electric_spark"));
        setCooldown(1f);
    }
    public float getDamage()
    {
        return 12*(float)Math.pow(1.25f,getTier());
    }
    public void useItem(PlayerEntity player) {
        for (int i = 0; i < 3; i++) {
            SparkProjectile b = new SparkProjectile(player.getPosition().copy(),player.getPointingAngle()+ (float)(i-1)*0.45f);
            b.setDamage(getDamage());
            b.setKnockback(100);
            GameWindow.queueSpawn(b);
        }

    }
}
