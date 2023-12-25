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
    public Vector2 mul(float m) {
        x *= m;
        y *= m;

        return this;
    }
    public Vector2 projection(float length, float angleDegrees) {
        float rad = (float) Math.toRadians(angleDegrees);

        x = length * (float) Math.cos((double) rad);
        y = length * (float) Math.sin((double) rad);

        return this;
    }
    public float distance(Vector2 b) {
        float dx = x - b.x;
        float dy = y - b.y;

        return (float) Math.sqrt((double)(dx * dx) + (double)(dy * dy));
    }
    public float length() {
        return (float) Math.sqrt((double)(x * x) + (double)(y * y));
    }
}
