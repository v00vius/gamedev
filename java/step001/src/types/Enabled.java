package types;

public abstract class Enabled {
    private boolean enabled;

    public Enabled() {
        disable();
    }

    public boolean setEnabled(boolean[] en) {
        boolean prev = enabled;

        if(en != null)
            this.enabled = en[0];

        return prev;
    }
    public boolean enable() {
        boolean prev = enabled;

        enabled = true;

        return prev;
    }
    public boolean disable() {
        boolean prev = enabled;

        enabled = false;

        return prev;
    }
    public boolean isEnabled() {
        return enabled;
    }
}
