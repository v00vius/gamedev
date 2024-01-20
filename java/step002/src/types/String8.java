package types;

//
// Representation of a eight-byte string as a long
public class String8 {
        public static long pack(String s)
        {
                long packed = 0;
                byte[] sBytes = s.getBytes();
                short bound = (short) Math.min(8, sBytes.length);

                for (short i = 0; i < bound; ++i) {
                        packed = (packed << 8) | (short) sBytes[i];
                }

                for (short i = bound; i < 8; ++i) {
                        packed = (packed << 8) | (short) ' ';
                }

                return packed;
        }

        public static String unpack(long packed)
        {
                StringBuilder s = new StringBuilder();

                for (short i = 0; i < 8; ++i) {
                        char b = (char) (0xFF & packed);

                        s.insert(0, b);
                        packed >>>= 8;
                }

                return s.toString().trim();
        }
}
