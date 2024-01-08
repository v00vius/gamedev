package component;

import types.Color;
import types.String8;
import types.Vector2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Mesh implements Cloneable {
    private Long id;

    private float[] vx;
    private float[] vy;
    private short[] vertices;
    private int[] colors;

    public Mesh(Mesh mesh) {
        id = mesh.id;
        vx = mesh.vx.clone();
        vy = mesh.vy.clone();
        vertices = mesh.vertices.clone();
        colors =  (mesh.colors == null) ? null : mesh.colors.clone();
    }

    public Mesh(String name, float[] vx, float[] vy, short[] vertices, int[] colors) {
        this.id = String8.pack(name);
        this.vx = vx;
        this.vy = vy;
        this.vertices = vertices;
        this.colors = colors;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return String8.unpack(id);
    }
    public Long setName(String tag) { return this.id = String8.pack(tag); }
    public int size() {
        return vertices.length / 3;
    }

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

    public int[] getColors() {
        return colors;
    }

    @Override
    public Mesh clone() {
        return new Mesh(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Mesh mesh = (Mesh) o;

        return id.equals(mesh.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        String str = "# mesh numOfCoordinates numOfTriangleVertices numOfColorsOfTriangles meshName\n"
                + String.format("mesh %d %d %d %s\n",
                        vx.length, vertices.length,
                        (colors == null ? 0 : colors.length),
                        getName()
                );

        str += "# coordinates:\n# v coordinateNumber x y\n";

        for (short i = 0; i < vx.length; ++i) {
            str += String.format("v %d %f %f\n", i, vx[i], vy[i]);
        }

        str += "# triangles:\n# t triangleNumber coordinateIndex1 coordinateIndex2 coordinateIndex3\n";

        for (short i = 0; i < vertices.length; i += 3) {
            str += String.format("t %d %d %d %d\n", i / 3, vertices[i], vertices[i + 1], vertices[i + 2]);
        }

        str += "# colors of triangles:\n# c triangleNumber R G B A\n";

        if(colors != null) {
            Color c = new Color();

            for (short i = 0; i < vertices.length / 3; ++i) {
                c.set(colors[i]);

                str += String.format("c %d %d %d %d %d\n", i, c.getR(), c.getG(), c.getB(), c.getA());
            }
        }

        return str + "mesh end\n";
    }
}
