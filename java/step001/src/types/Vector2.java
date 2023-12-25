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

    public Vector2 add(Vector2 a, Vector2 b) {
        a.x += b.x;
        a.y += b.y;

        return a;
    }

    public Vector2 sub(Vector2 a, Vector2 b) {
        a.x -= b.x;
        a.y -= b.y;

        return a;
    }
    public Vector2 neg() {
        x = -x;
        y = -y;

        return this;
    }

    public Vector2 mul(Vector2 a, float m) {
        a.x *= m;
        a.y *= m;

        return a;
    }
    public Vector2 projection(Vector2 a, float radius, float angleDegrees) {
        float rad = (float) Math.toRadians(angleDegrees);

        a.x = radius * (float) Math.cos((double) rad);
        a.y = radius * (float) Math.sin((double) rad);

        return a;
    }
}
