package system;

import entity.Entity;
import service.EntityManager;
import types.PaintContext;

import java.util.ArrayList;

public class PaintingSystem extends System {
    public PaintingSystem() {
    }

    @Override
    public void task(EntityManager entityManager) {
        PaintContext paintContext = new PaintContext();

        for (Entity e : entityManager.getEntities()) {
            if(e.painter == null)
                continue;

            if(e.opacity != null)
                e.opacity.action(e.painter);

            e.painter.setPaintContext(paintContext);
            e.painter.action(e.position);
        }
    }
}
