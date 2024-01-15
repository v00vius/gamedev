package system;

import component.Controller;
import component.WindowBounds;
import entity.Entity;
import imgui.ImGui;
import repo.EntityManager;

public class MovementSystem extends GameSystem {
        public MovementSystem()
        {
                super();
        }

        @Override
        public void task(EntityManager entityManager)
        {
                WindowBounds windowBounds = new WindowBounds();
                windowBounds.enable();

                for (Entity e : entityManager.getEntities()) {
                        if (!e.isAlive())
                                continue;

                        e.rotation.frame();
                        e.motion.frame();
                        e.controller.frame();

                        move(e);

                        windowBounds.setBoundingBox(e.bBox);
                        short whereIsBump = windowBounds.frame();
                        e.motion.bump(whereIsBump);
                }
        }

        private void move(Entity e)
        {
                Controller c = e.controller;

                if (!c.isEnabled() || !e.motion.isEnabled())
                        return;

                if (c.isUp())
                        e.position.getCoordinate().add(0.f, -5.f);

                if (c.isDown())
                        e.position.getCoordinate().add(0.f, 5.f);

                if (c.isLeft())
                        e.position.getCoordinate().add(-5.f, 0.f);

                if (c.isRight())
                        e.position.getCoordinate().add(5.f, 0.f);
        }
}
