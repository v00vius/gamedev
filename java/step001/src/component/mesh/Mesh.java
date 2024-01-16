package component.mesh;

import component.Component;
import types.Color;
import types.Precision;
import types.String8;
import types.Vector2;

import java.util.*;

public class Mesh extends Component implements Cloneable {
        static public Mesh NIL = createEmpty();
        private Long id;
        private float[] vx;
        private float[] vy;
        private short[] vertices;
        private int[] colors;

        static private Mesh createEmpty()
        {
                return new Mesh("__NIL__", new float[0], new float[0], new short[0], null);
        }

        public boolean isEmpty()
        {
                return size() == 0;
        }

        public Mesh(String name, float[] vx, float[] vy, short[] vertices, int[] colors)
        {
                super();

                this.id = String8.pack(name);
                this.vx = vx;
                this.vy = vy;
                this.vertices = vertices;
                this.colors = colors;
        }

        public Mesh(Mesh mesh)
        {
                super();

                id = mesh.id;
                vx = mesh.vx.clone();
                vy = mesh.vy.clone();
                vertices = mesh.vertices.clone();
                colors = (mesh.colors == null) ? null : mesh.colors.clone();
        }

        public float[] getX()
        {
                return vx;
        }

        public float[] getY()
        {
                return vy;
        }

        public short[] getTriangles()
        {
                return vertices;
        }

        public int[] getColors()
        {
                return colors;
        }
        public Map<Edge, Short> getEdges() {
                Map<Edge, Short> map = new HashMap<>(vertices.length / 3);

                for (short i = 0; i < vertices.length / 3; i += 3) {
                        putEdge(map, i, (short) (i + 1));
                        putEdge(map, (short)(i + 1), (short) (i + 2));
                        putEdge(map, i, (short) (i + 2));
                }

                return map;
        }
        private void putEdge(Map<Edge, Short> map, short n1, short n2) {
                Edge e = new Edge(vertices[n1], vertices[n2]);
                Short count = map.computeIfAbsent(e, k -> (short) 0);

                ++count;
                map.put(e, count);
        }

        @Override
        public Mesh clone()
        {
                return new Mesh(this);
        }

        public Long getId()
        {
                return id;
        }

        public String getName()
        {
                return String8.unpack(id);
        }

        public Long setName(String tag)
        {
                return this.id = String8.pack(tag);
        }

        public int size()
        {
                return vertices.length;
        }

        public int points()
        {
                return vx.length;
        }

        public Mesh mirorX()
        {
                for (short i = 0; i < vx.length; ++i)
                        vx[i] = -vx[i];

                return this;
        }

        public Mesh mirorY()
        {
                for (short i = 0; i < vy.length; ++i)
                        vy[i] = -vy[i];

                return this;
        }

        public Mesh flipXY()
        {
                for (short i = 0; i < vy.length; ++i) {
                        float tmp = vx[i];

                        vx[i] = vy[i];
                        vy[i] = tmp;
                }

                return this;
        }

        public Mesh translate(Vector2 d)
        {
                for (short i = 0; i < vx.length; ++i) {
                        vx[i] += d.x;
                        vy[i] += d.y;
                }

                return this;
        }

        public Mesh union(Mesh mesh)
        {
                int sz = vx.length + mesh.vx.length;
                float[] uvx = new float[sz];
                float[] uvy = new float[sz];
                short i;

                for (i = 0; i < vx.length; ++i) {
                        uvx[i] = vx[i];
                        uvy[i] = vy[i];
                }

                for (short j = 0; i < uvx.length; ++i, ++j) {
                        uvx[i] = mesh.vx[j];
                        uvy[i] = mesh.vy[j];
                }


                sz = vertices.length + mesh.vertices.length;
                short[] uvertices = new short[sz];

                for (i = 0; i < vertices.length; ++i) {
                        uvertices[i] = vertices[i];
                }

                for (short j = 0; i < uvertices.length; ++i, ++j) {
                        uvertices[i] = (short) (vx.length + mesh.vertices[j]);
                }

                vx = uvx;
                vy = uvy;
                vertices = uvertices;

                if (colors != null && mesh.colors != null) {
                        sz = colors.length + mesh.colors.length;
                        int[] ucolors = new int[sz];

                        for (i = 0; i < colors.length; ++i) {
                                ucolors[i] = colors[i];
                        }

                        for (short j = 0; i < ucolors.length; ++i, ++j) {
                                ucolors[i] = mesh.colors[j];
                        }

                        colors = ucolors;
                } else
                        colors = null;

                return this;
        }

