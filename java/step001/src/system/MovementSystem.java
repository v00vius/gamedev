package system;

import component.WindowBounds;
import entity.Entity;
import repo.EntityManager;

public class MovementSystem extends System {
    public MovementSystem() {
        super();
    }

    @Override
    public void task(EntityManager entityManager) {
        WindowBounds windowBounds = new WindowBounds();

        for (Entity e : entityManager.getEntities()) {
                e.rotation.exec();
                e.motion.exec();

                windowBounds.setPointToCheck(e.position.getCoordinate());
                short whereIsBump = windowBounds.exec();
                e.motion.bump(whereIsBump);
        }
    }
}
