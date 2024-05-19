import java.awt.*;

public class TestRenderer extends GameRenderer {

    Color color;
    public TestRenderer(int rgb)
    {
        color = new Color(rgb);
    }

    public void render(int x, int y, Graphics2D g) {
        g.setColor(color);
        g.fillRect(x,y,16,16);
    }
}
