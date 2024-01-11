package system;

import entity.Entity;
import repo.EntityManager;
import types.PaintContext;

public class PaintingSystem extends GameSystem {
    public PaintingSystem() {
        super();
    }

    @Override
    public void task(EntityManager entityManager) {
        PaintContext paintContext = new PaintContext();

        for (Entity e : entityManager.getEntities()) {
            e.opacity.exec();
            e.painter.setPaintContext(paintContext);
            e.painter.exec();
        }
    }
}
