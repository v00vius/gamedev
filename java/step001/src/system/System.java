package system;

import service.EntityManager;

public abstract class System implements Task {
    private boolean enabled;

    public System() {
        enabled = true;
    }
}
