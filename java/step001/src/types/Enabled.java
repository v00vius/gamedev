package types;

public abstract class Enabled {
    private boolean enabled;

    public Enabled() {
        enabled = true;
    }

    public boolean setEnabled(boolean[] en) {
        boolean prev = enabled;

        if(en != null)
            this.enabled = en[0];

        return prev;
    }
    public boolean isEnabled() {
        return setEnabled(null);
    }
}
