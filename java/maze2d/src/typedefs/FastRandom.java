package typedefs;

import java.util.Random;

public class FastRandom {
private int[] values;
private int index;
public FastRandom(int length)
{
        values = new int[length];
        index = 0;

        Random rnd = new Random(System.currentTimeMillis());
//        Random rnd = new Random(2);

        for (int i = 0, len = values.length; i < len; ++i)
                values[i] = rnd.nextInt(0, Integer.MAX_VALUE);
}
public int nextInt(int bound)
{
        return values[++index % values.length] % bound;
//        return ++index % bound;
}
}
