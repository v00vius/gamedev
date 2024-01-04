
import imgui.*;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.*;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import types.Vector2;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    private long time;
    private boolean done;
    private boolean blink = true;
    private long frameCount = 0;

    public Main() {
        super();

        time = System.currentTimeMillis();
        done = false;
    }

    public static void main(final String[] args) {
        launch(new Main());
        System.exit(0);
    }

    @Override
    protected void configure(final Configuration config) {
        config.setTitle("Test 001");
    }

    @Override
    public void process() {
        ++frameCount;
        mainMenu();

        ImGuiViewport vp = ImGui.getMainViewport();
        ImDrawList draw =  ImGui.getBackgroundDrawList(vp);
        ImVec2 vpPos = vp.getWorkPos();
        ImVec2 vpSize = vp.getWorkSize();
        ImGuiIO io = ImGui.getIO();
        ImVec2 center = vp.getWorkCenter();
        ImVec2 mp = io.getMousePos();


        draw.addCircle(center.x, center.y, 0.4f * vpSize.y,
                ImColor.rgb(0.f, 1.f, 0.f),
                22, 6
                );

        if(mp.x < vpPos.x || mp.x > vpPos.x + vpSize.x ||
                mp.y < vpPos.y || mp.y > vpPos.y + vpSize.y) {
            if(frameCount % 60 == 0)
                blink = !blink;

            if(blink)
                draw.addRect(vpPos.x, vpPos.y, vpPos.x + vpSize.x, vpPos.y + vpSize.y,
                        ImColor.rgb(1.f, 0.f, 0.f),
                        5.f, ImDrawFlags.None, 4.f);
        }
        else {
            draw.addCircle(mp.x, mp.y, 50.f,
                    ImColor.rgb(0.f, 0.f, 1.f),
                    22, 1
            );
        }

        if(io.getKeysDown(GLFW_KEY_F4)) {
            ImGui.text("F4 pressed");
        }

        ImGui.text("Key mods: ");

        if(io.getKeyAlt()) {
            ImGui.sameLine();
            ImGui.text("ALT");
        }

        if(io.getKeyShift()) {
            ImGui.sameLine();
            ImGui.text("SHIFT");
        }

        if(io.getKeyCtrl()) {
            ImGui.sameLine();
            ImGui.text("CTRL");
        }

        if(done) {
            io.addInputCharacter(GLFW_KEY_F4);  // F4
            io.addInputCharacter(GLFW_KEY_LEFT_ALT);  // ALT
            done = false;
        }

        if (ImGui.isMousePosValid()) {
            ImGui.text(String.format("Mouse pos: (%g, %g)",
                    io.getMousePosX(), io.getMousePosY()));
        }
        else
            ImGui.text("Mouse pos: <INVALID>");

        ImGui.text(String.format("Mouse delta: (%g, %g)",
                io.getMouseDeltaX(), io.getMouseDeltaY()));

        boolean[] mouseButtons = new boolean[3];
        io.getMouseDown(mouseButtons);

        ImGui.text("Mouse down: ");

        for (int i = 0; i < mouseButtons.length; ++i) {
            if (ImGui.isMouseDown(i)){
                ImGui.sameLine ();
                ImGui.text (String.format("button #%d", i));
            }
        }
    }

    private void mainMenu() {
        long tick  = System.currentTimeMillis() - time;
        float delta = ImGui.getIO().getDeltaTime();

        time += tick;

        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("File")) {
                ImGui.menuItem("[File menu]", null, false, false);

                if (ImGui.menuItem("Quit", "Alt+F4")) { done = true; }

                ImGui.endMenu();
            }

            ImGui.endMainMenuBar();
        }

        ImGui.text("Frame time: " + tick + " ms ("
                + (int)(0.5f + 1000.f / (float)tick) + " FPS), delta " + delta);
    }

    @Override
    protected void initImGui(final Configuration config) {
        super.initImGui(config);

        final ImGuiIO io = ImGui.getIO();

        io.setIniFilename(null);                                // We don't want to save .ini file
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);  // Enable Keyboard Controls
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);      // Enable Docking
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);    // Enable Multi-Viewport / Platform Windows
        io.setConfigViewportsNoTaskBarIcon(true);

