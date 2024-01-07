package component;

import imgui.*;

import types.Color;
import types.Vector2;

public class Draw extends Component implements Action {
    public Vector2 position;
    public ImDrawList drawList;
    public int color;
    private float opacityFactor;    // 0 .. 1

    public Draw() {
        super();

        position = new Vector2();
        setOpacityFactor(1.f);
    }

    public void setOpacityFactor(float opacityFactor) {
        this.opacityFactor = opacityFactor;
    }

    public float getOpacityFactor() {
        return opacityFactor;
    }

    @Override
    public void action(Object o) {
        float[] vx = mesh.getX();
        float[] vy = mesh.getY();
        short[] tr = mesh.getTriangles();
        int[] trColor = mesh.getColor();
        Color ctmp = new Color();

        for (short i = 0; i < tr.length; i += 3) {
            short v0 = tr[i    ];
            short v1 = tr[i + 1];
            short v2 = tr[i + 2];
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
