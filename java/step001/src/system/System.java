package system;

import repo.EntityManager;
import types.Enabled;

public abstract class System  extends Enabled {
    public System() {
        super();
    }

    public void exec(EntityManager entityManager) {
        if(isEnabled())
            task(entityManager);
    }

    protected abstract void task(EntityManager entityManager);
}
