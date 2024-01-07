package component;

public class Component {
    protected Mesh mesh;

    public Component() {
        mesh = null;
    }

    public Component(Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}
