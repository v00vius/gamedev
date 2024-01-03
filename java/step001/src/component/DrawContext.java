package component;

import imgui.*;

import types.Context;
import types.Node;

public class DrawContext implements Context  {
    public ImVec2 screenPosition;
    public ImDrawList drawList;
    public int color;
    public int generation;

    public DrawContext() {
        screenPosition = ImGui.getCursorScreenPos();
        drawList = ImGui.getWindowDrawList();
        color = ImColor.rgb(255, 255, 0);
    }

    @Override
    public void function(Node node) {
/*
        int a = generation - node.getMask();

        if(a > 255)
            a = 0;
        else
            a = 255 - generation;
*/

        color = ImColor.rgb(255, 255, 0);
        ((Shape)node).draw(this);
    }
}
