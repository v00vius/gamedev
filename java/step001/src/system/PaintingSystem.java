package system;

import entity.Entity;
import service.EntityManager;

import java.util.ArrayList;

public class PaintingSystem implements Task{
    public PaintingSystem() {
    }

    @Override
    public void task(EntityManager entityManager) {
        for (Entity e : entityManager.getEntities()) {
            if(e.painter == null)
                continue;

            e.painter.action(e.position);
        }
    }
}
