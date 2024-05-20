import java.awt.*;

public class AABB {
    Vector2 position;
    Vector2 size;
    public AABB(Vector2 position, Vector2 size)
    {
        this.position = position;
        this.size = size;
    }
    public AABB(float x, float y, float w, float h)
    {
        this.position = new Vector2(x,y);
        this.size = new Vector2(w,y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Vector2 getMax()
    {
        return position.added(size);
    }
    public AABB moved(Vector2 offset)
    {
        return new AABB(position.added(offset),size);
    }

    public boolean isColliding(AABB other)
    {
        return position.getX() < other.getMax().getX() && getMax().getX() > other.getPosition().getX() && position.getY() < other.getMax().getY() && getMax().getY() > other.position.getY();
    }
    public boolean collideTilemap(Tilemap t)
    {
        return (
                t.checkCollision(position) ||
                t.checkCollision(position.added(getSize().getX(),0)) ||
                t.checkCollision(position.added(0,getSize().getY())) ||
                t.checkCollision(getMax())
                );
    }
    public void debugDraw(int x, int y, Graphics2D g)
    {
        g.setColor(Color.red);
        g.drawRect((int)position.getX() + x,(int)position.getY() + y,(int)size.x,(int)size.y);
    }
}
