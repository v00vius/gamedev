package entity;

import types.Vector2;

public class Rectangle extends Entity {
    private Vector2 size;

    public Rectangle(int id, String tag, float width, float height) {
        super(id, tag);

        size = new Vector2(width, height);
    }
}
