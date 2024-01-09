package types;

import imgui.*;

public class PaintContext {
    public Vector2 windowPosition;
    public ImDrawList drawList;

    public PaintContext() {
        ImGuiViewport vp = ImGui.getMainViewport();

        drawList =  ImGui.getBackgroundDrawList(vp);

        windowPosition = new Vector2(vp.getWorkPos());
    }
}
