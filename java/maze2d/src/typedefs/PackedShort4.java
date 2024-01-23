package typedefs;

public class PackedShort4 {
private static final int QUATER = Long.SIZE / 4;
static short get(long packed, int i)
{
        return (short)(packed >>> (i * QUATER));
}
static long set(long packed, int i, short value)
{
        int shift = i * QUATER;

        packed = (packed & ~(0xFFFFL << shift)) | ((long)value << shift);

        return packed;
}
static public String toString(long packed)
{
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
                short v = PackedShort4.get(packed, i);

                builder.append(i).append(": ").append(v).append('\n');
        }

        return builder.toString();
}

public static void main(String[] args)
{
        long packed = 0;

        System.out.println(PackedShort4.toString(packed));

        for (int i = 0; i < 4; i++) {
                packed = PackedShort4.set(packed, i, (short)((i + 10) * 2));
        }

        System.out.println(PackedShort4.toString(packed));

        for (int i = 0; i < 4; i++) {
                short v  = PackedShort4.get(packed, i);
                System.out.println(i + ")" + v);
        }

}
}
