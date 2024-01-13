
import component.*;
import imgui.*;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.*;
import repo.EntityManager;
import repo.MeshManager;
import scene.Scene;
import service.GameService;
import service.Utils;
import types.Vector2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    private Scene scene;
    private EntityManager entityManager;
    private MeshManager meshManager;
    private int nVertices = 0;
    private boolean done = false;
    private long frameCount = 0;
    private float delta = 0.f;

    public Main() {
        super();
    }

    public static void main(final String[] args) {
        launch(new Main());
        System.exit(0);
    }

    @Override
    protected void preRun() {
        GameService service = new GameService();
        meshManager = new MeshManager();
        entityManager = new EntityManager();
        service.createMeshSet(meshManager);
        nVertices += service.createGrass(entityManager, meshManager, 70);
        nVertices += service.createForest(entityManager, meshManager, 20);
        nVertices += service.createMisc(entityManager, meshManager, 25);
        nVertices += service.createMonsters(entityManager, meshManager, 70);
        nVertices += service.createTower(entityManager, meshManager);
        scene = service.createScene(entityManager);
    }

    @Override
    protected void configure(final Configuration config) {
        config.setTitle("ImGUI Version " + ImGui.getVersion() + ", https://github.com/SpaiR/imgui-java");
        config.setWidth(1920);
        config.setHeight(1080);
    }

    @Override
    public void process() {
        long msDuration = System.currentTimeMillis();

        mainMenu();

        scene.frame();

        int numEntities = scene.getEntityManager().getEntities().size();

        msDuration = System.currentTimeMillis() - msDuration;
        ImGui.text(String.format("Entities: %5d, scene latency: %d ms (%5.1f FPS)",
                                    numEntities, msDuration, 1000.f / (float)msDuration));
    }
    private void mainMenu() {
        if(++frameCount % 100 == 0)
            delta = Utils.getDeltaTime();

        ImGui.text(String.format("Frame time: %5.1f ms", delta * 1000.f));
        ImGui.text("FPS: " + (int)(0.5f + 1.f / delta));
        ImGui.text( String.format("Time: %5.1f ", Utils.getTime()));
        ImGui.text(String.format("Vertices: %d", nVertices));

        ImGui.colorEdit3("Background Color", this.getColorBg().data);

        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("File")) {
                ImGui.menuItem("[File menu]", null, false, false);

                if (ImGui.menuItem("Quit", "Alt+F4")) { done = true; }

                ImGui.endMenu();
            }

            if (ImGui.beginMenu("Settings")) {
                if (ImGui.menuItem("Stats", "S")) {}

                ImGui.checkbox("Show Bounding boxes", BoundingBox.showBB);
                ImGui.endMenu();
            }

            if (ImGui.beginMenu("Help")) {
                if (ImGui.menuItem("Controls", "C")) {}

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

        initFonts(io);
    }
    private void initFonts(final ImGuiIO io) {
        io.getFonts().addFontDefault(); // Add default font for latin glyphs
    }
    private static byte[] loadFromResources(String name) {
        try {
            return Files.readAllBytes(Paths.get(Main.class.getResource(name).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
