

import graph.AlgoDFS;
import graph.GraphBuilder;
import graph.Maze2D;
import gui.MazePainter;
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


public class Main extends Application {
private enum State {
        NONE,
        BUILDING_MAZE,
        INIT_PATH,
        BUILDING_PATH,
        BUILT
}
private UserInput userInput;
private PaintContext paintContext;
private Painter painter;
private long msDuration;
private Color gridColor;
private MazePainter mazePainter;
private Maze2D maze;
private int lastPoint = -1;
private boolean done;
private boolean pause;
private long frameCount = 0;
private float frameTime = 0.f;
private State state;
private GraphBuilder pathBuilder;
private AlgoDFS dfs;

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
        done = false;
        pause = false;

        userInput = new UserInput();
        paintContext = new PaintContext();
        painter = new Painter();
        painter.setContext(paintContext);
        gridColor = new Color((float)29 / 255.f, (float)43 / 255.f, (float)43 / 255.f, 1.f);
        mazePainter = new MazePainter(96.f);
        lastPoint = 0;
        state = State.BUILDING_MAZE;
        pathBuilder = new GraphBuilder();
        dfs = new AlgoDFS();
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

        mazeFrame();

        mazePainter.paint(maze, painter,
                ImColor.rgb(gridColor.getRed(), gridColor.getGreen(), gridColor.getBlue())
        );

        if(state == State.BUILDING_PATH || state == State.BUILT)
                mazePainter.paint(dfs, maze, painter);

//        painter.grid(0.f, 0.f, rows, cols, cell_size,
//                        ImColor.rgb(gridColor.getRed(), gridColor.getGreen(), gridColor.getBlue())
//        );
        mainMenu();
}
private void mazeFrame()
{
        if(pause)
                return;

        if(state == State.BUILDING_MAZE) {
                if(buildMaze()) {
                        state = State.INIT_PATH;
//                        state = State.BUILDING_MAZE;
                }

        } else if (state == State.INIT_PATH) {
                pathBuilder.init(maze);

                long[] graph = pathBuilder.getGraph();

                dfs.init(graph, (short) 0, (short)(maze.getCols() * maze.getRows() - 1));
                state = State.BUILDING_PATH;

        } else if (state == State.BUILDING_PATH) {
                ImGui.text("Path: " + dfs.getPath().size() + " steps");

                if(dfs.step())
                        state = State.BUILT;
        } else if (state == State.BUILT) {
                ImGui.text("Path has been built: " + dfs.getPath().size() + " steps");
                state = State.BUILDING_MAZE;
        }
}
private boolean buildMaze()
{
        if(lastPoint == 0)
                mazeInit();

        int n = 10;

        while(n-- > 0 && lastPoint != 0)
                lastPoint = maze.step(lastPoint);

        return lastPoint == 0;
}
private void mazeInit()
{
        float cell_size = mazePainter.getCellSize();
        int cols = (int)(paintContext.size.x / cell_size);
        int rows = (int)(paintContext.size.y / cell_size);

        maze = new Maze2D(rows, cols);
        lastPoint = maze.init();
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

        ImGui.text(String.format("Frame time: %5.1f ms", frameTime * 1000.f));
        ImGui.text("FPS: " + (int) (0.5f + 1.f / frameTime));
        ImGui.text(String.format("Time: %5.1f ", ImGui.getTime()));

        ImGui.colorEdit3("Background Color", this.getColorBg().data);
        ImGui.colorEdit3("Grid color", gridColor.data);
        ImGui.text(String.format("Maze (%d x %d): graph size: %d, wave size: %d",
                maze.getCols(), maze.getRows(),
                maze.getGraph().size(),
                maze.getWave().size())
        );

        if(ImGui.button(pause ? "Continue" : " Pause  ")) {
                pause = !pause;
        }

        if (ImGui.beginMainMenuBar()) {
                if (ImGui.beginMenu("File")) {
                        ImGui.menuItem("[File menu]", null, false, false);

                        if (ImGui.menuItem("Quit", "Alt+F4")) {
                                done = true;
                        }

                        ImGui.endMenu();
                }

                if (ImGui.beginMenu("Settings")) {
                        if (ImGui.menuItem("Stats", "S")) {
                        }

                        ImGui.endMenu();
                }

                if (ImGui.beginMenu("Help")) {
                        if (ImGui.menuItem("Controls", "C")) {
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
