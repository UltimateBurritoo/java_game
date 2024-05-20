import java.awt.*;

public abstract class GameRenderer {
    public final void render(Vector2 pos, Graphics2D g)
    {
        render((int)pos.getX(),(int)pos.getY(), g);
    }
    public abstract void render(int x, int y, Graphics2D g);
    public static final float lightDistance = 400;
    public static float calculateLighting(Vector2 position)
    {
        return 1.0f-Math.clamp(position.distance(GameWindow.getLightPosition())/lightDistance,0.0f,1.0f);
    }
}
