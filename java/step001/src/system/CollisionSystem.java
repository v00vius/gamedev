package system;

import entity.Entity;
import service.EntityManager;
import types.Pair;
import types.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;

public class CollisionSystem extends System {
    private ArrayList<Pair<Entity>> collisions;

    public CollisionSystem() {
        super();
        collisions = new ArrayList<>(16);
    }

    public boolean hasCollisions() {
        return collisions.size() > 0;
    }

    public ArrayList<Pair<Entity>> getCollisions() {
        return collisions;
    }

    @Override
    public void task(EntityManager entityManager) {
        ArrayList<Entity> entities = entityManager.getEntities();

        collisions.clear();

        for (int i = 0; i < entities.size() - 1; ++i) {
            Entity e1 = entities.get(i);

            if(e1.bBox == null)
                continue;

            for (int j = i + 1; j < entities.size(); ++j) {
                Entity e2 = entities.get(j);

                if(e2.bBox == null)
                    continue;

                Pair<Entity> pair = collisionDetection(e1, e2);

                if(pair != null)
                    collisions.add(pair);
            }
        }
    }

    private static Pair<Entity> collisionDetection(Entity e1, Entity e2) {
        Vector2 crossSection = new Vector2(e1.bBox.getSize());

        crossSection.add(e2.bBox.getSize())
                .sub(e1.bBox.getPosition()
                        .sub(e2.bBox.getPosition())
                        .abs()
                );

        if(crossSection.x  < 0.f || crossSection.y < 0.f)
            return null;

        collision(e1, crossSection);
        collision(e2, crossSection);

        return new Pair<>(e1, e2);
    }

    private static void collision(Entity e1, Vector2 crossSection) {
        if(e1.motion == null)
            return;

        e1.motion.stepBack(e1.position);

        float strike_x = crossSection.x * e1.motion.getVelocity().x;
        float strike_y = crossSection.y * e1.motion.getVelocity().y;

        if (strike_x > strike_y)
            e1.motion.reverseX();
        else
            e1.motion.reverseY();
    }

}
