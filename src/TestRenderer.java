import java.awt.*;

public class TestRenderer extends GameRenderer {

    Color color;
    int width;
    int height;
    public TestRenderer(int rgb,int w,int h)
    {
        color = new Color(rgb);
        width = w;
        height = h;
    }

    public void render(int x, int y, Graphics2D g) {
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }
}
