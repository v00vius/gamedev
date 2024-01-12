package component;

import imgui.ImGui;
import service.Utils;
import types.Vector2;

public class WindowBounds extends Component {
    public static short NONE = 0;
    public static short LEFT = 1;
    public static short RIGHT = 2;
    public static short TOP = 3;
    public static short BOTTOM = 4;

    private Vector2 bounds;
    private Vector2 point;
    private BoundingBox boundingBox;


    public WindowBounds() {
        super();

        bounds = new Vector2();
        setBounds();
    }

    public Vector2 setBounds() {
        return bounds.set(Utils.getWindowBounds());
    }

    public Vector2 getBounds() {
        return bounds;
    }

    public void setPointToCheck(Vector2 point) {
        this.point = point;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    @Override
    protected Short action() {
        setBounds();

        Vector2 p0 = boundingBox.getP0();
        Vector2 p1 = boundingBox.getP1();


        float dx = 0.f, dy = 0.f;
        short dir = NONE;

        if(p0.x < 0.f) {
//            dx = -p0.x;
            return LEFT;
        }

        else if(p1.x > bounds.x) {
//            dx = bounds.x - p1.x;
            return RIGHT;
        }

        else if(p0.y < 0.f) {
//            dy = -p0.y;
            return TOP;
        }

        else if(p1.y > bounds.y) {
//            dy = bounds.y - p1.y;
            return BOTTOM;
        }

//        Vector2 v = boundingBox.getPosition().getCoordinate().add(dx, dy);


        return NONE;
    }
}
