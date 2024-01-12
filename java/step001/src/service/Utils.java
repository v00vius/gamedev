package service;

import imgui.ImGui;
import imgui.ImVec2;

public class Utils {
    static public float getDeltaTime() {
        return ImGui.getIO().getDeltaTime();
    }

    static public double getTime() {
        return ImGui.getTime();
    }
    static public ImVec2 getWindowBounds() {
        return ImGui.getMainViewport().getWorkSize();
    }
}
