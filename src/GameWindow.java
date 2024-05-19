import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.*;
import java.util.ArrayList;

public class GameWindow extends Frame implements KeyListener, Runnable {
    public static final int pixelScale = 2;
    public static final int topPadding = 30;
    static boolean[] pressedKeys = new boolean[256];
    static Vector2 viewportPosition = new Vector2();
    TestRenderer t;
    int n = 0;
    BufferedImage canvasImage;
    Graphics2D g2d;
    Tilemap tilemap;

    ArrayList<EntityBase> activeEntities = new ArrayList<EntityBase>();

    public GameWindow()
    {
        setVisible(true);
        setSize(800, 600);
        setTitle("game");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        addKeyListener(this);
        canvasImage = new BufferedImage(getWidth()/pixelScale,(getHeight()-topPadding)/pixelScale,BufferedImage.TYPE_INT_ARGB);
        g2d = canvasImage.createGraphics();

        t = new TestRenderer(0xff0000);
        tilemap = new Tilemap(20,20);
        for(int x = 0; x < 20; x++)
        {
            for(int y = 0; y < 20; y++)
            {
                tilemap.setTile(x,y,(int)(Math.random()*2));
            }
        }

        activeEntities.add(new PlayerEntity());

    }
    public int getPixelWidth()
    {
        return getWidth() / pixelScale;
    }
    public int getPixelHeight()
    {
        return getHeight() / pixelScale;
    }

    public static boolean getPressedKey(int index)
    {
        return pressedKeys[index];
    }

    public static Vector2 getViewportPosition() {
        return viewportPosition;
    }

    public static void setViewportPosition(Vector2 viewportPosition) {
        GameWindow.viewportPosition = viewportPosition;
    }

    // we don't care about this method
    public void keyTyped(KeyEvent e)
    {
        return;
    }

    public void keyPressed(KeyEvent e)
    {
        pressedKeys[e.getKeyCode()] = true;
        //System.out.println(e.getKeyCode() + " down");
    }

    public void keyReleased(KeyEvent e)
    {
        pressedKeys[e.getKeyCode()] = false;
        //System.out.println(e.getKeyCode() + " up");
    }

    // TODO add spawn entity method and spawn player

    long previousTime = System.currentTimeMillis();
    int fps;
    // main loop
    public void run()
    {
        System.out.println("main thread started");
        while (true) {
            try {
                Thread.sleep(1);
                long msDeltaTime = System.currentTimeMillis() - previousTime;
                float deltaTime = (float)msDeltaTime / 1000;
                fps = (int)(1 / deltaTime);
                previousTime = System.currentTimeMillis();
                tick(deltaTime);
                repaint(getGraphics());

                // put main loop stuff here
            } catch (InterruptedException e){
            }
        }
    }

    public void repaint(Graphics g)
    {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        // draw to buffer (g2d)
        g2d.clearRect(0,0,getPixelWidth(),getPixelHeight());
        tilemap.render((int)-viewportPosition.getX(),(int)-viewportPosition.getY(),g2d);
        for(EntityBase entity : activeEntities)
        {
            entity.render(g2d);
        }
        g2d.setColor(Color.white);
        g2d.drawString(fps + " fps", 2,10);

        g.drawImage(canvasImage,0,topPadding,getWidth(),getHeight()-topPadding,null);

    }

    public void tick(float dt)
    {
        for (EntityBase entity : activeEntities)
        {
            entity.tick(dt);
        }
    }
}
