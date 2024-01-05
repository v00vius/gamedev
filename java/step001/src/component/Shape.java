package component;

import types.Context;
import types.Drawable;
import types.Node;
import types.Vector2;

public abstract class Shape extends Node implements Drawable {
    private Vector2 position;
    private Vector2 velocity;

    public Shape() {
        position = new Vector2();
        velocity = new Vector2();
    }
}
