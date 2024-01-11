package system;

import entity.Entity;
import repo.EntityManager;
import types.PaintContext;

public class PaintingSystem extends System {
    public PaintingSystem() {
        super();
    }

    @Override
    public void task(EntityManager entityManager) {
        PaintContext paintContext = new PaintContext();

        for (Entity e : entityManager.getEntities()) {
            if(!e.painter.isEnabled())
                continue;

            e.opacity.exec(e.painter);
            e.painter.setPaintContext(paintContext);
            e.painter.exec(e.position);
        }
    }
}
