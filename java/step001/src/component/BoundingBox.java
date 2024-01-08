package component;

import types.Vector2;

public class BoundingBox extends Component {
    private Vector2 center;
    private Vector2 radius;
    private boolean collision;

    public BoundingBox() {
        super();

        center = new Vector2();
        radius = null;
        collision = false;
    }

    public void attach(Position position) {
        if(position == null)
            return;

        if(radius == null) {
            Mesh mesh = position.getMesh();

            if(mesh == null)
                return;

            radius = mesh.getBoundingBox();
            radius.mul(0.5f);
        }

        center.set(position.getPosition()).add(radius);
        collision = false;
    }

    public Vector2 getCenter() {
        return center;
    }

    public Vector2 getRadius() {
        return radius;
    }

    @Override
    public short action(Component component) {
        if(component == null)
            return 0;

        return 1;
    }
}
