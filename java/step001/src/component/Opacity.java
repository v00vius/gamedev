package component;

import imgui.ImGui;

import java.util.function.Function;

public class Opacity extends Component  {
    private Painter painter;
    private float step;
    private Function<Component, Short> opacity;

    public Opacity(Painter painter) {
        super();

        this.painter = painter;
        this.step = 0.f;
        opacity = this::stepEmpty;
    }

    public void blink(float period, float deltaTime) {
        opacity = this::stepBlink;
        step = 2.f * deltaTime / period;
    }

    public void decay(float lifeTime, float restTime, float deltaTime) {
        opacity = this::stepDecay;
        int totalSteps = (int)(0.5f + lifeTime / deltaTime);
        int restSteps = (int)(0.5f + restTime / deltaTime);
    }

    @Override
    protected Short action(Component component) {
        return opacity.apply(painter);
    }
    private Short stepEmpty(Component component) {
        return 0;
    }
    private Short stepDecay(Component component) {
        return  1;
    }

    private Short stepBlink(Component component) {
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

        return 1;
    }
}
