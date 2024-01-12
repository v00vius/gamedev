package component;

import types.Color;
import types.Vector2;

public class BoundingBox extends Component {
    static public int cNormal = new Color(61,25,44).get();
    static public int cCollision = new Color(255,25,44).get();
    static public BoundingBox NIL = createEmpty();
    private Position position;
    private Vector2 p0;
    private Vector2 p1;
    private int color;

    static private BoundingBox createEmpty() {
        return new BoundingBox(Position.NIL);
    }
    public BoundingBox(Position p) {
        super();

        position = p;
        p0 = new Vector2(0f, 0f);
        p1 = new Vector2(0f, 0f);
        update();
        setColor(cNormal);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void update() {
        Mesh mesh = position.getMesh();

        mesh.getBoundingBox(p0, p1);
    }
    public Vector2 getP0() {
        return new Vector2(position.getCoordinate()).add(p0);
    }
    public Vector2 getP1() {
        return new Vector2(position.getCoordinate()).add(p1);
    }

    static public boolean isIntersected(Vector2 pa0, Vector2 pa1, Vector2 pb0, Vector2 pb1) {
        return pa0.x <= pb1.x &&
                pb0.x <= pa1.x &&
                pa0.y <= pb1.y &&
                pb0.y <= pa1.y;
    }

    @Override
    protected Short action() {
        return 0;
    }
}
