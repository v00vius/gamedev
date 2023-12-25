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

        Vector2 v1 = new Vector2(100, 100);
        Vector2 v2 = new Vector2(1, 1);
        Vector2 v3 = new Vector2();

        v3.set(v1);
        System.out.println("v3 = " + v3);
        System.out.println("v2 = " + v2);

        v3.add(v2).sub(v2).mul(0.5f);
        System.out.println("((v3 += v2) -= v2) * 0.5: " + v3);
        System.out.println("v3.length = " + v3.length());

        for(float a = 0; a <= 360; a += 10) {
            v1.projection(100, a);
            System.out.println("projection of 100, angle " + a + ": " + v1);
        }
    }
}
