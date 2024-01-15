import component.Mesh;
import types.Vector2;

import java.util.Random;

public class TestMesh {
    public static void main(String[] args) {
        float[] pts_x = new float[20];
        float[] pts_y = new float[pts_x.length];
        Random rnd = new Random();

        for (int i = 0; i < pts_x.length; ++i) {
            pts_x[i] = rnd.nextFloat(-100, +100);
            pts_y[i] = rnd.nextFloat(-100, +100);
        }

        short[] indexes = new short[pts_x.length];

        for (short i = 0; i < indexes.length; ++i)
            indexes[i] = i;

        Mesh m0 = new Mesh("m0",
                new float[] {pts_x[0], pts_x[1], pts_x[2]},
                new float[] {pts_y[0], pts_y[1], pts_y[2]},
                new short[] {0, 1, 2},
                null
        );

        Mesh mesh = m0.clone();

        for (int i = 0; i < 100; ++i) {
            randomize(rnd, indexes);

            m0 = new Mesh("m0",
                    new float[] {pts_x[indexes[0]], pts_x[indexes[1]], pts_x[indexes[2]]},
                    new float[] {pts_y[indexes[0]], pts_y[indexes[1]], pts_y[indexes[2]]},
                    new short[] {0, 1, 2},
                    null
            );

            mesh.union(m0);
        }

        mesh.setName("test");

        System.out.println(mesh.points() + " +++ unpacked +++++++++++++++++++++++++++");
        System.out.println(mesh);

        mesh.pack();
        System.out.println(mesh.points() + " +++ packed +++++++++++++++++++++++++++");
        System.out.println(mesh);
    }

    private static void randomize(Random rnd, short[] indexes) {
        for (short i = 0; i < 3; ++i) {
            short j = (short) rnd.nextInt(i + 1, indexes.length);
            short tmp = indexes[i];

            indexes[i] = indexes[j];
            indexes[j] = tmp;
        }
    }
}
