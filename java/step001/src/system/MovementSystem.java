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
                e.rotation.frame();
                e.motion.frame();

                windowBounds.setPointToCheck(e.position.getCoordinate());
                short whereIsBump = windowBounds.frame();
                e.motion.bump(whereIsBump);
        }
    }
}
