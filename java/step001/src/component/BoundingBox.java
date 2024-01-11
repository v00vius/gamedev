package component;

import types.Vector2;

public class BoundingBox extends Component {
    static public BoundingBox NIL = createEmpty();
    private Position position;
    private Vector2 center;
    private Vector2 radius;

    static private BoundingBox createEmpty() {
        return new BoundingBox(Position.NIL);
    }
    public BoundingBox(Position p) {
        super();

        position = p;
        center = new Vector2(0f, 0f);
        radius = new Vector2(1f, 1f);
        update();
    }

    public void update() {
            Mesh mesh = position.getMesh();

            mesh.getBoundingBox(center, radius);
    }

    public Vector2 getPosition() {
        return new Vector2(position.getCoordinate()).add(center);
    }
    public Vector2 getSize() {
        return radius;
    }

    @Override
    protected Short action() {
        return 0;
    }
}
