import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class LivingEntity extends EntityBase{
    public static final BufferedImage shadow = AssetLoader.getSprite("player_shadow");
    EntityAttributes attributes;
    float currentHP;
    int iFrames;
    public static final int invincibilityFrames = 8;
    public LivingEntity(Vector2 position, Vector2 size, GameRenderer r,EntityAttributes attributes)
    {
        super(position,size,r);
        this.attributes = attributes;
        currentHP = attributes.getMaxHP();
    }

    public void render(Graphics2D g2d) {
        Vector2 p = position.subtracted(GameWindow.getViewportPosition()).added(0,getCollider().getSize().getY()-2);
        g2d.drawImage(shadow,(int)p.getX(),(int)p.getY(),null);
        super.render(g2d);
    }

    public void checkEnemyMelee(float damage, float knockback)
    {
        PlayerEntity player = Game.getWindow().getPlayer();
        boolean touchingPlayer = player.getCollider().isColliding(getCollider());
        if(touchingPlayer)
        {
            player.damage(damage);
            player.knockback(player.getPosition().subtracted(getPosition()).normalized().multiplied(knockback));
        }
    }

    public int getiFrames() {
        return iFrames;
    }

    public void tick(float dt)
    {
        super.tick(dt);
        if (currentHP <= 0)
        {
            kill();
        }
        iFrames--;
        iFrames = Math.max(iFrames,0);
    }

    public EntityAttributes getAttributes() {
        return attributes;
    }

    public float getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(float currentHP) {
        this.currentHP = currentHP;
    }

    public void damage(float hp)
    {
        if(GameWindow.isGameOver()) return;
        if(iFrames != 0) return;
        this.currentHP -= hp;
        iFrames = invincibilityFrames;
    }

    public void knockback(Vector2 kb)
    {
        setVelocity(getVelocity().added(kb));
    }
}
