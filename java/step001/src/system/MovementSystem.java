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
                e.rotation.exec(e.mesh);
                e.motion.exec(e.position);
                short whereIsBump = windowBounds.exec(e.position);
                e.motion.bump(whereIsBump);
            }
        }
    }
}
