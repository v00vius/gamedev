package component;

import types.Vector2;

public class Position extends Component {
    private Mesh mesh;
    private Vector2 position;

    public Position() {
        super();

        position = new Vector2();
    }

    public Vector2 setPosition(float x, float y) {
        return position.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }

    public Mesh getMesh() {
        return mesh;
    }

    @Override
    public short action(Component component) {
        mesh = (Mesh) component;

        return 0;
    }
}
