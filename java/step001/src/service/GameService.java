package service;

import component.*;
import entity.Entity;
import imgui.ImGui;
import repo.EntityManager;
import repo.MeshManager;
import scene.Scene;
import system.CollisionSystem;
import system.GameSystem;
import system.MovementSystem;
import system.PaintingSystem;
import types.Color;
import types.String8;
import types.Vector2;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameService {
    public GameService() {
    }

    public void createMeshSet (MeshManager mm) {
        Mesh m = mm.createFromFile("crab1.mesh");

        m.rotate((float) Math.toRadians(180.));
        m = mm.createFromFile("trident2.mesh");
        m.rotate((float) Math.toRadians(180.));

    }
    public int createGrass(EntityManager em, MeshManager mm,  int n) {
        Random rnd = new Random();
        Map<Long, Mesh> mset = mm.getMeshSet();
        int size = 0;

        Mesh m0 = new Mesh("gr0", new float[] {0, 40, 0},
                new float[] {-10, 0, 10},
                new short[] {0, 1, 2},
                new int[]{new Color(125, 88, 0).get()});

        Mesh grass = m0.clone();
        grass.setName("grass");

        float angle = (float) Math.toRadians(-30.f);

        m0.rotate(angle);
        grass.union(m0);

        m0.rotate(angle);
        grass.union(m0);

        m0 = grass.clone();
        m0.mirorX();
        grass.union(m0);

        while (n-- >= 0) {
            Entity e = em.createEntity("grass");

            e.mesh = grass;
            e.position = new Position(grass, rnd.nextFloat(50, 1920 - 50),
                    rnd.nextFloat(50, 1080-50));
            e.painter = new Painter(e.position, 0);

            e.position.enable();
            e.painter.enable();

            size += grass.size();
        }

        return size;
    }

    public int createEntities(EntityManager em, MeshManager mm,  int n) {
        Random rnd = new Random();
        Map<Long, Mesh> mset = mm.getMeshSet();
        int size = 0;


        while (n-- >= 0) {
            Entity e = em.createEntity(n % 2 == 0 ? "monster" : "forest");
            String name = n % 2 == 0 ? "crab1" : "trident";
            Mesh m = mset.get(String8.pack(name));

            e.mesh = m;
            e.position = new Position(m, rnd.nextFloat(50, 1920 - 50),
                    rnd.nextFloat(50, 1080-50));
            e.motion = new Motion(e.position);
            e.motion.setVelocity(rnd.nextFloat(-3, 3),
                    rnd.nextFloat(-2, 2));
            e.painter = new Painter(e.position, 0);
            e.bBox = new BoundingBox(e.position);

            if(n % 2 == 0)
                e.motion.enable();

            e.position.enable();
            e.painter.enable();
            e.bBox.enable();

            size += m.size();
        }

        return size;
    }

    public Scene createScene(EntityManager entityManager) {
        List<GameSystem> scene = new LinkedList<GameSystem>();

        scene.add(new MovementSystem());
        scene.add(new CollisionSystem());
        scene.add(new PaintingSystem());

        for(GameSystem g : scene) {
            g.enable();
        }

        return new Scene(scene, entityManager);
    }

}
