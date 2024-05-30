import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.*;
import java.util.ArrayList;

public class GameWindow extends Frame implements KeyListener, MouseListener, Runnable {
    public static final int pixelWidth = 640;
    public static final int pixelHeight = 360;
    public static final int pixelScale = 2;
    public static final int topPadding = 30;
    static boolean gameOver;
    static boolean[] pressedKeys = new boolean[256];
    static Vector2 viewportPosition = new Vector2();
    static Vector2 lightPosition = new Vector2();
    BufferedImage canvasImage;
    Graphics2D g2d;
    Tilemap tilemap;
    boolean isClicked;
    static int currentLevel;

    public static ArrayList<UIElement> uiElements = new ArrayList<UIElement>();

    public static ArrayList<EntityBase> activeEntities = new ArrayList<EntityBase>();
    static float screenShake;
    static ArrayList<EntityBase> killQueue = new ArrayList<EntityBase>();
    static ArrayList<EntityBase> spawnQueue = new ArrayList<EntityBase>();
    PlayerEntity player;
    public GameWindow()
    {
        setVisible(true);
        setSize(1280, 720);
        setTitle("game");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        addKeyListener(this);
        addMouseListener(this);
        canvasImage = new BufferedImage(getWidth()/pixelScale,(getHeight()-topPadding)/pixelScale,BufferedImage.TYPE_INT_ARGB);
        g2d = canvasImage.createGraphics();
        tilemap = new Tilemap(100,100);
        DungeonBuilder b = new DungeonBuilder();
        b.build(tilemap);
        player = new PlayerEntity();
        activeEntities.add(player);
        uiElements.add(new PlayerHUD(player));
        uiElements.add(new GameOverScreen());

    }
    public int getPixelWidth()
    {
        return getWidth() / pixelScale;
    }
    public int getPixelHeight()
    {
        return getHeight() / pixelScale;
    }
    public PlayerEntity getPlayer()
    {
        return player;
    }
    public static Vector2 getLightPosition() {
        return lightPosition;
    }

    public static void setLightPosition(Vector2 lightPosition) {
        GameWindow.lightPosition = lightPosition;
    }

    public static boolean isGameOver() {
        return gameOver;
    }

    public static void setGameOver(boolean gameOver) {
        GameWindow.gameOver = gameOver;
    }

    public static boolean getPressedKey(int index)
    {
        return pressedKeys[index];
    }

    public static Vector2 getViewportPosition() {
        return viewportPosition.added(new Vector2((float)Math.random()*screenShake,(float)Math.random()*screenShake));
    }

    public static void setViewportPosition(Vector2 viewportPosition) {
        GameWindow.viewportPosition = viewportPosition;
    }

    public Tilemap getTilemap()
    {
        return tilemap;
    }

    public boolean getClicked()
    {
        return isClicked;
    }

    Vector2 lastMousePos = new Vector2();
    public Vector2 getMousePos()
    {
        if (getMousePosition() == null) {
            return lastMousePos;
        }
        else if (lastMousePos == null) return new Vector2();
        lastMousePos = new Vector2((float)getMousePosition().getX()/pixelScale,(float)getMousePosition().getY()/pixelScale);
        return lastMousePos;
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
    public void mouseClicked(MouseEvent e)
    {return;}
    public void mousePressed(MouseEvent e)
    {
        getPlayer().click(e.getButton(),getViewportPosition().added(e.getX()/pixelScale,e.getY()/pixelScale));
        if(e.getButton() == 1) isClicked = true;
    }
    public void mouseReleased(MouseEvent e)
    {if(e.getButton() == 1) isClicked = false;}
    public void mouseEntered(MouseEvent e)
    {return;}
    public void mouseExited(MouseEvent e)
    {isClicked = false;}
    public static void queueKill(EntityBase e)
    {
        killQueue.add(e);
    }
    public static void queueSpawn(EntityBase e)
    {
        spawnQueue.add(e);
    }
    static boolean skipFrame = false;
    public static void nextLevel()
    {
        skipFrame = true;
        for (EntityBase entity : activeEntities)
        {
            if(!(entity instanceof PlayerEntity)) entity.kill();
        }
        Game.getWindow().getPlayer().setPosition(new Vector2(160,160));
        Game.getWindow().getTilemap().clear();
        DungeonBuilder b = new DungeonBuilder();
        b.build(Game.getWindow().getTilemap());
        currentLevel++;
    }

    public static float getScreenShake() {
        return screenShake;
    }
    public static void addScreenShake(float amount)
    {
        screenShake+=amount;
    }

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
        tilemap.render((int)-getViewportPosition().getX(),(int)-getViewportPosition().getY(),g2d);
        for(EntityBase entity : activeEntities)
        {
            entity.render(g2d);
        }
        for(UIElement element : uiElements)
        {
            element.render(0,0,g2d);
        }
        g2d.setColor(Color.white);
        g2d.drawString(fps + " fps", 2,10);

        g.drawImage(canvasImage,0,topPadding,getWidth(),getHeight()-topPadding,null);

    }

    public void tick(float dt)
    {
        screenShake = (-screenShake)*(dt*7)+screenShake;
        if(!isGameOver())
        {
            for (EntityBase entity : activeEntities)
            {
                entity.tick(dt);
            }
        }
        for (EntityBase toKill : killQueue)
        {
            if(activeEntities.contains(toKill))
            {
                activeEntities.remove(toKill);
            }
        }
        killQueue.clear();
        for (EntityBase toSpawn : spawnQueue)
        {
            activeEntities.add(toSpawn);
        }
        spawnQueue.clear();

    }
}
