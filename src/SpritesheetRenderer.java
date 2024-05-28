import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SpritesheetRenderer extends GameRenderer{
    int width;
    int height;
    int frameHeight;
    int frameWidth;
    BufferedImage image;

    float currentTime;
    int animationIndex;
    boolean flipped;
    boolean centered;
    SpriteAnimation[] animations;

    public SpritesheetRenderer(String sprite,int width, int height, SpriteAnimation[] animations)
    {
        image = AssetLoader.getSprite(sprite);
        this.width = width;
        this.height = height;
        frameHeight = image.getWidth() / width;
        frameWidth = image.getHeight() / height;
        this.animations = animations;
    }

    public boolean isFlipped() {
        return flipped;
    }
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }
    public void render(int x, int y, Graphics2D g) {
        int frameToRender = (int)((currentAnimation().endFrame - currentAnimation().startFrame) * (currentTime / currentAnimation().getTime()) + currentAnimation().startFrame);
        int fx = frameToRender % width;
        int fy = frameToRender / width;
        g.translate(x,y);
        if(isFlipped())
        {
            g.translate(frameWidth,0);
            g.scale(-1,1);
        }
        if(centered) g.translate(-frameWidth/2,frameHeight/2);
        g.drawImage(image,0,0,frameWidth-1,frameHeight-1,fx*frameWidth,fy*frameHeight,(fx+1)*frameWidth-1,(fy+1)*frameHeight-1,null);
        g.setTransform(new AffineTransform());
    }
    public void playAnimation(int index)
    {
        if(index == animationIndex) return;
        animationIndex = index;
        currentTime = 0;
    }
    public SpriteAnimation currentAnimation()
    {
        try
        {
            return animations[animationIndex];
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }
    public void tick(float dt)
    {
        currentTime += dt;
        if(currentAnimation().isLooping())
        {
            if(currentTime >= currentAnimation().getTime())
            {
                currentTime = 0;
            }
        }
        else
        {
            if(currentTime >= currentAnimation().getTime())
            {
                currentTime = currentAnimation().getTime();
            }
        }
    }
}
