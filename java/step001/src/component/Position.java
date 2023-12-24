package component;

import types.Vect2;

public class Position extends ComponentNode {
    private Vect2 position;

    public Position(Vect2 position) {
        this.position = position;
    }
    public Position(float x, float y) {
        this.position = new Vect2(x,y);
    }

    @Override
    public void apply() {

    }
}
