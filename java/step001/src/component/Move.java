package component;

import types.Context;
import types.Vector2;

public class Move implements Context {
    private Vector2 position;
    private Vector2 velocity;

    public Move(float dx, float dy) {
        this.position = new Vector2();
        this.velocity = new Vector2(dx, dy);
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public void reverseX() {
        velocity.x = -velocity.x;
    }

    public void reverseY() {
        velocity.y = -velocity.y;
    }

    @Override
    public void action(Mesh mesh) {
        position.add(velocity);
    }
}
