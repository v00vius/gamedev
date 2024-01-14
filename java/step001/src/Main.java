
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main extends Application {
    GameService service;
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
        service = new GameService();
        meshManager = new MeshManager();
        entityManager = new EntityManager();
        service.createMeshSet(meshManager);
        nVertices += service.createGrass(entityManager, meshManager, 70);
        nVertices += service.createForest(entityManager, meshManager, 20);
        nVertices += service.createMisc(entityManager, meshManager, 25);
        nVertices += service.createMonsters(entityManager, meshManager, 100);
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
    protected void preProcess() {
    }


    @Override
    public void process() {
        long msDuration = System.currentTimeMillis();

        mainMenu();

        scene.frame();

        msDuration = System.currentTimeMillis() - msDuration;

        int numEntities = scene.getEntityManager().getEntities().size();
        int numComponents = Component.createdTotal();

        ImGui.text(String.format("Entities: %d", numEntities));
        ImGui.text(String.format("Scene latency: %d ms (%5.1f FPS)",
                                    msDuration, 1000.f / (float)msDuration));
    }
    @Override
    protected void postProcess() {
        if(entityManager.getTaggedAs("monster").size() < 5)
            service.createMonsters(entityManager, meshManager, 10);
    }

    private void mainMenu() {
        if(++frameCount % 100 == 0)
            delta = Utils.getDeltaTime();

        ImGui.text(String.format("Frame time: %5.1f ms", delta * 1000.f));
        ImGui.text("FPS: " + (int)(0.5f + 1.f / delta));
        ImGui.text( String.format("Time: %5.1f ", Utils.getTime()));

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
