package component;

import imgui.ImGui;

public class Opacity extends Component implements Action {
    private Draw draw;
    private float step;

    public Opacity() {
        super();

        draw = null;
        this.step = 0.f;
    }

    public void setDraw(Draw draw) {
        this.draw = draw;
    }

    public void blink(float period) {
        step = 2.f * ImGui.getIO().getDeltaTime() / period;
    }

    public float getOpacity() {
        return draw.getOpacityFactor();
    }
    public void setOpacity(float opacity) {
        draw.setOpacityFactor(opacity);
    }

    @Override
    public void action(Object o) {
        float opacity = getOpacity();

        opacity -= step;

        if(opacity < 0.f) {
            opacity = 0.f;
            step = -step;
        }
        else if(opacity > 1.f) {
            opacity = 1.f;
            step = -step;
        }

        setOpacity(opacity);
    }
}
