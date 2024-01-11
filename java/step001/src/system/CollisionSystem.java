package system;

import entity.Entity;
import repo.EntityManager;
import types.Triple;
import types.Vector2;

import java.util.ArrayList;
import java.util.List;

public class CollisionSystem extends GameSystem {
    private List<Triple<Entity, Entity, Vector2>> collisions;

    public CollisionSystem() {
        super();
        collisions = new ArrayList<>(16);
    }

    public boolean hasCollisions() {
        return !collisions.isEmpty();
    }

    public List<Triple<Entity, Entity, Vector2>> getCollisions() {
        return collisions;
    }

    @Override
    public void task(EntityManager entityManager) {
        List<Entity> entities = entityManager.getEntities();

        collisions.clear();
        detectCollisions(entities);
    }

    private void detectCollisions(List<Entity> entities) {
        for (int i = 0; i < entities.size() - 1; ++i) {
            Entity e1 = entities.get(i);

            if(!e1.bBox.isEnabled())
                continue;

            for (int j = i + 1; j < entities.size(); ++j) {
                Entity e2 = entities.get(j);

                if(!e2.bBox.isEnabled())
                    continue;

                Triple<Entity, Entity, Vector2> collided = collisionDetection(e1, e2);

                if(collided != null)
                    collisions.add(collided);
            }
        }
    }

    private static Triple<Entity, Entity, Vector2> collisionDetection(Entity e1, Entity e2) {
        Vector2 crossSection = new Vector2(e1.bBox.getSize());

        crossSection.add(e2.bBox.getSize())
                .sub(e1.bBox.getPosition()
                        .sub(e2.bBox.getPosition())
                        .abs()
                );

        if(crossSection.x  < 0.f || crossSection.y < 0.f)
            return null;

        return new Triple<>(e1, e2, crossSection);
    }

    private static void collision(Entity e1, Vector2 crossSection) {
        if(!e1.motion.isEnabled())
            return;

        e1.motion.stepBack(1);

        float strike_x = crossSection.x * e1.motion.getVelocity().x;
        float strike_y = crossSection.y * e1.motion.getVelocity().y;

        if (strike_x > strike_y)
            e1.motion.reverseX();
        else
            e1.motion.reverseY();
    }

}
