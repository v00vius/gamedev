package service;

import imgui.ImGui;

public class Utils {
    static public float getDeltaTime() {
        return ImGui.getIO().getDeltaTime();
    }

    static public double getTime() {
        return ImGui.getTime();
    }
}
