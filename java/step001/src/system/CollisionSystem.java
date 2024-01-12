package system;

import entity.Entity;
import imgui.ImGui;
import repo.EntityManager;
import types.Triple;
import types.Vector2;

import java.util.*;

public class CollisionSystem extends GameSystem {
    static private int direction = 0;
    private List<Triple<Entity, Entity, Vector2>> collisions;

    public CollisionSystem() {
        super();

        collisions = new LinkedList<>();
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

        for (Triple<Entity, Entity, Vector2> c : getCollisions()) {
            Entity e1 = c.first;
            Entity e2 = c.second;
            Vector2 crossSection = c.third;

            switch (++direction % 3) {
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

                if(!e2.bBox.isEnabled())
                    continue;

                Vector2 cs = detectCollision(e1, e2);

                if(cs != null) {
                    collisions.add(new Triple<>(e1, e1, cs));
                }
            }
        }
    }

    private static Vector2 detectCollision(Entity e1, Entity e2) {
        Vector2 crossSection = new Vector2(e1.bBox.getSize());

        crossSection.add(e2.bBox.getSize())
                .sub(e1.bBox.getPosition()
                        .sub(e2.bBox.getPosition())
                        .abs()
                );

        if(crossSection.x  < 0.f || crossSection.y < 0.f)
            return null;

        ImGui.text(String.format("cross {%5.1f, %5.1f}", crossSection.x, crossSection.y));

        return crossSection;
    }
}
