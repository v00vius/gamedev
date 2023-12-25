package types;

public class Vector2 {
    public float x = 0;
    public float y = 0;

    public Vector2() {
    }
    public Vector2(Vector2 v) {
        x = v.x;
        y = v.y;
    }
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 set(Vector2 v) {
        x = v.x;
        y = v.y;

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
    public Vector2 projection(float length, float angle) {
        float rad = (float) Math.toRadians(angle);

        x = length * (float) Math.cos((double) rad);
        y = length * (float) Math.sin((double) rad);

        return this;
    }
    public float angle() {
        if(Math.abs(x) < 1e-6)
            return 90f;

        return (float) Math.toDegrees(Math.atan2(y, x));
    }
    public Vector2 rotateRelative(float delta) {
        return projection(length(), delta + angle());
    }
    public float distance(Vector2 b) {
        float dx = x - b.x;
        float dy = y - b.y;

        return (float) Math.sqrt((double)(dx * dx) + (double)(dy * dy));
    }
    public float length() {
        return (float) Math.sqrt((double)(x * x) + (double)(y * y));
    }

    public boolean isInsideOf(Vector2 a, Vector2 b) {
        if(x < a.x || x > b.x)
            return false;

        if(y < a.y || y > b.y)
            return false;

        return true;
    }
    public static boolean isIntersect(Vector2 a1, Vector2 b1,
                                        Vector2 a2, Vector2 b2) {

        System.out.println("[0]");
        System.out.println("a1=" + a1);
        System.out.println("b1=" + b1);
        System.out.println("a2=" + a2);
        System.out.println("b2=" + b2);
//  1
        b1.sub(a1);
        a2.sub(a1);
        b2.sub(a1);
        a1.sub(a1);

        System.out.println("[1]");
        System.out.println("a1=" + a1);
        System.out.println("b1=" + b1);
        System.out.println("a2=" + a2);
        System.out.println("b2=" + b2);
//  2
        float angle = b1.angle();

        b1.rotateRelative(-angle);
        a2.rotateRelative(-angle);
        b2.rotateRelative(-angle);

        System.out.println("[2]");
//        System.out.println("a1=" + a1);
        System.out.println("b1=" + b1);
        System.out.println("a2=" + a2);
        System.out.println("b2=" + b2);
//  3
        if(Math.abs(a2.y - b2.y) < 1e-5)
            return false;

        Vector2 c = new Vector2(b2);

        c.sub(a2).abs();

        float x = c.x < 1e-5 ? a2.x : a2.y * c.x / c.y;

        System.out.println("[3]");
        System.out.println("c=" + c);
        System.out.println("x=" + x);

        return (-1e-5f <= x && x <= (b1.x + 1e-5f));
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}
