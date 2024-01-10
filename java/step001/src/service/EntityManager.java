package service;

import entity.Entity;
import types.String8;

import java.util.ArrayList;
import java.util.Hashtable;

public class EntityManager {
    static private short creationCount = 0;
    private ArrayList<Entity> entities;
    private Hashtable<Long, ArrayList<Entity>> byTag;

    public EntityManager() {
        entities = new ArrayList<>(32);
        byTag = new Hashtable<>();
    }

    static public short getCreationCount() {
        return ++creationCount;
    }

    public Entity newEntity(String tag) {
        Entity entity = new Entity(getCreationCount(), tag);

        entities.add(entity);

        ArrayList<Entity> tagged = byTag.computeIfAbsent(entity.getTagId(), k -> new ArrayList<>());

        tagged.add(entity);

        return entity;
    }

    public ArrayList<Entity> getTaggedAs(String tag) {
        return byTag.computeIfAbsent(String8.pack(tag), k -> new ArrayList<>());
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
