package system;

import entity.Entity;
import repo.EntityManager;

import java.util.List;

public class CleanUpSystem extends GameSystem {
        private int frequency;
        private int counter;

        public CleanUpSystem(int frequency)
        {
                super();

                this.frequency = frequency;
                this.counter = 0;
        }

        @Override
        protected void task(EntityManager entityManager)
        {
                if (++counter % frequency == 0) {
                        entityManager.getEntities().removeIf(Entity::isDead);
                        entityManager.getTaggedAs("bullet").removeIf(Entity::isDead);
                        entityManager.getTaggedAs("monster").removeIf(Entity::isDead);
                        entityManager.getTaggedAs("explode").removeIf(Entity::isDead);
                }
        }
}
