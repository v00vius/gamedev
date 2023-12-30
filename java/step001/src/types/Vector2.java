package types;

public class Vector2 {
    public float x;
    public float y;

    public Vector2() {
        x = y = 0.f;
    }
    public Vector2(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;

        return this;
    }

    public Vector2 add(Vector2 b) {
        x += b.x;
        y += b.y;

        return this;
    }

    public Vector2 sub(Vector2 b) {
        x -= b.x;
        y -= b.y;

        return this;
    }

    public Vector2 neg() {
        x = -x;
        y = -y;

        return this;
    }

    public Vector2 abs() {
        x = Math.abs(x);
        y = Math.abs(y);

        return this;
    }

    public Vector2 mul(float m) {
        x *= m;
        y *= m;

        return this;
    }

    public Vector2 projection(float length, float angleRadians) {
        x = length * (float) Math.cos((double) angleRadians);
        y = length * (float) Math.sin((double) angleRadians);

        return this;
    }

    public float angle() {
        if(Math.abs(x) < 1e-5)
            return (float)Math.toRadians(90.);

        return (float) Math.atan2(y, x);
    }

    public Vector2 rotateRelative(float deltaRadians) {
        return this.projection(this.length(), deltaRadians + this.angle());
    }

    public float distance(Vector2 b) {
        float dx = x - b.x;
        float dy = y - b.y;

        return (float) Math.sqrt((double)(dx * dx) + (double)(dy * dy));
    }

    public float length() {
        return (float) Math.sqrt((double)(x * x) + (double)(y * y));
    }

    public boolean isCollided(float r, Vector2 center1, float r2) {
        Vector2 v = new Vector2(this);

        v.sub(center1);

        return (v.x * v.x + v.y * v.y) < (r + r2) * (r + r2);
    }
    
    public static Vector2 isIntersect2(Vector2 a1, Vector2 b1,
                                        Vector2 a2, Vector2 b2) {
        Vector2 v1 = new Vector2(b1).sub(a1);
        Vector2 v2 = new Vector2(b2).sub(a2);

//      Vectors are parallel
        if((Math.abs(v1.x) < 1e-5 && Math.abs(v2.x) < 1e-5) ||
            (Math.abs(v1.y) < 1e-5 && Math.abs(v2.y) < 1e-5))
            return null;
      
        if(Math.abs(v1.x) < 1e-5) {
            float yc = a2.y + (a1.x - a2.x) * v2.y / v2.x;

            if(yc < Math.min(a1.y, b1.y) - 1e-5 ||
                yc > Math.max(a1.y, b1.y) + 1e-5)
                return null;

            return new Vector2(a1.x, yc );
        }

        if(Math.abs(v2.x) < 1e-5) {
            float yc = a1.y + Math.abs(a2.x - a1.x) * v1.y / v1.x;

            if(yc < Math.min(a2.y, b2.y) - 1e-5 ||
                yc > Math.max(a2.y, b2.y) + 1e-5 )
                return null;

            return new Vector2(a1.x, yc );
        }

        float c1 = v1.y / v1.x;
        float c2 = v2.y / v2.x;

        float d = c1 * a1.x - a1.y - c2 * a2.x + a2.y;
        float xc = d / (c1 - c2);
        float yc = c1 * (xc - a1.x)  + a1.y;

        if(xc < Math.min(a1.x, b1.x) - 1e-5 ||
            xc > Math.max(a1.x, b1.x) + 1e-5)
            return null;

        return new Vector2(xc, yc);                                            
    }                                            

    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}
