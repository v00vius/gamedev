package service;

import component.Mesh;
import types.Context;

import java.util.LinkedList;

public class MeshMgr {
    LinkedList<Mesh> polygon;

    public MeshMgr() {
        this.polygon = new LinkedList<>();
    }

    void addMesh(float[] vx, float[] vy, int[] tr) {
        Mesh mesh = new Mesh(vx, vy, tr, null);

        polygon.add(mesh);
    }

    void action(Context ctx) {
        for (Mesh m : polygon) {
            ctx.action(m);
        }
    }

    public LinkedList<Mesh> getPolygon() {
        return polygon;
    }
}
