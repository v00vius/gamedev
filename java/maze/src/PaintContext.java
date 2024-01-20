
import imgui.*;

public class PaintContext {
public Vector2 windowPosition;
public Vector2 windowsSize;
public ImDrawList drawList;
public PaintContext()
{
        windowPosition = new Vector2();
        windowsSize = new Vector2();
}
public void frame()
{
        ImGuiViewport vp = ImGui.getMainViewport();
        drawList = ImGui.getBackgroundDrawList(vp);
        windowPosition.set(vp.getWorkPos());
        windowsSize.set(vp.getWorkSize());
}
}
