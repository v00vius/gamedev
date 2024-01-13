package system;

import component.*;
import entity.Entity;
import imgui.ImGui;
import repo.EntityManager;
import service.Utils;
import types.Color;
import types.Vector2;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class TowerSystem extends GameSystem {
    private int particles;
    private double period;
    private float lifetime;
    private double lastStrike;
    private EntityManager em;

    public TowerSystem(int particles, float period, float lifetime) {
        super();

        this.particles = particles;
        this.period = period;
        this.lifetime = lifetime;
        this.lastStrike = 0.;
    }

    @Override
    protected void task(EntityManager entityManager) {
        em = entityManager;
        double now = Utils.getTime();

        int bulletsActive = em.getTaggedAs("bullet").size();

        ImGui.text(String.format("Tower, waiting: %f secs", now - lastStrike));
        ImGui.text(String.format("Tower, bullets: %d", bulletsActive));

        List<Entity> towers = em.getTaggedAs("tower");

        for(Entity e : towers) {
            if(!e.isAlive())
                continue;

            if(e.controller.isEnabled()) {
                Controller c = e.controller;

                if(c.getMouseDown(0) == 0.f)
                    fire(towers);
            }
        }

        if((now - lastStrike) > period) {
            fire(towers);
            lastStrike = now;
        }
    }

    private void fire(List<Entity> towers) {
        Mesh b0 = new Mesh("b0", new float[] {0, 10, 10},
                new float[] {0, 0, 10},
                new short[] {0, 1, 2},
                new int[]{new Color(255, 50, 50).get()});

        Mesh bullet = b0.clone();
        bullet.setName("bullet");
        b0.rotate((float)Math.toRadians(180));
        bullet.union(b0);

        for(Entity tower : towers) {
            if(!tower.isAlive())
                continue;

            for (int i = 0; i < particles; ++i) {
                createBullet(tower.position.getCoordinate(), bullet);
            }
        }

    }

    private void createBullet(Vector2 start, Mesh bullet) {
        Random rnd = new Random();
        Entity e = em.createEntity("bullet");

        e.mesh = bullet;
        e.position = new Position(bullet, start.x, start.y);
        e.motion = new Motion(e.position);
        e.motion.setVelocity(rnd.nextFloat(-10, 10),
                rnd.nextFloat(-10, 10));
        e.painter = new Painter(e.position, 0);
        e.bBox = new BoundingBox(e.position);
        e.opacity = new Decay(e.painter, lifetime, 0.1f * lifetime);
        e.rotation = new Rotation(bullet, 0.5f);

        e.motion.enable();
        e.position.enable();
        e.painter.enable();
        e.bBox.enable();
        e.rotation.enable();
        e.opacity.enable();
    }
}
