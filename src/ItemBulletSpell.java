public class ItemBulletSpell extends InventoryItem{
    public ItemBulletSpell()
    {
        super("Magic Bullet", new SpriteRenderer("magic_bullet"));
        setCooldown(0.25f);
        continuousUse = true;
    }
    public float getDamage()
    {
        return 16*(float)Math.pow(1.2f,getTier());
    }
    public void useItem(PlayerEntity player) {
        BulletProjectile b = new BulletProjectile(player.getPosition().copy(),player.getPointingAngle());
        b.setDamage(getDamage());
        b.setKnockback(100);
        GameWindow.queueSpawn(b);
    }
}
