package component;

import types.Vector2;

public class Position extends ComponentNode {
    private Vector2 position;

    public Position(Vector2 position) {
        this.position = position;
    }
    public Position(float x, float y) {
        this.position = new Vector2(x,y);
    }

    @Override
    public void apply() {

    }
}
