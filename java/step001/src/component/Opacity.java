package component;

import imgui.ImGui;

import java.util.function.Function;

public class Opacity extends Component  {
    private float step;
    private Function<Component, Short> opacity;

    public Opacity() {
        super();

        this.step = 0.f;
        opacity = this::empty;
    }

    public void blink(float period) {
        step = 2.f * ImGui.getIO().getDeltaTime() / period;
        opacity = this::blinkExec;
    }

    public void decay(float period) {
        opacity = this::empty;
    }

    @Override
    public Short action(Component component) {
        return opacity.apply(component);
    }
    private Short empty(Component component) {
        return 0;
    }
    private Short blinkExec(Component component) {
        Painter painter = (Painter) component;
        float opacity = painter.getOpacityFactor();

        opacity -= step;

        if(opacity < 0.f) {
            opacity = 0.f;
            step = -step;
        }
        else if(opacity > 1.f) {
            opacity = 1.f;
            step = -step;
        }

        painter.setOpacityFactor(opacity);
        return null;
    }
}
