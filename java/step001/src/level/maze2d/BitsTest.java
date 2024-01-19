package level.maze2d;

public class BitsTest {
public enum sizeof {
;
        public static final int FLOAT = Float.SIZE / 8;
        public static final int INTEGER = Integer.SIZE / 8;
        public static final int DOUBLE = Double.SIZE / 8;

}
static int size_of_byte()
{
        byte x = 1;
        int bytes =0;

        while(x != 0) {
                x = (byte)(x << 8);
                ++bytes;
        }

        return bytes;
}
static int size_of_int()
{
        int x = 1;
        int bytes =0;

        while(x != 0) {
                x = (int)(x << 8);
                ++bytes;
        }

        return bytes;
}
static int size_of_short()
{
        short x = 1;
        int bytes =0;

        while(x != 0) {
                x = (short)(x << 8);
                ++bytes;
        }

        return bytes;
}
static int size_of_long()
{
        long x = 1;
        int bytes =0;

        while(x != 0) {
                x = (long)(x << 8);
                ++bytes;
        }

        return bytes;
}
static int size_of_char()
{
        char x = 1;
        int bytes =0;

        while(x != 0) {
                x = (char)(x << 8);
                ++bytes;
        }

        return bytes;
}

public static void main(String[] args)
{
        Bits bits = new Bits(12, 10);

        bits.set(0, 0);
        bits.set(11, 0);
        bits.set(0, 9);
        bits.set(11, 9);

        System.out.println(bits);

        bits = new Bits(128);
        bits.set(0);
        bits.set(63);
        bits.set(64);
        bits.set(127);
        System.out.println(bits);

        System.out.println("size_of_byte " + size_of_byte());
        System.out.println("size_of_int " + size_of_int());
        System.out.println("size_of_long " + size_of_long());
        System.out.println("size_of_char " + size_of_char());
        System.out.println("size_of_short " + size_of_short());
        System.out.println();
}
}
