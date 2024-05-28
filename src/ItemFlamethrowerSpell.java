public class ItemFlamethrowerSpell extends InventoryItem{
    public ItemFlamethrowerSpell()
    {
        super("Flamethrower",new SpriteRenderer("flamethrower"));
        setCooldown(0.05f);
        continuousUse = true;
    }
    public float getDamage()
    {
        return 5*(float)Math.pow(1.1f,getTier());
    }
    public void useItem(PlayerEntity player) {
        FlamethrowerProjectile b = new FlamethrowerProjectile(player.getPosition().copy(),player.getPointingAngle() + (float)(Math.random()*0.5-0.25));
        b.setDamage(getDamage());
        b.setKnockback(10);
        GameWindow.queueSpawn(b);
    }
}
