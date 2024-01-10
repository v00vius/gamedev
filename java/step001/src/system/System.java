package system;

import service.EntityManager;

public abstract class System {
    private boolean enabled;

    public System() {
        enabled = true;
    }

    public boolean enabled(boolean[] en) {
        boolean prev = enabled;

        if(en != null)
            this.enabled = en[0];

        return prev;
    }
    public boolean isEnabled() {
        return enabled(null);
    }
    public void exec(EntityManager entityManager) {
        if(enabled)
            task(entityManager);
    }

    protected abstract void task(EntityManager entityManager);
}
