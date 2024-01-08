package component;

import types.Vector2;

public class BoundingBox extends Component {
    private Position position;
    private Vector2 center;
    private Vector2 radius;

    public BoundingBox() {
        super();

        position = null;
        center = new Vector2();
        radius = null;
    }

    public void attach(Position pos) {
        if(pos == null)
            return;

        position = pos;

        if(radius == null) {
            Mesh mesh = position.getMesh();

            if(mesh == null)
                radius = new Vector2(1.f, 1.f);
            else {
                radius = mesh.getBoundingBox();
                radius.mul(0.5f);
            }
        }
    }

    public Vector2 getCenter() {
        return center.set(position.getPosition()).add(radius);
    }

    public Vector2 getRadius() {
        return radius;
    }

    @Override
    public short action(Component component) {
        if(component == null)
            return 0;

        BoundingBox bb = (BoundingBox) component;

        return 1;
    }
}
