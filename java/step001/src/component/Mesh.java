package component;

import types.Color;
import types.String8;
import types.Vector2;

import java.util.Arrays;

public class Mesh implements Cloneable {
    private long id;

    private float[] vx;
    private float[] vy;
    private short[] vertices;

    private int[] color;

    public Mesh(Mesh mesh) {
        id = mesh.id;
        vx = mesh.vx.clone();
        vy = mesh.vy.clone();
        vertices = mesh.vertices.clone();
        color =  (mesh.color == null) ? null : mesh.color.clone();
    }

    public Mesh(String tag, float[] vx, float[] vy, short[] v, int[] color) {
        this.id = String8.pack(tag);
        this.vx = vx;
        this.vy = vy;
        this.vertices = v;
        this.color = color;
    }

    public long getId() {
        return id;
    }
    public String getTag() {
        return String8.unpack(id);
    }
    public long setTag(String tag) { return this.id = String8.pack(tag); }

    Vector2 getBoundingBox() {
        float min_x = Float.MAX_VALUE;
        float min_y = Float.MAX_VALUE;
        float max_x = Float.MIN_VALUE;
        float max_y = Float.MIN_VALUE;

        for (short i = 0; i < vx.length; ++i) {
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

    @Override
    public Mesh clone() {
        return new Mesh(this);
    }

    @Override
    public String toString() {
        String str = String.format("mesh %d %d %d %s\n",
                vx.length, vertices.length,
                (color == null ? 0 : color.length),
                getTag()
        );

        for (short i = 0; i < vx.length; ++i) {
            str += String.format("v %d %f %f\n", i, vx[i], vy[i]);
        }

        for (short i = 0; i < vertices.length; i += 3) {
            str += String.format("t %d %d %d %d\n", i / 3, vertices[i], vertices[i + 1], vertices[i + 2]);
        }

        if(color != null) {
            Color c = new Color();

            for (short i = 0; i < vertices.length / 3; ++i) {
                c.set(color[i]);

                str += String.format("c %d %d %d %d %d\n", i, c.getR(), c.getG(), c.getB(), c.getA());
            }
        }

        return str;
    }
}
