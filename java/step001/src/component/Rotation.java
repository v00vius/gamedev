package component;

public class Rotation extends Component {
    private float angle;

    public Rotation() {
        super();

        angle = 0.f;
    }

    public void setAngle(float angleDeg) {
        this.angle = (float)Math.toRadians(angleDeg);
    }

    @Override
    public Short action(Component component) {
        ((Mesh)component).rotate(angle);
        return 1;
    }
}
