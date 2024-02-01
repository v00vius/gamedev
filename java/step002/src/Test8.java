import types.String8;

import java.util.*;
import java.util.function.Supplier;

public class Test8<T> {
static public Random random = new Random(System.currentTimeMillis());
private Map<T, Integer> aMap;

public Test8(Map<T, Integer> aMap)
{
        this.aMap = aMap;
}

public long test(int mapSize, Supplier<T> supplier)
{
        long delta = System.currentTimeMillis();

        while(mapSize-- > 0) {
                T k = supplier.get();
                int count = aMap.computeIfAbsent(k, v -> 0);

                aMap.put(k, ++count);
        }

        return System.currentTimeMillis() - delta;
}

public static void main(String[] args)
        {
                String test1 = "Hello how are you?";
                long value = String8.pack(test1);
                String test1result = String8.unpack(value);

                System.out.printf("Initial string: \"%s\"\n", test1);
                System.out.printf("Initial string as long: 0x%X\n", value);
                System.out.printf("Converted back to the string: \"%s\"\n", test1result);

                final int testSize = 10_000_000;
                String[] strings = new String[testSize];

                for (int i = 0; i < strings.length; i++) {
                        strings[i] = Integer.toString(random.nextInt(0, testSize));
                }

                Test8<String> testString = new Test8<>(new HashMap<>());
                long delta = testString.test(testSize, () -> strings[random.nextInt(0, testSize)]);
                System.out.println("HashMap<String> " + delta + " ms");

                Test8<Long> testLong = new Test8<>(new HashMap<>());
                delta = testLong.test(testSize, () -> random.nextLong(0, testSize));
                System.out.println("HashMap<Long> " + delta + " ms");
        }
}
