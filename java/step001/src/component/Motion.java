package component;

import types.Vector2;

public class Motion extends Component {
    private Vector2 velocity;

    public Motion() {
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

    public void bump(short border) {
        if(border == WindowBounds.LEFT || border == WindowBounds.RIGHT)
            reverseX();

        else if(border == WindowBounds.TOP || border == WindowBounds.BOTTOM)
            reverseY();
    }

    @Override
    public short action(Component component) {
        if(component == null)
            return 0;

        Vector2 position = ((Position) component).getPosition();

        position.add(velocity);

        return 1;
    }
}
