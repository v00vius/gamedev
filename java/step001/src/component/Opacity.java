package component;

import imgui.ImGui;

import java.util.function.Function;

public abstract class Opacity extends Component  {
    static public Opacity NIL = createEmpty();
    protected Painter painter;

    static private Opacity createEmpty() {
        return new Blink(Painter.NIL);
    }
    public Opacity(Painter painter) {
        super();

        this.painter = painter;
    }
    public void setOpacity(float opa) {
        painter.setOpacityFactor(opa);
    }
    public float getOpacity() {
        return painter.getOpacityFactor();
    }
}
