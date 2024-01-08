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
        if (o == null || getClass() != o.getClass()) return false;
        Mesh mesh = (Mesh) o;

        return id.equals(mesh.id);
/*
   ;     return id.equals(mesh.id)
                && Arrays.equals(vx, mesh.vx)
                && Arrays.equals(vy, mesh.vy)
                && Arrays.equals(vertices, mesh.vertices)
                && Arrays.equals(color, mesh.color);
*/
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        String str = String.format("mesh %d %d %d %s\n",
                vx.length, vertices.length,
                (colors == null ? 0 : colors.length),
                getName()
        );

        for (short i = 0; i < vx.length; ++i) {
            str += String.format("v %d %f %f\n", i, vx[i], vy[i]);
        }

        for (short i = 0; i < vertices.length; i += 3) {
            str += String.format("t %d %d %d %d\n", i / 3, vertices[i], vertices[i + 1], vertices[i + 2]);
        }

        if(colors != null) {
            Color c = new Color();

            for (short i = 0; i < vertices.length / 3; ++i) {
                c.set(colors[i]);

                str += String.format("c %d %d %d %d %d\n", i, c.getR(), c.getG(), c.getB(), c.getA());
            }
        }

        return str;
    }

    public static void store(Mesh mesh, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(mesh.toString());
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Mesh load(String fileName) {
        Scanner rd = null;

        try {
            File file = new File(fileName);
            rd = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            rd.close();
            e.printStackTrace();
        }

        boolean foundMesh = false;
        String[] words;
        float[] x =  null;
        float[] y = null;
        short[] vertices = null;
        int[] colors = null;
        String tag = "mesh";
        Color color = new Color();
        short lineNum = 0;

        System.out.println("# loading \"" + fileName + "\"");

        while (rd.hasNextLine()) {
            String line = rd.nextLine();
            ++lineNum;

            line.trim();

            if(line.isEmpty())
                continue;

            if(line.charAt(0) == '#')
                continue;

            words = line.split("[ \t]+");

            if(foundMesh) {
                short i;

                switch(line.charAt(0)) {
                    case 'v':
                        i = Short.parseShort(words[1]);

                        x[i] = Float.parseFloat(words[2]);
                        y[i] = Float.parseFloat(words[3]);
                        break;

                    case 't':
                        i = Short.parseShort(words[1]);
                        i *= 3;
                        vertices[i    ] = Short.parseShort(words[2]);
                        vertices[i + 1] = Short.parseShort(words[3]);
                        vertices[i + 2] = Short.parseShort(words[4]);
                        break;

                    case 'c':
                        i = Short.parseShort(words[1]);
                        color.set(Integer.parseInt(words[2]),
                                Integer.parseInt(words[3]),
                                Integer.parseInt(words[4]),
                                Integer.parseInt(words[5])
                        );

                        colors[i] = color.get();
                        break;

                    default:
                        System.out.println("File: \"" + fileName + "\" invalid format: \"" + line + "\"");
                }
            }
            else if(0 == words[0].compareTo("mesh")) {
                foundMesh = true;

                short nv = Short.parseShort(words[1]);
                short nt = Short.parseShort(words[2]);
                short nc = Short.parseShort(words[3]);

                tag = words[4];
                x = new float[nv];
                y = new float[nv];
                vertices = new short[nt];

                if (nc > 0 && nc == (nt / 3))
                    colors = new int[nc];

                System.out.println("# found mesh \"" + tag + "\", vertices: " + nv
                                + ", triangles: " + (nt / 3)
                                + ", colors: " + nc
                );
            }
        }

        rd.close();

        if(foundMesh) {
            System.out.println("# \"" + fileName + "\", " + lineNum + " lines, done \"" + tag + "\"");

            return new Mesh(tag, x, y, vertices, colors);
        }

        return null;
    }

}
