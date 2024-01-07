
import component.*;
import imgui.*;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.*;
import types.Vector2;

import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    static int cLow =  ImColor.rgb(253, 199, 2);
    static int cHigh = ImColor.rgb(89, 45, 190);
    float delta = 1.f;
    private boolean done;
    private boolean blink = true;
    private long frameCount = 1;

    private WindowBounds bounds;
    private Draw painter;
    private Rotation rotation;
    private Move motion;
    private Opacity opacity;

    private Mesh trident = new Mesh("Trident",
                new float[] {00.f, 10.f, 20.f, 30.f, 40.f, 50.f, 60.f,  30.f},
                new float[] {00.f, 50.f, 00.f, 50.f, 00.f, 50.f, 00.f, -20.f},
                new short[]   {
                        0, 1, 2,
                        2, 3, 4,
                        4, 5, 6,
                        0, 6, 7
                },
                new int[] {
                        cLow,
                        cLow,
                        cLow,
                        cHigh
                }
            );

    public Main() {
        super();

        done = false;
    }

    public static void main(final String[] args) {
        launch(new Main());
        System.exit(0);
    }

    @Override
    protected void configure(final Configuration config) {
        config.setTitle("ImGUI Version " + ImGui.getVersion() + ", https://github.com/SpaiR/imgui-java");


        System.out.println(trident);
        Mesh.store(trident, "trident2.mesh");
        Mesh mesh = Mesh.load("trident2.mesh");
        System.out.println(mesh);
        String t1 = trident.getName();
        String t2 = mesh.getName();
        System.out.println("Tags: '" + t1 + "' <==> '" + t2 + "'" );
        boolean eq = trident.equals(mesh);
        System.out.println("Equals: " + eq);
    }

    @Override
    public void process() {
        mainMenu();

        ImGuiViewport vp = ImGui.getMainViewport();
        ImDrawList draw =  ImGui.getBackgroundDrawList(vp);
        ImVec2 vpPos = vp.getWorkPos();
        ImVec2 vpSize = vp.getWorkSize();
        ImGuiIO io = ImGui.getIO();
        ImVec2 center = vp.getWorkCenter();
        ImVec2 mp = io.getMousePos();

/*
        draw.addCircle(center.x, center.y, 0.4f * vpSize.y,
                ImColor.rgb(0.f, 1.f, 0.f),
                22, 6
                );
*/
        ImFont font = ImGui.getFont();
        draw.addText(font, 24.f, vpPos.x, vpPos.y, ImColor.rgb(255, 255, 0),
                String.format("%05d", ImGui.getFrameCount())
        );

        if(ImGui.isMouseDown(0)) {
            Random rnd = new Random();

            if (motion == null) {
                motion = new Move();
                motion.setMesh(trident);
                motion.setPosition(vpSize.x * 0.5f, vpSize.y * 0.5f);
                motion.setVelocity(
                        rnd.nextFloat(-5.f, 5.f),
                        rnd.nextFloat(-5.f, 5.f)
                );

                rotation = new Rotation();
                rotation.setMesh(trident);

                bounds = new WindowBounds();
                bounds.setComponent(motion);

                painter = new Draw();
                painter.setMesh(trident);
                painter.color = ImColor.rgb(253, 199, 2);

                opacity = new Opacity();
                opacity.setDraw(painter);
            }

            rotation.setAngle(360.f * delta / rnd.nextFloat(1.f, 5.f));
            opacity.blink(3.f);
        }

        if(motion != null) {
            bounds.setBounds(vpSize);
            bounds.action(trident);

            rotation.action(trident);
            motion.action(trident);
            opacity.action(trident);
        }

        if(painter != null) {
            painter.drawList = draw;
            painter.position.x = vpPos.x + motion.getPositionX();
            painter.position.y = vpPos.y + motion.getPositionY();

            ImGui.text(String.format("Opacity Factor: %4.2f", opacity.getOpacity()));

            painter.setOpacityFactor(opacity.getOpacity());
//            painter.setOpacityFactor(0.5f);
            painter.action(trident);
        }

        if(mp.x < vpPos.x || mp.x > vpPos.x + vpSize.x ||
                mp.y < vpPos.y || mp.y > vpPos.y + vpSize.y) {
            if(frameCount % 60 == 0)
                blink = !blink;

            if(blink)
                draw.addRect(vpPos.x, vpPos.y, vpPos.x + vpSize.x, vpPos.y + vpSize.y,
                        ImColor.rgb(1.f, 0.f, 0.f),
                        5.f, ImDrawFlags.None, 5.f);
        }
        else {

            draw.addCircle(mp.x, mp.y, 40.f,
                    ImColor.rgb(0.f, 0.f, 1.f),
                    22, 2.f
            );
            draw.addLine(mp.x - 50.f, mp.y,
                    mp.x + 50.f, mp.y,
                    ImColor.rgb(0.f, 0.f, 1.f),
                    1.f
            );
            draw.addLine(mp.x, mp.y - 50.f,
                    mp.x, mp.y + 50.f,
                    ImColor.rgb(0.f, 0.f, 1.f),
                    1.f
            );
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
            if (ImGui.isMouseDown(i)) {
                ImGui.sameLine ();
                ImGui.text (String.format("button #%d", i));
            }
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

        if(io.getKeysDown(GLFW_KEY_F4)) {
            ImGui.text("F4 pressed");
        }
    }

    private void mainMenu() {
        if(++frameCount % 100 == 0)
            delta = ImGui.getIO().getDeltaTime();

        ImGui.text(String.format("Frame time: %5.1f ms", delta * 1000.f));
        ImGui.text("FPS: " + (int)(0.5f + 1.f / delta));
        ImGui.text( String.format("Time: %5.1f ", ImGui.getTime()));

        ImGui.colorEdit3("Background Color", this.getColorBg().data);

        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("File")) {
                ImGui.menuItem("[File menu]", null, false, false);

                if (ImGui.menuItem("Quit", "Alt+F4")) { done = true; }

                ImGui.endMenu();
            }

            ImGui.endMainMenuBar();
        }

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

            a1.rotate(dAngle);
            b1.rotate(dAngle);

            a2.rotate(dAngle);
            b2.rotate(dAngle);
        }

        msDuration = System.currentTimeMillis() - msDuration;

        // boolean ok = Vector2.isIntersect(a1, b1, a2, b2);

        // System.out.println("Intersection: " + vc);
        System.out.println("Duration: " + msDuration);
    }
}
