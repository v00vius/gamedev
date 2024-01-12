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

    public int createEntities(EntityManager em, MeshManager mm,  int n) {
        Random rnd = new Random();
        Map<Long, Mesh> mset = mm.getMeshSet();
        int size = 0;


        while (n-- >= 0) {
            Entity e = em.createEntity(n % 2 == 0 ? "monster" : "forest");
            String name = n % 2 == 0 ? "crab1" : "trident";
            Mesh m = mset.get(String8.pack(name));

            e.mesh = m;
            e.position = new Position(m, rnd.nextFloat(20, 200),
                    rnd.nextFloat(20, 200));
            e.motion = new Motion(e.position);
            e.motion.setVelocity(rnd.nextFloat(-3, 3),
                    rnd.nextFloat(-2, 2));
            e.painter = new Painter(e.position, 0);
            e.bBox = new BoundingBox(e.position);

//            if(n % 2 == 0)
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
