package entity;

import component.Component;
import component.ComponentList;
import component.ComponentNode;

public abstract class Entity {
    private int id;
    private ComponentList components;
    private String tag;
    private boolean active;

    public Entity(int id, String tag) {
        this.id = id;
        this.tag = tag;
        components = new ComponentList();
        active = true;
    }

    public Entity addComponent(ComponentNode component) {
        components.insert(component);
        return this;
    }
    public Entity removeComponent(ComponentNode component) {
        components.remove(component);

        return this;
    }
    public int getId() {
        return id;
    }

    public ComponentList getComponents() {
        return components;
    }

    public String getTag() {
        return tag;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
