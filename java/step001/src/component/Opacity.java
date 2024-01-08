package component;

import imgui.ImGui;

public class Opacity extends Component  {
    private float step;

    public Opacity() {
        super();

        this.step = 0.f;
    }

    public void blink(float period) {
        step = 2.f * ImGui.getIO().getDeltaTime() / period;
    }

    @Override
    public short action(Component component) {
        if(component == null)
            return 0;

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

        return 1;
    }
}
