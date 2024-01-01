package service;

import entity.Entity;
import types.Color;

public class EntityFromMemory extends EntityLoader {
    private String[] entityList;
    private Entity[] entities;

    public EntityFromMemory(int maxEntries, String[] entityList) {
        this.entityList = entityList;
        entities = new Entity[maxEntries];
    }

//     0       1       2   3    4  5   6   7    8  9
//    "Circle circle1 100 100  45  5 255   0    0 50",
//    "Circle circle1 200 200 160  7   0 255    0 75"
//  where:
//      0 - Object name
//      1 - Entity name
//      2, 3 - initial position x, y
//      4, 5 - initial velocity: bearing, speed
//      6, 7, 8 - rgb
//      9 - radius
    @Override
    public Entity[] load() {
        int count = 0;
        Entity entity = null;

        for (String s : entityList) {

            s.trim();

            if(s.charAt(0) == '#' || s.startsWith("//"))
                continue;

            String[] ids = s.split("[ \t]+");

            if(ids.length < 10) {
                System.out.println("Invalid string '" + s + "', ids.length="
                                        + ids.length+ ", ignored");

                continue;
            }

            switch(ids[0]) {
                case "Circle":
                    break;

                case "Rectangle":
                    break;

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

}
