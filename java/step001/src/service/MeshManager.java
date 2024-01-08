package service;

import component.Mesh;

import java.util.HashMap;

public class MeshManager {
    HashMap<Long, Mesh> meshSet;

    public MeshManager() {
        this.meshSet = new HashMap<>();
    }

    public Mesh add(String name, float[] vx, float[] vy, short[] vertices, int[] colors) {
        Mesh mesh = new Mesh(name, vx, vy, vertices, colors);

        return meshSet.put(mesh.getId(), mesh);
    }

    public Mesh addFromFile(String fileName) {
        Mesh mesh = Mesh.load(fileName);

        if(mesh == null)
            return null;

        return meshSet.put(mesh.getId(), mesh);
    }
}
