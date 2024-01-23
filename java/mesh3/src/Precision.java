import java.util.Comparator;

public class Precision implements Comparator<Float> {
private final float epsilon;

static public Precision create(float eps)
{
        return new Precision(eps);
}

private Precision(float epsilon)
{
        this.epsilon = epsilon;
}
public int floatCompare(float f1, float f2)
{
        if (Math.abs(f1 - f2) <= epsilon)
                return 0;

        return f1 < f2 ? -1 : 1;
}
public boolean equals(float f1, float f2)
{
        return 0 == compare(f1, f2);
}
public boolean lessThan(float f1, float f2)
{
        return -1 == compare(f1, f2);
}
public boolean greaterThan(float f1, float f2)
{
        return 1 == compare(f1, f2);
}

@Override
public int compare(Float o1, Float o2) {
        return floatCompare(o1, o2);
}

}
