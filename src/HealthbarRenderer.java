import java.awt.*;

public class HealthbarRenderer extends GameRenderer{
    LivingEntity entity;
    int width;
    public static final int height = 4;

    public HealthbarRenderer(LivingEntity entity, int width) {
        this.entity = entity;
        this.width = width;
    }

    public void render(int x, int y, Graphics2D g) {
        g.setColor(new Color(0x5b2000));
        g.fillRect(x,y-5,width,height);
        g.setColor(Color.red);
        g.fillRect(x,y-5,(int)(width*(entity.getCurrentHP()/entity.getAttributes().getMaxHP())),height);
    }
}
