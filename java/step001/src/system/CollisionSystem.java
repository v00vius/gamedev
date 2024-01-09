package system;

import entity.Entity;
import service.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class CollisionSystem implements Task {
    @Override
    public void task(EntityManager entityManager) {
        ArrayList<Entity> entities = entityManager.getEntities();

        for (int i = 0; i < entities.size() - 1; ++i) {
            Entity e1 = entities.get(i);

            if(e1.bBox == null)
                continue;

            for (int j = i + 1; j < entities.size(); ++j) {
                Entity e2 = entities.get(j);

                if(e2.bBox == null)
                    continue;

                if(1 == e1.bBox.action(e2.bBox)) {

                }
            }
        }
    }
}
