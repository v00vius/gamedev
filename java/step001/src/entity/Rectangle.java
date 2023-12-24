package entity;

import types.Vect2;

public class Rectangle extends Entity {
    private Vect2 size;

    public Rectangle(int id, String tag, float width, float height) {
        super(id, tag);

        size = new Vect2(width, height);
    }
}
