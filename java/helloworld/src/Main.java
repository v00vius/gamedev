

import gui.PaintContext;
import gui.Painter;
import gui.UserInput;

import imgui.app.Application;
import imgui.app.Configuration;
import imgui.app.Color;
import imgui.*;
import imgui.flag.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Main extends Application {
private UserInput userInput;
private PaintContext paintContext;
private Painter painter;
private long msDuration;
private boolean pause;
private long frameCount = 0;
private float frameTime = 0.f;
private Color gridColor;;
private Random random = new Random(System.currentTimeMillis());

public Main()
{
        super();
}

public static void main(final String[] args)
{
        launch(new Main());
        System.exit(0);
}
@Override
protected void configure(final Configuration config)
{
        config.setTitle("ImGUI Version " + ImGui.getVersion() + ", https://github.com/SpaiR/imgui-java");
        config.setWidth(1366);
        config.setHeight(768);
//        config.setWidth(1920);
//        config.setHeight(1080);
}
@Override
protected void preRun()
{
        userInput = new UserInput();
        paintContext = new PaintContext();
        painter = new Painter();
        painter.setContext(paintContext);
        gridColor = new Color(1.f, 1.f, 1.f, 1.f);
}
@Override
protected void postRun() {
}

@Override
protected void preProcess()
{
        msDuration = System.currentTimeMillis();

        userInput.frame();
        paintContext.frame();
}

@Override
public void process()
{
        if(userInput.isEsc())
                pause = !pause;

        painter.grid(1.f, 1.f, (int)paintContext.size.y / 64, (int)paintContext.size.x / 64,
                64.f,
                ImColor.rgb(gridColor.getRed(), gridColor.getGreen(), gridColor.getBlue())
        );       

        mainMenu();
}

@Override
protected void postProcess()
{
        msDuration = System.currentTimeMillis() - msDuration;

        int fps = msDuration == 0 ? 999 : (int)(0.5f + 1000.f / (float) msDuration);

        ImGui.text(String.format("Scene latency: %d ms (%3d FPS)", msDuration, fps));
}
private void mainMenu()
{
        if (++frameCount % 100 == 0)
                frameTime = ImGui.getIO().getDeltaTime();

        ImGui.text("Hello!");
        
        ImGui.text(String.format("Frame time: %5.1f ms", frameTime * 1000.f));
        ImGui.text("FPS: " + (int) (0.5f + 1.f / frameTime));
        ImGui.text(String.format("Time: %5.1f ", ImGui.getTime()));

        ImGui.colorEdit3("Background Color", this.getColorBg().data);
        ImGui.colorEdit3("Grid Color", gridColor.data);

        if(ImGui.button(pause ? "Continue" : " Pause  ")) {
                pause = !pause;
        }

        if (ImGui.beginMainMenuBar()) {
                if (ImGui.beginMenu("File")) {
                        ImGui.menuItem("[File menu]", null, false, false);

                        if (ImGui.menuItem("Quit", "Alt+F4")) {
                        }

                        ImGui.endMenu();
                }

                ImGui.endMainMenuBar();
        }
}
@Override
protected void initImGui(final Configuration config)
{
        super.initImGui(config);

        final ImGuiIO io = ImGui.getIO();

        io.setIniFilename(null);                                // We don't want to save .ini file
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);  // Enable Keyboard Controls
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);      // Enable Docking
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);    // Enable Multi-Viewport / Platform Windows
        io.setConfigViewportsNoTaskBarIcon(true);

        initFonts(io);
}

private void initFonts(final ImGuiIO io)
{
        io.getFonts().addFontDefault(); // Add default font for latin glyphs
}
private static byte[] loadFromResources(String name)
{
        try {
                return Files.readAllBytes(Paths.get(Main.class.getResource(name).toURI()));
        } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
        }
}
}
