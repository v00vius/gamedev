package entity;

public class Circle extends Entity {
    private float radius;

    public Circle(int id, String tag, float radius) {
        super(id, tag);

        this.radius = radius;
    }
}
