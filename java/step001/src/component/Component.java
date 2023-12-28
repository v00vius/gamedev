package component;

import entity.Entity;

public abstract class Component {
    public Component() {
        this.next = this;
        this.referenceCount = 0;
        this.entity = null;
    }
    public Component(Entity e) {
        this.next = this;
        this.referenceCount = 0;
        this.entity = e;
    }

    public Component getNext() {
        return next;
    }
    public void setNext(Component next) {
        this.next = next;
    }
    public int getReferenceCount() {
        return referenceCount;
    }
    public int setReference() {
        return referenceCount++;
    }
    public Entity getEntity() {
        return entity;
    }
    protected Entity setEntity(Entity entity) {
        Entity prev = entity;

        this.entity = entity;

        return prev;
    }

    private Component next;
    private int referenceCount;
    private Entity entity;
}
