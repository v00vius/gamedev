package component;

import imgui.*;

import types.Color;
import types.Vector2;

public class Painter extends Component  {
    public Vector2 screenPosition;
    public ImDrawList drawList;
    public int color;
    private float opacityFactor;

    public Painter() {
        super();

        screenPosition = new Vector2();
        setOpacityFactor(1.f);
    }

    public void setOpacityFactor(float opacityFactor) {
        this.opacityFactor = opacityFactor;
    }

    public float getOpacityFactor() {
        return opacityFactor;
    }

    @Override
    public void action() {
        if(mesh == null)
            return;

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
                        screenPosition.x + vx[v0], screenPosition.y + vy[v0],
                        screenPosition.x + vx[v1], screenPosition.y + vy[v1],
                        screenPosition.x + vx[v2], screenPosition.y + vy[v2],
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
                        screenPosition.x + vx[v0], screenPosition.y + vy[v0],
                        screenPosition.x + vx[v1], screenPosition.y + vy[v1],
                        screenPosition.x + vx[v2], screenPosition.y + vy[v2],
                        exactColor.setA(opacityFactor)
                );
            }
        }
    }
}
