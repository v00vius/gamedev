package component;

import imgui.ImVec2;
import types.Context;
import types.Vector2;

public class Bounds implements Context {
    private Vector2 bounds;
    private Move motion;

    public Bounds() {
        bounds = new Vector2();
    }

    public void setBouds(ImVec2 bounds) {
        this.bounds.set(bounds);
    }

    public void setMotion(Move motion) {
        this.motion = motion;
    }

    @Override
    public void action(Mesh mesh) {
        if(motion.getX() <= 0.f || motion.getX() >= bounds.x)
            motion.reverseX();

        if(motion.getY() <= 0.f || motion.getY() >= bounds.y)
            motion.reverseY();
    }
}
