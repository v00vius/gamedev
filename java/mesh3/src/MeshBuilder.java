
import java.util.*;

public class MeshBuilder {
private Precision precision;
private Map<Point, Point> positions;
private List<Integer> indices;

public MeshBuilder(float epsilon) {
    this.precision = Precision.create(epsilon);
    positions = new TreeMap<>();
    indices = new ArrayList<>();
}

private Point get(Point p)
{
        Point pos = positions.putIfAbsent(p, p);

        return pos == null ? p : pos;
}
public class Point implements Comparable <Point> {
public float x;
public float y;
public float z;

public Point(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
}

public Point(float x, float y)
{
    this.x = x;
    this.y = y;
    this.z = 0.f;
}

@Override
public boolean equals(Object o)
{
    if (this == o) {
        return true;
    }

    if (o == null || getClass() != o.getClass()) {
        return false;
    }

    Point point = (Point) o;

    return 0 == this.compareTo(point);
}

@Override
public int compareTo(MeshBuilder.Point p) {
    int cmp = precision.compare(x, p.x);

    if(cmp == 0)
        cmp = precision.compare(y, p.y);

    if(cmp == 0)
        cmp = precision.compare(z, p.z);

    return cmp;
}
}
}
