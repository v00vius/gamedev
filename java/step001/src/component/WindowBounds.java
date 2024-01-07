package component;

import imgui.ImVec2;
import types.Vector2;

public class WindowBounds implements Component {
    private Vector2 bounds;
    private Move motion;

    public WindowBounds() {
        bounds = new Vector2();
    }

    public void setBounds(ImVec2 bounds) {
        this.bounds.set(bounds);
    }

    public Vector2 getBounds() {
        return bounds;
    }

    public void setComponent(Move motion) {
        this.motion = motion;
    }

    @Override
    public void action(Mesh mesh) {
        if(motion.getPositionX() <= 0.f || motion.getPositionX() >= bounds.x)
            motion.reverseX();

        if(motion.getPositionY() <= 0.f || motion.getPositionY() >= bounds.y)
            motion.reverseY();
    }
}
