package component;

import imgui.ImVec2;
import types.Vector2;

public class WindowBounds extends Component implements Action {
    private Vector2 bounds;
    private Move motion;

    public WindowBounds() {
        super();
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
    public void action(Object o) {
        if(motion.getPositionX() <= 0.f || motion.getPositionX() >= bounds.x)
            motion.reverseX();

        if(motion.getPositionY() <= 0.f || motion.getPositionY() >= bounds.y)
            motion.reverseY();
    }
}
