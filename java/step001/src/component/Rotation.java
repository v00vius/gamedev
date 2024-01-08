package component;

import types.Vector2;

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
    public short action(Component component) {
        if(component == null)
            return 0;

        Mesh mesh = (Mesh) component;

        float[] x = mesh.getX();
        float[] y = mesh.getY();
        Vector2 r = new Vector2();

        for (int i = 0; i < x.length; ++i) {
            r.x = x[i];
            r.y = y[i];

            r.rotate(angle);
            x[i] = r.x;
            y[i] = r.y;
        }

        return 1;
    }
}
