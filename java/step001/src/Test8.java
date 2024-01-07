import types.String8;

public class Test8 {
    public static void main(String[] args) {
        String test1 = "Hello how are you";
        long value = String8.pack(test1);
        String test1result = String8.unpack(value);

        System.out.printf("Initial string: \"%s\"\n", test1);
        System.out.printf("As long: 0x%x\n", value);
        System.out.printf("Converted back to the string: \"%s\"\n", test1result);
    }
}
