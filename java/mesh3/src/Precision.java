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

@Override
public int compare(Float f1, Float f2)
{
        if (Math.abs(f1 - f2) <= epsilon)
                return 0;

        return f1 < f2 ? -1 : 1;
}

}
