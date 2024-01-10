package system;

import component.WindowBounds;
import entity.Entity;
import service.EntityManager;

public class MovementSystem extends System {
    public MovementSystem() {
        super();
    }

    @Override
    public void task(EntityManager entityManager) {
        WindowBounds windowBounds = new WindowBounds();

        for (Entity e : entityManager.getEntities()) {
            if(e.rotation != null && e.mesh != null)
                e.rotation.action(e.mesh);

            if(e.motion != null && e.position != null) {
                e.motion.action(e.position);
                short whereIsBump = windowBounds.action(e.position);

                e.motion.bump(whereIsBump);
            }
        }
    }
}
