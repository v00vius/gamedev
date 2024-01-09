package entity;

import component.Mesh;
import component.Painter;
import component.Position;
import component.Rotation;
import types.String8;

import java.util.Objects;

public class Entity {
    private short id;
    private Long tagId;
    private boolean alive;

    public Mesh mesh;
    public Position position;
    public Painter painter;
    public Rotation rotation;

    public Entity(short id, String tag) {
        this.id = id;
        this.tagId = String8.pack(tag);
        this.alive = true;
    }

    public short getId() {
        return id;
    }

    public String getTag() {
        return String8.unpack(tagId);
    }
    public Long getTagId() {
        return tagId;
    }

    public boolean isalive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
