import types.String8;

public class Test8 {
    public static void main(String[] args) {
        String test1 = "";
        long value = String8.pack(test1);
        String test1result = String8.unpack(value);

        System.out.println(test1);
        System.out.println(value);
        System.out.println(test1result);
    }
}
