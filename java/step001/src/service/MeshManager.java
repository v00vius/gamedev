package service;

import component.Mesh;

import java.util.HashMap;

public class MeshManager {
    HashMap<Long, Mesh> meshSet;

    public MeshManager() {
        this.meshSet = new HashMap<>();
    }
}
