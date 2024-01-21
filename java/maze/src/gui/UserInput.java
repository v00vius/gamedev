package gui;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiViewport;
import typedefs.Vector2;

import static org.lwjgl.glfw.GLFW.*;

public class UserInput {
        private boolean left;
        private boolean right;
        private boolean up;
        private boolean down;
        private boolean esc;
        private boolean alt;
        private float[] mouseDown;
        private Vector2 mousePosition;

        public UserInput()
        {
                left = false;
                right = false;
                up = false;
                down = false;
                esc = false;

                mouseDown = new float[]{-1.f, -1.f};
                mousePosition = new Vector2();
        }

        public boolean isLeft()
        {
                return left;
        }

        public boolean isRight()
        {
                return right;
        }

        public boolean isUp()
        {
                return up;
        }

        public boolean isDown()
        {
                return down;
        }

        public boolean isEsc()
        {
                return esc;
        }

        public boolean isAlt()
        {
                return alt;
        }

        public Vector2 getMousePosition()
        {
                return mousePosition;
        }

        public float getMouseDown(int i)
        {
                return mouseDown[i];
        }

        public void frame()
        {
                ImGuiIO io = ImGui.getIO();
                ImGuiViewport vp = ImGui.getMainViewport();
                Vector2 windowPosition = new Vector2(vp.getWorkPos());

                mousePosition.set(io.getMousePos()).sub(windowPosition);

                mouseDown(0);
                mouseDown(1);

                left = io.getKeysDown(GLFW_KEY_A);
                right = io.getKeysDown(GLFW_KEY_D);
                up = io.getKeysDown(GLFW_KEY_W);
                down = io.getKeysDown(GLFW_KEY_S);
                esc = io.getKeysDown(GLFW_KEY_ESCAPE);
                alt = io.getKeyAlt();

                ImGui.text("Keys: ");

                ImGui.sameLine();
                ImGui.text(left ? "LEFT" : ".");

                ImGui.sameLine();
                ImGui.text(right ? "RIGHT" : ".");

                ImGui.sameLine();
                ImGui.text(up ? "UP" : ".");

                ImGui.sameLine();
                ImGui.text(down ? "DOWN" : ".");

                ImGui.sameLine();
                ImGui.text(esc ? "ESC" : ".");

                ImGui.sameLine();
                ImGui.text(alt ? "ALT" : ".");

                ImGui.text(String.format("Mouse: x=%5.1f y=%5.1f", mousePosition.x, mousePosition.y));

                ImGui.text("Mouse down:");
                ImGui.sameLine();
                ImGui.text(String.format("b0 = %5.2f", mouseDown[0]));
                ImGui.sameLine();
                ImGui.text(String.format("b1 = %5.2f", mouseDown[1]));
        }

        private void mouseDown(int i)
        {
                if (ImGui.isMouseDown(i)) {
                        if (mouseDown[i] < 0.f) {
                                mouseDown[i] = 0.f;

                                return;
                        }

                        mouseDown[i] += ImGui.getIO().getDeltaTime();
                } else
                        mouseDown[i] = -1.f;
        }
}
