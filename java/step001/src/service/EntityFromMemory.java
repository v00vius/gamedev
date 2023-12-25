package service;

import component.Color;
import component.Position;
import component.Velosity;
import entity.Circle;
import entity.Entity;

public class EntityFromMemory extends EntityLoader {
    private String[] entityList;
    private Entity[] entities;

    public EntityFromMemory(int maxEntries, String[] entityList) {
        this.entityList = entityList;
        entities = new Entity[maxEntries];
    }

//     0       1       2   3   4  5   6   7    8  9
//    "Circle circle1 100 100 -3  2 255   0    0 50",
//    "Circle circle1 200 200 -2 -1   0 255    0 75"
//  where:
//      0 - Object name
//      1 - Entity name
//      2, 3 - initial position x, y
//      4, 5 - initial speed (velocity) dx, dy
//      6, 7, 8 - rgb
//      9 - radius
    @Override
    public Entity[] load() {
        int count = 0;
        Entity entity = null;

        for (String s : entityList) {

            s.trim();

            if(s.charAt(0) == '#')
                continue;

            String[] ids = s.split("[ \t]+");

            if(ids.length < 10) {
                System.out.println("Invalid string '" + s + "', ids.length="
                                        + ids.length+ ", ignored");

                continue;
            }

            switch(ids[0]) {
                case "Circle":
                    entity =  circleFromString(ids);
                    break;

                case "Rectangle":

                default:
                    entity = null;
            }

            if(entity != null)
                entities[count++] = entity;

            if(count >= entities.length)
                break;
        }

        return entities;
    }

    private Entity circleFromString(String[] ids) {
        Circle circle = new Circle(10, ids[1], Float.parseFloat(ids[9]));
        Position position = new Position(Float.parseFloat(ids[2]),
                                        Float.parseFloat(ids[3])
                                        );

        Velosity velosity = new Velosity(Float.parseFloat(ids[4]),
                                        Float.parseFloat(ids[5])
                                        );

        Color color = new Color( Integer.parseInt(ids[6]),
                                Integer.parseInt(ids[7]),
                                Integer.parseInt(ids[8])
                                );

        circle.addComponent(position)
                .addComponent(velosity)
                .addComponent(color);

        return circle;
    }
}
