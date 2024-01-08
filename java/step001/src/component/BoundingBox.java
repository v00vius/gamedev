package component;

import types.Vector2;

public class BoundingBox extends Position {
    private Vector2 center;
    private Vector2 radius;

    public BoundingBox() {
        super();

        center = new Vector2();
        radius = new Vector2(1.f, 1.f);
    }

    @Override
    public void setMesh(Mesh m) {
        super.setMesh(m);

        radius = mesh.getBoundingBox();
        radius.mul(0.5f);
    }

    public Vector2 getCenter() {
        return center.set(getPosition()).add(radius);
    }

    public Vector2 getRadius() {
        return radius;
    }

    public boolean isCollided(BoundingBox box) {
        Vector2 c1 = getCenter();
        Vector2 c2 = box.getCenter();


    }
}
