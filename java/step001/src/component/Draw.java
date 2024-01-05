package component;

import imgui.*;

import types.Context;
import types.Vector2;

public class Draw implements Context  {
    public Vector2 position;
    public ImDrawList drawList;
    public int color;

    public Draw() {
        position = new Vector2();
    }

    @Override
    public void action(Mesh mesh) {
        float[] vx = mesh.getX();
        float[] vy = mesh.getY();
        int[] tr = mesh.getTriangles();
        int[] trColor = mesh.getColor();

        for (int i = 0; i < tr.length; i += 3) {
            int v0 = tr[i + 0];
            int v1 = tr[i + 1];
            int v2 = tr[i + 2];
            int col = trColor == null ? color : trColor[ i / 3];

            drawList.addTriangleFilled(
                    position.x + vx[v0], position.y + vy[v0],
                    position.x + vx[v1], position.y + vy[v1],
                    position.x + vx[v2], position.y + vy[v2],
                    col
            );
        }
    }
}
