package gui;

import imgui.*;
import typedefs.Vector2;

public class PaintContext {
public Vector2 position;
public Vector2 center;
public Vector2 size;
public ImDrawList draw;

public PaintContext()
{
        position = new Vector2();
        center = new Vector2();
        size = new Vector2();
}
public void frame()
{
        ImGuiViewport vp = ImGui.getMainViewport();

        draw = ImGui.getBackgroundDrawList(vp);
        position.set(vp.getWorkPos());
        center.set(vp.getWorkCenter());
        size.set(vp.getWorkSize());
}
}
