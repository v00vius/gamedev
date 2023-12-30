package entity;

public abstract class Entity {
    private int id;
    private String tag;
    private boolean alive;

    public Entity(int id, String tag) {
        this.id = id;
        this.tag = tag;
        this.alive = true;
    }
    
    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public boolean isalive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
