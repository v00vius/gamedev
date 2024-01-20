import java.util.Random;

public class FastRandom {
private int[] values;
private int index;
static private Random rnd = new Random(System.currentTimeMillis());
public FastRandom(int length)
{
        values = new int[length];
        index = 0;

        for (int i = 0, len = values.length; i < len; ++i)
                values[i] = rnd.nextInt(0, Integer.MAX_VALUE);
}
public int nextInt(int bound)
{
        return values[++index % values.length] % bound;
}
}
