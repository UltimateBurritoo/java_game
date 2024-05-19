import java.awt.*;

public abstract class GameRenderer {
    public final void render(Vector2 pos, Graphics2D g)
    {
        render((int)pos.getX(),(int)pos.getY(), g);
    }
    public abstract void render(int x, int y, Graphics2D g);
}
