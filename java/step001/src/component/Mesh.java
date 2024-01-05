package component;

public class Mesh {
    private float[] vx;
    private float[] vy;
    private int[] tr;
    private int[] color;

    public Mesh(float[] vx, float[] vy, int[] tr, int[] color) {
        this.vx = vx;
        this.vy = vy;
        this.tr = tr;
        this.color = color;
    }

    public float[] getX() {
        return vx;
    }

    public float[] getY() {
        return vy;
    }

    public int[] getTriangles() {
        return tr;
    }

    public int[] getColor() {
        return color;
    }
}
