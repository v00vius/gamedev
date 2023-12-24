import entity.Entity;
import service.EntityFromMemory;

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
    }
}
