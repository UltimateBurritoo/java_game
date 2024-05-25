import java.awt.*;

public class UIElement extends GameRenderer {
    boolean visible = true;
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public void render(int x, int y, Graphics2D g)
    {
        return;
    }
}
