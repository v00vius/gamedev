package component;

import entity.Entity;

public class Speed extends Component {
    private float bearing;
    private float speed;

    public Speed(Entity e, float bearing, float speed) {
        super(e);
        
        this.bearing = bearing;
        this.speed = speed;
    }
}
