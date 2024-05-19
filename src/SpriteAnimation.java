public class SpriteAnimation {
    int startFrame;
    int endFrame;
    float fps;
    boolean looping;

    public SpriteAnimation(int startFrame, int endFrame, float fps, boolean looping) {
        if(startFrame >= 0) this.startFrame = startFrame;
        else this.startFrame = 0; // 0 = beginning
        if(endFrame > startFrame) this.endFrame = endFrame;
        else this.endFrame = startFrame;
        this.fps = fps;
        this.looping = looping;
    }

    public int getStartFrame() {
        return startFrame;
    }

    public int getEndFrame() {
        return endFrame;
    }

    public float getFps() {
        return fps;
    }

    public boolean isLooping() {
        return looping;
    }

    public int getLength()
    {
        return endFrame-startFrame+1;
    }

    public float getTime()
    {
        return getLength() / getFps();
    }
}
