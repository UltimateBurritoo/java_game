public class ItemBulletSpell extends InventoryItem{
    public ItemBulletSpell()
    {
        super("Magic Bullet", new SpriteRenderer("magic_bullet"));
        continuousUse = true;
    }
    public float getDamage()
    {
        return 16*(float)Math.pow(1.1f,getTier());
    }
    public void useItem(PlayerEntity player) {
        setCooldown(0.25f*(float)Math.pow(0.8,getTier()-1));

        BulletProjectile b = new BulletProjectile(player.getPosition().copy(),player.getPointingAngle());
        b.setDamage(getDamage());
        b.setKnockback(100);
        GameWindow.queueSpawn(b);
    }
}
