package system;

import component.WindowBounds;
import entity.Entity;
import repo.EntityManager;

public class MovementSystem extends GameSystem {
    public MovementSystem() {
        super();
    }

    @Override
    public void task(EntityManager entityManager) {
        WindowBounds windowBounds = new WindowBounds();
        windowBounds.enable();

        for (Entity e : entityManager.getEntities()) {
            if(!e.isAlive())
                continue;

            e.rotation.frame();
            e.motion.frame();

            windowBounds.setBoundingBox(e.bBox);
            short whereIsBump = windowBounds.frame();
            e.motion.bump(whereIsBump);
        }
    }
}
