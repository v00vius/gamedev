import entity.Entity;
import service.EntityFromMemory;
import types.Vector2;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        EntityFromMemory loader = new EntityFromMemory(10,
                new String[] {
                    "Circle circle1 100 100 -3 2 255 0 0 50",
                    "Circle circle1 200 200 -2 -1 0 255 0 75"
                }
        );

        Entity[] shapes = loader.load();

        Vectortest();
    }

    private static void Vectortest() {
        // Vector2 v1 = new Vector2(100, 100);
        // Vector2 v2 = new Vector2(1, 1);
        // Vector2 v3 = new Vector2();

        // v3.set(v1);
        // System.out.println("v3 = " + v3);
        // System.out.println("v2 = " + v2);

        // v3.add(v2).sub(v2).mul(0.5f);
        // System.out.println("((v3 += v2) -= v2) * 0.5: " + v3);
        // System.out.println("v3.length = " + v3.length());
        // System.out.println("v3 angle: " + v3.angle());
        // System.out.println("v3 angle: " + v3.angle());

        // v3.rotateRelative(-v3.angle());
        // System.out.println("v3 new angle: " + v3.angle() + ", v3=" + v3);
        // v3.rotateRelative(90f);
        // System.out.println("v3 rotation (+90 deg), angle: " + v3.angle() + ", v3=" + v3);

        // for(float a = 0; a <= 360; a += 30) {
        //     v1.projection(100, a);
        //     System.out.println("projection of 100, bearing " + a + ": " + v1);
        // }

//        Vector2 a1 = new Vector2(10, 10);
//        Vector2 b1 = new Vector2(50, 20);
//        Vector2 a2 = new Vector2(10, 50);
//        Vector2 b2 = new Vector2(40, 00);

//        Vector2 a1 = new Vector2(10, 10);
//        Vector2 b1 = new Vector2(50, 10);
//        Vector2 a2 = new Vector2(10, 50);
//        Vector2 b2 = new Vector2(40, 50);
        Vector2 a1 = new Vector2(00, 50);
        Vector2 b1 = new Vector2(20, 00);
        Vector2 a2 = new Vector2(20, 50);
        Vector2 b2 = new Vector2(00, 00);
        Vector2 vc = null;

        long msDuration = System.currentTimeMillis();

        for (long i = 0; i < 1000000; ++i) {
            vc = Vector2.isIntersect2(a1, b1, a2, b2);
        }

        msDuration = System.currentTimeMillis() - msDuration;

        // boolean ok = Vector2.isIntersect(a1, b1, a2, b2);

        System.out.println("Intersection: " + vc);
        System.out.println("Duration: " + msDuration);
    }
}
