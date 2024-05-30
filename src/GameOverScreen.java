import java.awt.*;

public class GameOverScreen extends UIElement{
    public void render(int x, int y, Graphics2D g) {
        if(!GameWindow.isGameOver()) return;
        g.setColor(new Color(0,0,0,0.5f));
        g.fillRect(0,GameWindow.pixelHeight/2-50,GameWindow.pixelWidth,100);
        g.setColor(Color.RED);
        g.setFont(new Font(Font.SERIF,Font.BOLD,48));
        g.drawString("YOU DIED",GameWindow.pixelWidth/2-100,GameWindow.pixelHeight/2+10);
        g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,16));
    }
}
