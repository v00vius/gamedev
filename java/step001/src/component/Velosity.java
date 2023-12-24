package component;

import types.Vect2;

public class Velosity extends ComponentNode {
    private Vect2 speed;

    public Velosity(Vect2 speed) {
        this.speed = speed;
    }
    public Velosity(float x, float y) {
        this.speed = new Vect2(x,y);
    }

    @Override
    public void apply() {

    }
}
