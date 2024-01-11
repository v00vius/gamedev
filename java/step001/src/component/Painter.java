package component;

import types.Color;
import types.PaintContext;
import types.Vector2;

public class Painter extends Component  {
    static public Painter NIL = createEmpty();
    private PaintContext paintContext;
    private Position position;
    public int defaultColor;
    private float opacityFactor;
    private Vector2 absPosition;

    static private Painter createEmpty() {
        return new Painter(Position.NIL, Color.create());
    }
    public Painter(Position position, int defaultColorColor) {
        super();

        this.position = position;
        this.defaultColor = defaultColorColor;
        setOpacityFactor(1.f);
        absPosition = new Vector2();
    }

    public void setPaintContext(PaintContext paintContext) {
        this.paintContext = paintContext;
    }

    public void setOpacityFactor(float opacityFactor) {
        this.opacityFactor = opacityFactor;
    }

    public float getOpacityFactor() {
        return opacityFactor;
    }

    @Override
    protected Short action() {
        Mesh mesh = position.getMesh();

        absPosition.set(paintContext.windowPosition).add(position.getCoordinate());

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

                exactColor.set(defaultColor);
                paintContext.drawList.addTriangleFilled(
                        absPosition.x + vx[v0], absPosition.y + vy[v0],
                        absPosition.x + vx[v1], absPosition.y + vy[v1],
                        absPosition.x + vx[v2], absPosition.y + vy[v2],
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
                paintContext.drawList.addTriangleFilled(
                        absPosition.x + vx[v0], absPosition.y + vy[v0],
                        absPosition.x + vx[v1], absPosition.y + vy[v1],
                        absPosition.x + vx[v2], absPosition.y + vy[v2],
                        exactColor.setA(opacityFactor)
                );
            }
        }

        return 1;
    }
}
