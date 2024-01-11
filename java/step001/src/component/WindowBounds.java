package component;

import imgui.ImGui;
import imgui.ImVec2;
import types.Vector2;

public class WindowBounds extends Component {
    public static short NONE = 0;
    public static short LEFT = 1;
    public static short RIGHT = 2;
    public static short TOP = 3;
    public static short BOTTOM = 4;

    private Vector2 bounds;

    public WindowBounds() {
        super();

        bounds = new Vector2();
        setBounds();
    }

    public Vector2 setBounds() {
        return bounds.set(ImGui.getMainViewport().getWorkSize());
    }
    public Vector2 getBounds() {
        return bounds;
    }

    @Override
    protected Short action(Component component) {
        Vector2 point = ((Position)component).getCoordinate();

        if(point.x < 0.f) {
            point.x = 0.f;

            return LEFT;
        }

        if(point.x > bounds.x) {
            point.x = bounds.x;

            return RIGHT;
        }

        if(point.y < 0.f) {
            point.y = 0.f;

            return TOP;
        }

        if(point.y > bounds.y) {
            point.y = bounds.y;

            return BOTTOM;
        }

        return NONE;
    }
}
