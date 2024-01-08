package component;

import imgui.ImGui;

public class Opacity extends Component  {
    private Painter painter;
    private float step;

    public Opacity() {
        super();

        painter = null;
        this.step = 0.f;
    }

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    public void blink(float period) {
        step = 2.f * ImGui.getIO().getDeltaTime() / period;
    }

    public float getOpacity() {
        return painter.getOpacityFactor();
    }
    public void setOpacity(float opacity) {
        painter.setOpacityFactor(opacity);
    }

    @Override
    public void action() {
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
