import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteRenderer extends GameRenderer {
    BufferedImage image;
    int width = 16;
    int height = 16;
    public SpriteRenderer(String sprite)
    {
        image = AssetLoader.getSprite(sprite);
        width = image.getWidth();
        height = image.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void render(int x, int y, Graphics2D g) {
        if(image == null)
        {
            g.setColor(Color.MAGENTA);
            g.fillRect(x,y,width,height);
        }
        else
        {
            g.drawImage(image,x,y,width,height,null);
        }
    }
}