        public Mesh rotate(float radians)
        {
                Vector2 r = new Vector2();

                for (short i = 0; i < vx.length; ++i) {
                        r.x = vx[i];
                        r.y = vy[i];
                        r.rotate(radians);
                        vx[i] = r.x;
                        vy[i] = r.y;
                }

                return this;
        }

        public Mesh zoom(float factor)
        {
                for (short i = 0; i < vx.length; ++i) {
                        vx[i] *= factor;
                        vy[i] *= factor;
                }

                return this;
        }

        public int pack()
        {
                short count;
                BitSet dup = new BitSet(vx.length);
                short[] remap = new short[vx.length];
                Precision precision = Precision.create(1e-5f);

                for (short i = 0; i < vx.length; ++i)
                        remap[i] = i;

                dup.clear(0, vx.length - 1);
                count = 0;

                for (short i = 0; i < vx.length; ++i) {
                        if (dup.get(i))
                                continue;

                        float p0x = vx[i];
                        float p0y = vy[i];

                        for (short j = (short) (i + 1); j < vx.length; ++j) {
                                float p1x = vx[j];
                                float p1y = vy[j];

                                if (precision.equals(p0x, p1x) && precision.equals(p0y, p1y)) {
                                        remap[j] = count;
                                        dup.set(j);

                                        for (short k = (short) (j + 1); k < vx.length; ++k) {
                                                if (remap[k] > count)
                                                        --remap[k];
                                        }
                                }
                        }

                        ++count;
                }

                if (count < vx.length) {
                        float[] vx_new = new float[count];
                        float[] vy_new = new float[vx_new.length];

                        count = 0;

                        for (short i = 0; i < vx.length; ++i) {
                                if (dup.get(i))
                                        continue;

                                vx_new[count] = vx[i];
                                vy_new[count] = vy[i];
                                ++count;
                        }

                        vx = vx_new;
                        vy = vy_new;

                        for (short i = 0; i < vertices.length; ++i)
                                vertices[i] = remap[vertices[i]];
                }

                return count;
        }

        public void getBoundingBox(Vector2 p0, Vector2 p1)
        {
                if (isEmpty()) {
                        p0.set(0.f, 0.f);
                        p1.set(0.f, 0.f);

                        return;
                }

                float min_x = vx[0];
                float min_y = vy[0];
                float max_x = vx[0];
                float max_y = vy[0];

                for (short i = 1; i < vx.length; ++i) {
                        if (vx[i] < min_x)
                                min_x = vx[i];

                        else if (vx[i] > max_x)
                                max_x = vx[i];

                        if (vy[i] < min_y)
                                min_y = vy[i];

                        else if (vy[i] > max_y)
                                max_y = vy[i];
                }

                p0.set(min_x, min_y);
                p1.set(max_x, max_y);
        }

        @Override
        public boolean equals(Object o)
        {
                if (this == o)
                        return true;

                if (o == null || getClass() != o.getClass())
                        return false;

                Mesh mesh = (Mesh) o;

                return id.equals(mesh.id);
        }

        @Override
        public int hashCode()
        {
                return id.hashCode();
        }

        @Override
        public String toString()
        {
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

                if (colors != null) {
                        Color c = new Color();

                        for (short i = 0; i < vertices.length / 3; ++i) {
                                c.set(colors[i]);

                                str += String.format("c %d %d %d %d %d\n", i, c.getR(), c.getG(), c.getB(), c.getA());
                        }
                }

                return str + "mesh end\n";
        }

        @Override
        protected Short action()
        {
                return 0;
        }
}
