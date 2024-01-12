package system;

import component.BoundingBox;
import entity.Entity;
import imgui.ImGui;
import repo.EntityManager;
import types.Pair;
import types.Triple;
import types.Vector2;

import java.util.*;

public class CollisionSystem extends GameSystem {
    static private int direction = 0;
    private List<Pair<Entity, Entity>> collisions;

    public CollisionSystem() {
        super();

        collisions = new LinkedList<>();
    }

    public boolean hasCollisions() {
        return !collisions.isEmpty();
    }

    public List<Pair<Entity, Entity>> getCollisions() {
        return collisions;
    }

    @Override
    public void task(EntityManager entityManager) {
        List<Entity> entities = entityManager.getEntities();

        collisions.clear();
        detectCollisions(entities);

        for (Pair<Entity, Entity> c : getCollisions()) {
            Entity e1 = c.first;
            Entity e2 = c.second;

            e1.bBox.setColor(BoundingBox.cCollision);
            e2.bBox.setColor(BoundingBox.cCollision);

            switch ( ++direction % 2) {
                case 0:
                    e1.motion.stepBack(1);
                    e2.motion.stepBack(1);
                    e1.motion.reverse();
                    e2.motion.reverse();
                    break;

                case 1:
                    if(e1.motion.getVelocity().x * e2.motion.getVelocity().x >= 0.f) {
                        e1.motion.reverseX();
                        e2.motion.reverseY();
                    }
                    else {
                        e1.motion.reverseX();
                        e2.motion.reverseX();
                    }
                    break;

                case 2:
                    if(e1.motion.getVelocity().y * e2.motion.getVelocity().y >= 0.f) {
                        e1.motion.reverseY();
                        e2.motion.reverseX();
                    }
                    else {
                        e1.motion.reverseY();
                        e2.motion.reverseY();
                    }
                    break;
                default:
            }

        }
    }

    private void detectCollisions(List<Entity> entities) {
        for (int i = 0; i < entities.size() - 1; ++i) {
            Entity e1 = entities.get(i);

            if(!e1.bBox.isEnabled())
                continue;

            for (int j = i + 1; j < entities.size(); ++j) {
                Entity e2 = entities.get(j);

                if(!e2.bBox.isEnabled() || e1 == e2)
                    continue;

                if(e1.getTag().equals("monster") &&
                    e2.getTag().equals("monster"))
                    continue;

                e1.bBox.setColor(BoundingBox.cNormal);
                e2.bBox.setColor(BoundingBox.cNormal);

                boolean cs = detectCollision(e1, e2);

                if(cs) {
                    collisions.add(new Pair<>(e1, e2));
                }
            }
        }
    }

    private static boolean detectCollision(Entity e1, Entity e2) {
        Vector2 pa0 = e1.bBox.getP0();
        Vector2 pa1 = e1.bBox.getP1();
        Vector2 pb0 = e2.bBox.getP0();
        Vector2 pb1 = e2.bBox.getP1();

        boolean ok = BoundingBox.isIntersected(pa0, pa1, pb0, pb1);

        return ok;
    }
}
