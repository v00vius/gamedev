package component;

import repo.EntityManager;
import types.Enabled;

public abstract class Component extends Enabled {
    public Component() {
        super();
    }
    public Short exec(Component component) {
        return isEnabled() ? action(component) : -1;
    }

    protected abstract Short action(Component component);
}
