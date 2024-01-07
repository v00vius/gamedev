package component;

import types.Vector2;

public class Position extends Component implements Action {
    protected Vector2 position;

    public Position() {
        super();

        position = new Vector2();
    }

    public Vector2 setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;

        return position;
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

    @Override
    public void action(Object o) {

    }
}
