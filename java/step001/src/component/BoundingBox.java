package component;

import types.Vector2;

public class BoundingBox extends Component {
    private Position position;
    private Vector2 center;
    private Vector2 radius;

    public BoundingBox(Position p) {
        super();

        position = p;
        center = new Vector2(0f, 0f);
        radius = new Vector2(1f, 1f);
        update();
    }

    public void update() {
            Mesh mesh = position.getMesh();

            if(mesh == null) {
                center.set(0f, 0f);
                radius.set(1f, 1f);
            }
            else
                mesh.getBoundingBox(center, radius);
    }

    public Vector2 getPosition() {
        return new Vector2(position.getPosition()).add(center);
    }

    public Vector2 getSize() {
        return radius;
    }

    @Override
    public short action(Component component) {
/*
        if(component == null)
            return 0;

        BoundingBox bb = (BoundingBox) component;
        Vector2 delta = getPosition();

        delta.sub(bb.getPosition()).abs();

        if(delta.x > getSize().x + bb.getSize().x)
            return 0;

        if(delta.y > getSize().y + bb.getSize().y)
            return 0;
*/

        return 1;
    }
}
