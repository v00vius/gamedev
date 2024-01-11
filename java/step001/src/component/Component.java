package component;

import repo.EntityManager;
import types.Enabled;

import java.util.LinkedList;
import java.util.List;

public abstract class Component extends Enabled {
    static private List<Component> components = new LinkedList<Component>();
    public Component() {
        super();
        components.add(this);
    }
    public Short exec() {
        return isEnabled() ? action() : 0;
    }

    protected abstract Short action();
}
