package component;

import imgui.ImGui;

public class Opacity implements Component {
    private float opacity;
    private float step;

    public Opacity() {
        this.opacity = 1.f;
        this.step = 0.f;
    }

    public void blink(float period) {
        step = 2.f * ImGui.getIO().getDeltaTime() / period;
    }

    public float getOpacity() {
        return opacity;
    }

    @Override
    public void action(Mesh mesh) {
        opacity -= step;

        if(opacity < 0.f) {
            opacity = 0.f;
            step = -step;
        }
        else if(opacity > 1.f) {
            opacity = 1.f;
            step = -step;
        }
    }
}