//        initFonts(io);
    }
    /**
     * Example of fonts configuration
     * For more information read: https://github.com/ocornut/imgui/blob/33cdbe97b8fd233c6c12ca216e76398c2e89b0d8/docs/FONTS.md
     */
    private void initFonts(final ImGuiIO io) {
        io.getFonts().addFontDefault(); // Add default font for latin glyphs
/*

        // You can use the ImFontGlyphRangesBuilder helper to create glyph ranges based on text input.
        // For example: for a game where your script is known, if you can feed your entire script to it (using addText) and only build the characters the game needs.
        // Here we are using it just to combine all required glyphs in one place
        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder(); // Glyphs ranges provide
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesCyrillic());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesJapanese());
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange);

        // Font config for additional fonts
        // This is a natively allocated struct so don't forget to call destroy after atlas is built
        final ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setMergeMode(true);  // Enable merge mode to merge cyrillic, japanese and icons with default font

        final short[] glyphRanges = rangesBuilder.buildRanges();
        io.getFonts().addFontFromMemoryTTF(loadFromResources("Tahoma.ttf"), 14, fontConfig, glyphRanges); // cyrillic glyphs
        io.getFonts().addFontFromMemoryTTF(loadFromResources("NotoSansCJKjp-Medium.otf"), 14, fontConfig, glyphRanges); // japanese glyphs
        io.getFonts().addFontFromMemoryTTF(loadFromResources("fa-regular-400.ttf"), 14, fontConfig, glyphRanges); // font awesome
        io.getFonts().addFontFromMemoryTTF(loadFromResources("fa-solid-900.ttf"), 14, fontConfig, glyphRanges); // font awesome
        io.getFonts().build();

        fontConfig.destroy();
*/
    }

/*
    public static void main(String[] args) {

        EntityFromMemory loader = new EntityFromMemory(10,
                new String[] {
                    "Circle circle1 100 100 -3 2 255 0 0 50",
                    "Circle circle1 200 200 -2 -1 0 255 0 75"
                }
        );

        Entity[] shapes = loader.load();

        Vectortest();
    }
*/

    private static void Vectortest() {
        // Vector2 v1 = new Vector2(100, 100);
        // Vector2 v2 = new Vector2(1, 1);
        // Vector2 v3 = new Vector2();

        // v3.set(v1);
        // System.out.println("v3 = " + v3);
        // System.out.println("v2 = " + v2);

        // v3.add(v2).sub(v2).mul(0.5f);
        // System.out.println("((v3 += v2) -= v2) * 0.5: " + v3);
        // System.out.println("v3.length = " + v3.length());
        // System.out.println("v3 angle: " + v3.angle());
        // System.out.println("v3 angle: " + v3.angle());

        // v3.rotateRelative(-v3.angle());
        // System.out.println("v3 new angle: " + v3.angle() + ", v3=" + v3);
        // v3.rotateRelative(90f);
        // System.out.println("v3 rotation (+90 deg), angle: " + v3.angle() + ", v3=" + v3);

        // for(float a = 0; a <= 360; a += 30) {
        //     v1.projection(100, a);
        //     System.out.println("projection of 100, bearing " + a + ": " + v1);
        // }

//        Vector2 a1 = new Vector2(10, 10);
//        Vector2 b1 = new Vector2(50, 20);
//        Vector2 a2 = new Vector2(10, 50);
//        Vector2 b2 = new Vector2(40, 00);

//        Vector2 a1 = new Vector2(10, 10);
//        Vector2 b1 = new Vector2(50, 10);
//        Vector2 a2 = new Vector2(10, 50);
//        Vector2 b2 = new Vector2(40, 50);
        Vector2 a1 = new Vector2(000, 500);
        Vector2 b1 = new Vector2(200, 000);
        Vector2 a2 = new Vector2(200, 500);
        Vector2 b2 = new Vector2(000, 000);
        Vector2 vc = null;

        long msDuration = System.currentTimeMillis();

        float dAngle = (float)Math.toRadians(10.0);
        int counter = 0;

        for (float angle = 0.f; angle < Math.toRadians(361.f); angle += dAngle) {
            ++counter;
            vc = Vector2.isIntersect2(a1, b1, a2, b2);
            System.out.println(counter + ") a1=" + a1 + " b1=" + b1 + " a2=" + a2 + " b2=" + b2);    
            System.out.println("  intersection: " + vc);

            a1.rotateRelative(dAngle);
            b1.rotateRelative(dAngle);

            a2.rotateRelative(dAngle);
            b2.rotateRelative(dAngle);
        }

        msDuration = System.currentTimeMillis() - msDuration;

        // boolean ok = Vector2.isIntersect(a1, b1, a2, b2);

        // System.out.println("Intersection: " + vc);
        System.out.println("Duration: " + msDuration);
    }
}
