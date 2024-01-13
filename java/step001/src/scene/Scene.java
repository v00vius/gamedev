package scene;

import repo.EntityManager;
import system.GameSystem;

import java.util.List;

public class Scene {
    List<GameSystem> systems;
    EntityManager entityManager;

    public Scene(List<GameSystem> systems, EntityManager em) {
        this.systems = systems;
        this.entityManager = em;
    }

    public void frame() {
        for (GameSystem g : systems) {
            g.exec(entityManager);
        }
    }
}
