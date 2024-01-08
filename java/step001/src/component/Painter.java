package component;

import imgui.*;

import types.Color;
import types.Vector2;

public class Painter extends Component implements Action {
    public Vector2 position;
    public ImDrawList drawList;
    public int color;
    private float opacityFactor;

    public Painter() {
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
        short[] triangles = mesh.getTriangles();
        int[] colors = mesh.getColors();
        Color exactColor = new Color();

        if(colors == null) {
            for (short i = 0; i < triangles.length; i += 3) {
                short v0 = triangles[i    ];
                short v1 = triangles[i + 1];
                short v2 = triangles[i + 2];

                exactColor.set(color);
                drawList.addTriangleFilled(
                        position.x + vx[v0], position.y + vy[v0],
                        position.x + vx[v1], position.y + vy[v1],
                        position.x + vx[v2], position.y + vy[v2],
                        exactColor.setA(opacityFactor)
                );
            }
        }
        else {
            for (short i = 0, j = 0; i < triangles.length; i += 3, ++j) {
                short v0 = triangles[i    ];
                short v1 = triangles[i + 1];
                short v2 = triangles[i + 2];

                exactColor.set(colors[j]);
                drawList.addTriangleFilled(
                        position.x + vx[v0], position.y + vy[v0],
                        position.x + vx[v1], position.y + vy[v1],
                        position.x + vx[v2], position.y + vy[v2],
                        exactColor.setA(opacityFactor)
                );
            }
        }
    }
}
