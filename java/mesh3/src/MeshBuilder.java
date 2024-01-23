
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MeshBuilder {
private Precision precision;
private Set<Point> positions;
private List<Integer> indices;

public MeshBuilder(float epsilon) {
    this.precision = Precision.create(epsilon);
    positions = new HashSet<>();
    indices = new ArrayList<>();
}

private class Point implements Comparable <Point> {
public float x;
public float y;
public float z;

public Point(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
}

@Override
public int compareTo(MeshBuilder.Point p) {
    int cmp = precision.floatCompare(x, p.x);

    if(cmp == 0)
        cmp = precision.floatCompare(y, p.y);

    if(cmp == 0)
        cmp = precision.floatCompare(z, p.z);

    return cmp;
}
}
}
