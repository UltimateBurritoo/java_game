public class Vector2 {

    float x;
    float y;
    public  Vector2()
    {
        x = 0;
        y = 0;
    }
    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2 copy()
    {
        return new Vector2(x,y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void add(float x, float y)
    {
        this.x += x;
        this.y += y;
    }

    public void add(Vector2 v)
    {
        this.x += v.getX();
        this.y += v.getY();
    }

    public Vector2 added(float x, float y)
    {
        return new Vector2(this.x+x,this.y+y);
    }

    public Vector2 added(Vector2 v)
    {
        return new Vector2(this.x+v.getX(),this.y+v.getY());
    }

    public void subtract(float x, float y)
    {
        this.x -= x;
        this.y -= y;
    }

    public void subtract(Vector2 v)
    {
        this.x -= v.getX();
        this.y -= v.getY();
    }

    public Vector2 subtracted(float x, float y)
    {
        return new Vector2(this.x-x,this.y-y);
    }

    public Vector2 subtracted(Vector2 v)
    {
        return new Vector2(this.x-v.getX(),this.y-v.getY());
    }

    public void multiply(float x, float y)
    {
        this.x *= x;
        this.y *= y;
    }

    public void multiply(Vector2 v)
    {
        this.x *= v.getX();
        this.y *= v.getY();
    }

    public void multiply(float s)
    {
        this.x *= s;
        this.y *= s;
    }

    public Vector2 multiplied(float x, float y)
    {
        return new Vector2(this.x*x,this.y*y);
    }

    public Vector2 multiplied(Vector2 v)
    {
        return new Vector2(this.x*v.getX(),this.y*v.getY());
    }

    public Vector2 multiplied(float s)
    {
        return new Vector2(x*s,y*s);
    }

    public void divide(float x, float y)
    {
        this.x /= x;
        this.y /= y;
    }

    public void divide(Vector2 v)
    {
        this.x /= v.getX();
        this.y /= v.getY();
    }

    public void divide(float s)
    {
        this.x /= s;
        this.y /= s;
    }

    public Vector2 divided(float x, float y)
    {
        return new Vector2(this.x/x,this.y/y);
    }

    public Vector2 divided(Vector2 v)
    {
        return new Vector2(this.x/v.getX(),this.y/v.getY());
    }

    public Vector2 divided(float s)
    {
        return new Vector2(x/s,y/s);
    }

    public float dotProduct(Vector2 other)
    {
        return this.x * other.getX() + this.y * other.getY();
    }

    public float sqrMagnitude()
    {
        return dotProduct(this);
    }

    public float magnitude()
    {
        return (float)Math.sqrt(sqrMagnitude());
    }

    public void normalize()
    {
        float m = magnitude();
        if(m == 0) return;
        x /= m;
        y /= m;
    }

    public Vector2 normalized()
    {
        float m = magnitude();
        if(m == 0) return new Vector2();
        return new Vector2(this.x/m,this.y/m);
    }

    public float distance(Vector2 other)
    {
        return (float)Math.sqrt(Math.pow(other.getX()-x,2.0)+Math.pow(other.getY()-y,2.0));
    }

    public float distanceSquared(Vector2 other)
    {
        return (float)(Math.pow(other.getX()-x,2.0)+Math.pow(other.getY()-y,2.0));
    }

    public void moveTowards(Vector2 other,float delta)
    {
        float dist = distance(other);
        Vector2 direction = other.subtracted(this).normalized();
        direction.multiply(Math.min(delta,dist));
        add(direction);
    }

    public Vector2 movedTowards(Vector2 other,float delta)
    {
        float dist = distance(other);
        Vector2 direction = other.subtracted(this).normalized();
        direction.multiply(Math.min(delta,dist));
        return added(direction);
    }

    public void lerp(Vector2 other, float delta)
    {
        this.x = (other.getX() - this.x) * Math.clamp(delta,0f,1f) + this.x;
        this.y = (other.getY() - this.y) * Math.clamp(delta,0f,1f) + this.y;
    }

    public Vector2 lerped(Vector2 other, float delta)
    {
        return new Vector2((other.getX() - this.x) * Math.clamp(delta,0f,1f) + this.x,(other.getY() - this.y) * Math.clamp(delta,0f,1f) + this.y);
    }

    public void capMagnitude(float max)
    {
        float m = magnitude();
        normalize();
        multiply(Math.min(m,max));
    }

    public Vector2 cappedMagnitude(float max)
    {
        float m = magnitude();
        return normalized().multiplied(Math.min(m,max));
    }

    public boolean isZero()
    {
        return x == 0 && y == 0;
    }
    public String toString() {
        return "Vector2 {" + x + ", " + y + "}";
    }
    public void set(Vector2 other)
    {
        this.x = other.getX();
        this.y = other.getY();
    }
}
