package component;

import imgui.ImVec2;
import types.Vector2;

public class WindowBounds extends Component {
    private Vector2 bounds;

    public WindowBounds() {
        super();

        bounds = new Vector2();
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
            return 0;

        Vector2 position = ((Position)component).getPosition();

        if(position.x < 0.f)
            position.x = 0.f;

        if(position.x > bounds.x)
            position.x = bounds.x;

        if(position.y < 0.f)
            position.y = 0.f;

        if(position.y > bounds.y)
            position.y = bounds.y;

        return 0;
    }
}
