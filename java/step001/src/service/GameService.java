package service;

import component.*;
import entity.Entity;
import repo.EntityManager;
import repo.MeshManager;
import scene.Scene;
import system.*;
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

//        Mesh m = mm.createFromFile("monster.mesh");
//        m.rotate((float) Math.toRadians(180.));

//        m = mm.createFromFile("trident.mesh");
//        m.rotate((float) Math.toRadians(180.));

//        mm.createFromFile("grass.mesh");
//        mm.createFromFile("palm.mesh");
//        mm.createFromFile("misc.mesh");
    }

    public int createTower(EntityManager em, MeshManager mm) {
        Random rnd = new Random();
        Map<Long, Mesh> mset = mm.getMeshSet();
        int size = 0;

        Mesh tower = MeshManager.loadFromFile("trident.mesh");

        Entity e = em.createEntity("tower");

        e.mesh = tower;
        e.position = new Position(tower, rnd.nextFloat(50, 1920 - 50),
                rnd.nextFloat(50, 1080-50));
        e.painter = new Painter(e.position, 0);
        e.bBox = new BoundingBox(e.position);

        e.position.enable();
        e.painter.enable();
        e.bBox.enable();

        return tower.size();
    }
    public int createForest(EntityManager em, MeshManager mm,  int n) {
        Random rnd = new Random();
        Map<Long, Mesh> mset = mm.getMeshSet();
        int size = 0;

        Mesh p0 = new Mesh("p0", new float[] {0, -5, 0},
                new float[] {0, 0, 5},
                new short[] {0, 1, 2},
                new int[]{new Color(125, 88, 0).get()});

        Mesh palm = p0.clone();
        palm.union(p0);

        p0.translate(new Vector2(0.f, 5.f));
        palm.union(p0);

        p0.translate(new Vector2(0.f, 5.f));
        palm.union(p0);

        p0.translate(new Vector2(0.f, 5.f));
        palm.union(p0);

        p0 = palm.clone();
        p0.mirorX();
        palm.union(p0);

        p0 = new Mesh("p1", new float[] {0, -10, -20, -10},
                new float[] {0, 10, 0, 20},
                new short[] {0, 1, 3, 1, 2, 3},
                new int[]{
                        new Color(15, 244, 8).get(),
                        new Color(15, 244, 0).get()
                }
        );

        Mesh ptop = p0.clone();

        float angle = (float) Math.toRadians(30.f);

        p0.rotate(-angle);
        ptop.union(p0);

        p0.rotate(angle);
        ptop.union(p0);

        p0 = ptop.clone();
        p0.mirorX();
        ptop.union(p0);
        ptop.translate(new Vector2(0.f, 15.f));
        palm.union(ptop);
        palm.rotate((float)Math.toRadians(180));
        palm.zoom(2.f);

        palm.setName("palm");
        MeshManager.storeToFile(palm, "palm.mesh");

        while (n-- >= 0) {
            Entity e = em.createEntity("forest");

            e.mesh = palm;
            e.position = new Position(palm, rnd.nextFloat(50, 1920 - 50),
                    rnd.nextFloat(50, 1080-50));
            e.painter = new Painter(e.position, 0);
            e.bBox = new BoundingBox(e.position);

            e.position.enable();
            e.painter.enable();
            e.bBox.enable();

            size += palm.size();
        }

        return size;
    }
    public int createMisc(EntityManager em, MeshManager mm,  int n) {
        Random rnd = new Random();
        Map<Long, Mesh> mset = mm.getMeshSet();
        int size = 0;

        Mesh misc = new Mesh("misc", new float[] {0, 10, 10},
                new float[] {0, 0, 10},
                new short[] {0, 1, 2},
                new int[]{new Color(4, 50, 50, 100).get()});

        MeshManager.storeToFile(misc, "misc.mesh");

        while (n-- >= 0) {
            Entity e = em.createEntity("misc");

            e.mesh = misc.clone();
            e.position = new Position(misc, rnd.nextFloat(50, 1920 - 50),
                    rnd.nextFloat(50, 1080-50));
            e.rotation = new Rotation(misc, rnd.nextFloat(5.f, 10.f));
            e.painter = new Painter(e.position, 0);
            e.motion = new Motion(e.position);
            e.motion.setVelocity(rnd.nextFloat(-2.f, 2.f),
                    rnd.nextFloat(-2.f, 2.f));
            e.bBox = new BoundingBox(e.position);


            e.position.enable();
            e.rotation.enable();
            e.motion.enable();
            e.painter.enable();
            e.bBox.enable();

            size += misc.size();
        }

        return size;

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
        float angle = (float) Math.toRadians(-30.f);

        m0.rotate(angle);
        grass.union(m0);

        m0.rotate(angle);
        grass.union(m0);

        m0 = grass.clone();
        m0.mirorX();
        grass.union(m0);

        grass.setName("grass");
        MeshManager.storeToFile(grass, "grass.mesh");

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

    public int createMonsters(EntityManager em, MeshManager mm, int n) {
        Random rnd = new Random();
        Map<Long, Mesh> mset = mm.getMeshSet();
        int size = 0;

        Mesh monster = MeshManager.loadFromFile("crab1.mesh");
        monster.zoom(0.8f);
        monster.rotate((float)Math.toRadians(180));

        monster.setName("monster");
        MeshManager.storeToFile(monster, "monster.mesh");

        while (n-- >= 0) {
            Entity e = em.createEntity("monster");

            e.mesh = monster;
            e.position = new Position(monster, rnd.nextFloat(50, 1920 - 50),
                    rnd.nextFloat(50, 1080-50));
            e.motion = new Motion(e.position);
            e.motion.setVelocity(rnd.nextFloat(-3, 3),
                    rnd.nextFloat(-2, 2));
            e.painter = new Painter(e.position, 0);
            e.bBox = new BoundingBox(e.position);

            e.motion.enable();
            e.position.enable();
            e.painter.enable();
            e.bBox.enable();

            size += monster.size();
        }

        return size;
    }

    public Scene createScene(EntityManager entityManager) {
        List<GameSystem> scene = new LinkedList<GameSystem>();

        scene.add(new MovementSystem());
        scene.add(new CollisionSystem());
        scene.add(new TowerSystem(10, 5.f, 10.f));
        scene.add(new PaintingSystem());
        scene.add(new CleanUpSystem(100));

        for(GameSystem g : scene) {
            g.enable();
        }

        return new Scene(scene, entityManager);
    }

}
