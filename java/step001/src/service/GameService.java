package service;

import component.Mesh;
import component.Motion;
import component.Painter;
import component.Position;
import entity.Entity;
import repo.EntityManager;
import repo.MeshManager;
import scene.Scene;
import system.GameSystem;
import system.MovementSystem;
import system.PaintingSystem;
import types.Color;
import types.String8;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameService {
    public GameService() {
    }

    public void createMeshSet (MeshManager mm) {
        mm.createFromFile("crab1.mesh");
        mm.createFromFile("trident2.mesh");
    }

    public void createEntities(EntityManager em, MeshManager mm,  int n) {
        Random rnd = new Random();
        Map<Long, Mesh> mset = mm.getMeshSet();

        while (n-- >= 0) {
            Entity e = em.createEntity("monster");
            String name = n % 2 == 0 ? "crab1" : "trident";
            Mesh m = mset.get(String8.pack(name));

            e.mesh = m;
            e.position = new Position(m, rnd.nextFloat(50, 200),
                    rnd.nextFloat(50, 200));
            e.motion = new Motion(e.position);
            e.motion.setVelocity(rnd.nextFloat(-3, 3),
                    rnd.nextFloat(-3, 3));
            e.painter = new Painter(e.position, 0);

            e.motion.enable();
            e.position.enable();
            e.motion.enable();
            e.painter.enable();
        }
    }

    public Scene createScene(EntityManager entityManager) {
        List<GameSystem> scene = new LinkedList<GameSystem>();

        scene.add(new MovementSystem());
        scene.add(new PaintingSystem());

        for(GameSystem g : scene) {
            g.enable();
        }

        return new Scene(scene, entityManager);
    }
}
