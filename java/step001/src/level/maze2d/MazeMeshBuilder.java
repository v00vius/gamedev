package level.maze2d;

import component.mesh.Mesh;
import types.Vector2;

import java.util.List;
import java.util.Vector;

public class MazeMeshBuilder {
private final float cellSize;
private final int gridColor;
private final int waveColor;
private final int graphColor;

public MazeMeshBuilder(float cellSize, int gridColor, int waveColor, int graphColor)
{
        this.cellSize = cellSize;
        this.gridColor = gridColor;
        this.waveColor = waveColor;
        this.graphColor = graphColor;
}
private Mesh createBaseTriangle()
{
        Mesh base = new Mesh("base0",
                                new float[] {0f, cellSize * 0.5f, cellSize * 0.5f},
                                new float[] {0f, -cellSize * 0.5f, cellSize * 0.5f},
                                new short[] {0, 1, 2},
                                new int[] {gridColor}
        );

       return base;
}

public Mesh createMesh(Maze2D maze)
{
        List<Maze2D.Edge> wave = maze.getWave();
        List<Maze2D.Edge> graph = maze.getGraph();
        int rows = maze.getRows();
        int cols = maze.getCols();

        Mesh meshMaze = createGrid(cols, rows);
        meshMaze.union(createWave());
        meshMaze.union(createGraph());

        return meshMaze.pack();
}
private Mesh createGrid(int cols, int rows)
{
        Mesh m0 = createBaseTriangle();
        Mesh cell = m0.clone();

        m0.rotate((float) Math.toRadians(180.));
        cell.union(m0);

        m0 = cell.clone();
        m0.rotate((float) Math.toRadians(90.));
        cell.union(m0).pack();
        m0 = cell.clone();

        Vector2 pos = new Vector2(0.5f * cellSize, -0.5f * cellSize);
        m0.translate(pos);

        Mesh grid = m0.clone();

        for (int i = 0; i < rows; ++i) {
                float y = i * cellSize;

                pos.set(0.f, y);
                cell = m0.clone();
                cell.translate(pos);
                pos.set((float)cellSize, 0.f);

                for (int j = 0; j < cols; ++j) {
                        grid.union(cell);
                        cell.translate(pos);
                }
        }

        return grid.pack();
}
private Mesh createWave()
{
        return null;
}
private Mesh createGraph()
{
        return null;
}
}
