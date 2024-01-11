package system;

import entity.Entity;
import repo.EntityManager;
import types.Triple;
import types.Vector2;

import java.util.List;

public class AISystem extends GameSystem {
    private  CollisionSystem collisionSystem;

    public AISystem(CollisionSystem collisionSystem) {
        this.collisionSystem = collisionSystem;
    }

    @Override
    protected void task(EntityManager entityManager) {
        if(!collisionSystem.hasCollisions())
            return;

        List<Triple<Entity, Entity, Vector2>> collosions = collisionSystem.getCollisions();

        for(Triple<Entity, Entity, Vector2> c : collosions) {
            Entity e1 = c.first;
            Entity e2 = c.second;
            Vector2 intersection = c.third;

            if(e1.getTag().equals("tree")) {

            }
        }
    }
}
