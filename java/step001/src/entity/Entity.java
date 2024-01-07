package entity;

import types.String8;

public abstract class Entity {
    private Long id;
    private boolean alive;

    public Entity(String tag) {
        this.id = String8.pack(tag);
        this.alive = true;
    }
    
    public Long getId() {
        return id;
    }

    public String getTag() {
        return String8.unpack(id);
    }

    public boolean isalive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
