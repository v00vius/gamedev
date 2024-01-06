package component;

import imgui.*;

import types.Color;
import types.Context;
import types.Vector2;

public class Draw implements Context  {
    public Vector2 position;
    public ImDrawList drawList;
    public int color;
    private float opacityFactor;    // 0 .. 1

    public Draw() {
        position = new Vector2();
        opacityFactor = 1.f;
    }

    public void setOpacityFactor(float opacityFactor) {
        this.opacityFactor = opacityFactor;
    }

    @Override
    public void action(Mesh mesh) {
        float[] vx = mesh.getX();
        float[] vy = mesh.getY();
        short[] tr = mesh.getTriangles();
        int[] trColor = mesh.getColor();
        Color ctmp = new Color();

        for (int i = 0; i < tr.length; i += 3) {
            int v0 = tr[i + 0];
            int v1 = tr[i + 1];
            int v2 = tr[i + 2];
            int col = trColor == null ? color : trColor[ i / 3];

            ctmp.set(col);
            col = ctmp.setA((int)((float)ctmp.getA() * opacityFactor));

            drawList.addTriangleFilled(
                    position.x + vx[v0], position.y + vy[v0],
                    position.x + vx[v1], position.y + vy[v1],
                    position.x + vx[v2], position.y + vy[v2],
                    col
            );
        }
    }
}
