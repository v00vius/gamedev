package component;

public class Rotation extends Component {
    static public Rotation NIL = createEmpty();
    private Mesh mesh;
    private float radians;

    static private Rotation createEmpty() {
        return new Rotation(Mesh.NIL, 0.f);
    }

    public Rotation(Mesh mesh, float degrees) {
        super();

        this.mesh = mesh;
        setAngle(degrees);
    }

    public void setAngle(float angleDeg) {
        this.radians = (float)Math.toRadians(angleDeg);
    }

    public float getAngle() {
        return (float) Math.toDegrees(radians);
    }

    @Override
    protected Short action() {
        mesh.rotate(radians);

        return 1;
    }
}
