package system;

import component.*;
import component.mesh.Mesh;
import entity.Entity;
import imgui.ImGui;
import repo.EntityManager;
import types.Color;
import types.Pair;
import types.Vector2;

import java.util.*;

public class CollisionSystem extends GameSystem {
        static private int direction = 0;
        private List<Pair<Entity, Entity>> collisions;
        private EntityManager em;
        private Random rnd;

        public CollisionSystem()
        {
                super();

                collisions = new LinkedList<>();
                rnd = new Random();
        }

        public boolean hasCollisions()
        {
                return !collisions.isEmpty();
        }

        public List<Pair<Entity, Entity>> getCollisions()
        {
                return collisions;
        }

        @Override
        public void task(EntityManager entityManager)
        {
                em = entityManager;
                List<Entity> entities = entityManager.getEntities();

                collisions.clear();
                detectCollisions(entities);

                ImGui.text(String.format("Total collisions: %d", getCollisions().size()));

                for (Pair<Entity, Entity> c : getCollisions()) {
                        Entity e1 = c.first;
                        Entity e2 = c.second;

                        e1.bBox.setColor(BoundingBox.cCollision);
                        e2.bBox.setColor(BoundingBox.cCollision);

                        handleDamage(e1, e2);

                        switch (++direction % 3) {
                        case 0:
                                e1.motion.stepBack(1);
                                e2.motion.stepBack(1);
                                e1.motion.reverse();
                                e2.motion.reverse();
                                break;

                        case 1:
                                if (e1.motion.getVelocity().x * e2.motion.getVelocity().x >= 0.f) {
                                        e1.motion.reverseX();
                                        e2.motion.reverseY();
                                } else {
                                        e1.motion.reverseX();
                                        e2.motion.reverseX();
                                }
                                break;

                        case 2:
                                if (e1.motion.getVelocity().y * e2.motion.getVelocity().y >= 0.f) {
                                        e1.motion.reverseY();
                                        e2.motion.reverseX();
                                } else {
                                        e1.motion.reverseY();
                                        e2.motion.reverseY();
                                }
                                break;
                        default:
                        }

                }
        }

        private void handleDamage(Entity e1, Entity e2)
        {
                Entity monster = null;
                Entity bullet = null;

                if (e1.getTag().equals("monster"))
                        monster = e1;
                else if (e2.getTag().equals("monster"))
                        monster = e2;

                if (e1.getTag().equals("bullet"))
                        bullet = e1;
                else if (e2.getTag().equals("bullet"))
                        bullet = e2;

                if (monster == null)
                        return;

                if (bullet == null)
                        return;

                destroy(monster);
        }

        private void destroy(Entity monster)
        {
                monster.setAlive(false);

                Mesh exp = new Mesh("b0", new float[]{0, 10, 10, 0},
                        new float[]{0, 0, 10, 10},
                        new short[]{0, 1, 2, 0, 2, 3},
                        new int[]{new Color(255, 50, 50).get(),
                                new Color(0, 0, 255).get()
                        }
                );

                for (int i = 0; i < 50; ++i) {
                        createExplode(monster.position.getCoordinate(), exp);
                }

        }

        private void createExplode(Vector2 start, Mesh exp)
        {
                Entity e = em.createEntity("explode");

                e.mesh = exp;
                e.position = new Position(exp, start.x, start.y);
                e.motion = new Motion(e.position);
                e.motion.getVelocity().projection(rnd.nextFloat(1f, 7f ),
                        (float) Math.toRadians(rnd.nextFloat(0.f, 360.f)));

                e.painter = new Painter(e.position, 0);
                e.opacity = new Decay(e.painter, 0.5f, 0.2f);

                e.motion.enable();
                e.position.enable();
                e.painter.enable();
                e.opacity.enable();
        }

        private void detectCollisions(List<Entity> entities)
        {
                for (int i = 0; i < entities.size() - 1; ++i) {
                        Entity e1 = entities.get(i);

                        if (!e1.isAlive())
                                continue;

                        if (!e1.bBox.isEnabled())
                                continue;

                        if (e1.getTag().equals("grass") || e1.getTag().equals("misc"))
                                continue;

                        for (int j = i + 1; j < entities.size(); ++j) {
                                Entity e2 = entities.get(j);

                                if (!e2.isAlive())
                                        continue;

                                if (!e2.bBox.isEnabled())
                                        continue;

                                if (e2.getTag().equals("grass") || e2.getTag().equals("misc"))
                                        continue;

                                if (e1.getTag().equals("monster") && e2.getTag().equals("monster"))
                                        continue;

                                if (e1.getTag().equals("forest") && e2.getTag().equals("forest"))
                                        continue;

                                if (e1.getTag().equals("tower") && e2.getTag().equals("bullet"))
                                        continue;


                                e1.bBox.setColor(BoundingBox.cNormal);
                                e2.bBox.setColor(BoundingBox.cNormal);

                                boolean cs = detectCollision(e1, e2);

                                if (cs) {
                                        collisions.add(new Pair<>(e1, e2));
                                }
                        }
                }
        }

        private static boolean detectCollision(Entity e1, Entity e2)
        {
                Vector2 pa0 = e1.bBox.getP0();
                Vector2 pa1 = e1.bBox.getP1();
                Vector2 pb0 = e2.bBox.getP0();
                Vector2 pb1 = e2.bBox.getP1();

                boolean ok = BoundingBox.isIntersected(pa0, pa1, pb0, pb1);

                return ok;
        }
}
