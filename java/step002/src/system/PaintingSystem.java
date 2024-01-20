package system;

import component.BoundingBox;
import entity.Entity;
import imgui.ImGui;
import repo.EntityManager;
import types.Color;
import types.PaintContext;
import types.Vector2;

public class PaintingSystem extends GameSystem {
        public PaintingSystem()
        {
                super();
        }

        @Override
        public void task(EntityManager entityManager)
        {
                PaintContext paintContext = new PaintContext();
                int polygons = 0;

                for (Entity e : entityManager.getEntities()) {
                        polygons += e.mesh.size();

                        if (!e.isAlive())
                                continue;

                        if (e.opacity.frame() == 0)
                                e.setAlive(false);
                        ;

                        e.painter.setPaintContext(paintContext);
                        e.painter.frame();

                        if (e.bBox.isEnabled() && BoundingBox.getShowBB()) {
                                Vector2 p0 = e.bBox.getP0().add(paintContext.windowPosition);
                                Vector2 p1 = e.bBox.getP1().add(paintContext.windowPosition);

                                paintContext.drawList.addRect(p0.x, p0.y, p1.x, p1.y,
                                        e.bBox.getColor());
                        }
                }

                ImGui.text("Polygons: " + polygons);
        }
}
