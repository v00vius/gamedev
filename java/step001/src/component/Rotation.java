package component;

public class Rotation extends Component {
    private Mesh mesh;
    private float radians;

    public Rotation(Mesh mesh) {
        super();

        this.mesh = mesh;
        setAngle(0.f);
    }

    public void setAngle(float angleDeg) {
        this.radians = (float)Math.toRadians(angleDeg);
    }

    public float getAngle() {
        return (float) Math.toDegrees(radians);
    }

    @Override
    protected Short action(Component component) {
        mesh.rotate(radians);
        return 1;
    }
}
