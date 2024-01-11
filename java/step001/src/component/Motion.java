package component;

import types.Vector2;

public class Motion extends Component {
    private Position position;
    private Vector2 velocity;

    public Motion(Position position) {
        super();

        this.position = position;
        this.velocity = new Vector2();
    }

    public Vector2 setVelocity(float vx, float vy) {
        velocity.set(vx, vy);

        return velocity;
    }
    public void stop() {
        setVelocity(0.f, 0.f);
    }
    public Vector2 getVelocity() {
        return velocity;
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

    public void stepBack(short n) {
        while(n-- > 0)
            position.getCoordinate().add(-velocity.x, -velocity.y);
    }

    @Override
    protected Short action(Component component) {
        position.getCoordinate().add(velocity);

        return 1;
    }
}
