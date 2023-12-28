package component;

import entity.Entity;
import types.Vector2;

public class Position extends Component {
    private Vector2 position;

    public Position(Entity e, Vector2 position) {
        super(e);
        this.position = position;
    }
    public Position(Entity e, float x, float y) {
        super(e);
        this.position = new Vector2(x,y);
    }
}
