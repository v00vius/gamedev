public class Bits {
private final int size_x;
private final int size_y;
private long[] bits;
//private byte[] bits;
static private final int MIN_BITS = Long.SIZE;
//static private final int MIN_BITS = 8 * Byte.BYTES;
public Bits(int size)
{
        size_x = size_y = 0;
        init(size);
}
public Bits(int size_x, int size_y)
{
        this.size_x = size_x;
        this.size_y = size_y;
        init(size_x * size_y);
}
private void init(int size)
{
        int sz = size / MIN_BITS;

        if(0 != size % MIN_BITS)
                ++sz;

        bits = new long[sz];
//        bits = new byte[sz];
}
public int getSize_x()
{
        return size_x;
}
public int getSize_y()
{
        return size_y;
}
public boolean get(int i)
{
        int theWord = i / MIN_BITS;
        int theBit = i % MIN_BITS;

        return (bits[theWord] & (1L << theBit)) != 0;
}
public boolean get(int x, int y)
{
        return get(index(x, y));
}
private int index(int x, int y)
{
        return size_x * y + x;
}
public void set(int i)
{
        int theWord = i / MIN_BITS;
        int theBit = i % MIN_BITS;

        bits[theWord] |= 1L << theBit;
}
public void set(int x, int y)
{
        set(index(x, y));
}
public void clr(int i)
{
        int theWord = i / MIN_BITS;
        int theBit = i % MIN_BITS;

        bits[theWord] &= ~(1L << theBit);
}
public void clr(int x, int y)
{
        clr(index(x, y));
}
public void clear()
{
        for (int i = 0, len = bits.length; i < len; ++i)
                bits[i] = 0;
}

@Override
public String toString()
{
        StringBuilder s = new StringBuilder(String.format("Bits size %d (%d-bit word(s))\n", bits.length, MIN_BITS));

        if(size_x == 0 && size_y == 0) {
                for (int i = 0; i < bits.length; ++i) {
                        s.append(String.format("%03d:", i));

                        for (int x = 0; x < MIN_BITS; ++x) {
                                s.append(get(i * MIN_BITS + x) ? '1' : '0');
                        }

                        s.append('\n');
                }
        }
        else {
                s.append(String.format("size_x: %d, size_y: %d\n", size_x, size_y));

                for (int y = 0; y < size_y; ++y) {
                        s.append(String.format("%03d:", y));

                        for (int x = 0; x < size_x; ++x) {
                                s.append(get(x, y) ? '1' : '0');
                        }

                        s.append('\n');
                }
        }

        return s.toString();
}
}