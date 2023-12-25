package component;

import types.Vector2;

public class Velosity extends ComponentNode {
    private Vector2 speed;

    public Velosity(Vector2 speed) {
        this.speed = speed;
    }
    public Velosity(float x, float y) {
        this.speed = new Vector2(x,y);
    }

    @Override
    public void apply() {

    }
}
