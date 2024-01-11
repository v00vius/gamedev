package component;

import repo.EntityManager;
import types.Enabled;

public abstract class Component extends Enabled {
    public Component() {
        super();
    }
    public Short exec() {
        return isEnabled() ? action() : 0;
    }

    protected abstract Short action();
}
