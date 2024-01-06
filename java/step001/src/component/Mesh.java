package component;

import types.Vector2;

public class Mesh {
    private float[] vx;
    private float[] vy;
    private short[] vertices;
    private int[] color;

    public Mesh(float[] vx, float[] vy, short[] v, int[] color) {
        this.vx = vx;
        this.vy = vy;
        this.vertices = v;
        this.color = color;
    }

    Vector2 getBoundingBox() {
        float min_x = Float.MAX_VALUE;
        float min_y = Float.MAX_VALUE;
        float max_x = Float.MIN_VALUE;
        float max_y = Float.MIN_VALUE;

        for (int i = 0; i < vx.length; ++i) {
            if(vx[i] < min_x)
                min_x = vx[i];
            else if(vx[i] > max_x)
                max_x = vx[i];

            if(vy[i] < min_y)
                min_y = vy[i];
            else if(vy[i] > max_y)
                max_y = vy[i];
        }

        return new Vector2(max_x - min_x, max_y - min_y);
    }
    
    public float[] getX() {
        return vx;
    }

    public float[] getY() {
        return vy;
    }

    public short[] getTriangles() {
        return vertices;
    }

    public int[] getColor() {
        return color;
    }
}
