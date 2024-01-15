package repo;

import entity.Entity;
import types.String8;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntityManager {
        static private int creationCount = 0;
        private List<Entity> entities;
        private Map<Long, List<Entity>> byTag;

        private static List<Entity> createTagList()
        {
                return new LinkedList<Entity>();
        }

        public EntityManager()
        {
                entities = new ArrayList<Entity>(128);
                byTag = new Hashtable<Long, List<Entity>>(16);
        }

        static public int getCreationCount()
        {
                return ++creationCount;
        }

        public Entity createEntity(String tag)
        {
                Entity entity = new Entity(getCreationCount(), tag);

                entities.add(entity);

                List<Entity> tagged = byTag.computeIfAbsent(entity.getTagId(), k -> createTagList());

                tagged.add(entity);

                return entity;
        }

        public List<Entity> getTaggedAs(String tag)
        {
                return byTag.computeIfAbsent(String8.pack(tag), k -> createTagList());
        }

        public List<Entity> getEntities()
        {
                return entities;
        }
}
