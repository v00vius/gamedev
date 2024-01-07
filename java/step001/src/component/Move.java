package component;

import types.Vector2;

public class Move extends Position {
    private Vector2 velocity;

    public Move() {
        super();

        velocity = new Vector2();
    }

    public Vector2 setVelocity(float vx, float vy) {
        velocity.x = vx;
        velocity.y = vy;

        return velocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getVelocityX() {
        return velocity.x;
    }

    public float getVelocityY() {
        return velocity.y;
    }

    public void reverseX() {
        velocity.x = -velocity.x;
    }

    public void reverseY() {
        velocity.y = -velocity.y;
    }

    @Override
    public void action(Object o) {
        position.add(velocity);
    }
}
