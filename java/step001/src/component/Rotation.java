package component;

import service.Utils;

public class Rotation extends Component {
    static public Rotation NIL = createEmpty();
    private Mesh mesh;
    private float radians;
    private float period;

    static private Rotation createEmpty() {
        return new Rotation(Mesh.NIL, 1.f);
    }

    public Rotation(Mesh mesh, float period) {
        super();

        this.mesh = mesh;
        this.period = period;
    }

    @Override
    protected Short action() {
        float radians = (float) Math.toRadians(360. * Utils.getDeltaTime() / period);

        mesh.rotate(radians);

        return 1;
    }
}
