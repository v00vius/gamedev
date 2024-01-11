package component;

import types.Vector2;

public class Position extends Component {
    static public Position NIL = createEmpty();
    private Mesh mesh;
    private Vector2 coordinate;

    static private Position createEmpty() {
        return new Position(Mesh.NIL);
    }
    public Position(Mesh mesh) {
        super();

        this.mesh = mesh;
        coordinate = new Vector2();
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Vector2 setCoordinate(float x, float y) {
        return coordinate.set(x, y);
    }
    public Vector2 getCoordinate() {
        return coordinate;
    }

    @Override
    protected Short action() {
        return 0;
    }
}
