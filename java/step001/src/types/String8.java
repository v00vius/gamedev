package types;
//
// Representation of a eight-byte string as a long
public class String8 {
    public static long pack(String s) {
        long packed = 0;
        byte[] str = s.getBytes();

        for (short i = 0; i < 8; ++i) {
            short ch = i < s.length() ? (short)str[i] : (short)' ';

            packed = (packed << 8) | ch;
        }

        return packed;
    }

    public static String unpack(long packed) {
        String s = "";

        for (short i = 0; i < 8; ++i) {
            char b = (char)(~0xFF00 & packed);

            s = b  + s;
            packed >>= 8;
        }

        return s;
    }
}
