package component;

import imgui.ImGui;
import imgui.ImGuiViewport;
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

        bounds = new Vector2(ImGui.getMainViewport().getWorkSize());
    }

    public void setBounds(ImVec2 bounds) {
        this.bounds.set(bounds);
    }
    public Vector2 getBounds() {
        return bounds;
    }

    @Override
    public short action(Component component) {
        if(component == null)
            return NONE;

        Vector2 position = ((Position)component).getPosition();

        if(position.x < 0.f) {
            position.x = 0.f;

            return LEFT;
        }

        if(position.x > bounds.x) {
            position.x = bounds.x;

            return RIGHT;
        }

        if(position.y < 0.f) {
            position.y = 0.f;

            return TOP;
        }

        if(position.y > bounds.y) {
            position.y = bounds.y;

            return BOTTOM;
        }

        return NONE;
    }
}
