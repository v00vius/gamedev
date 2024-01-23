package gui;

import graph.Maze2D;
import gui.Painter;
import imgui.ImColor;
import typedefs.Bits;
import typedefs.FastEdge;

import java.util.List;

public class MazePainter {
private float cellSize;

public MazePainter(float cellSize)
{
        this.cellSize = cellSize;
}
public float getCellSize()
{
        return cellSize;
}

public void paint(Maze2D maze, Painter painter, int color)
{
        painter.grid(0.f, 0.f, maze.getRows(), maze.getCols(), cellSize, color);
        paintGraph(maze, painter);
        paintWave(maze, painter);
}

private void paintGraph(Maze2D maze, Painter painter)
{
        for(long edge : maze.getGraph()) {
                int src = FastEdge.getSrc(edge);
                int dst = FastEdge.getDst(edge);

                if(dst < src) {
                        int tmp = src;

                        src = dst;
                        dst = tmp;
                }

                int src_x = maze.getX(src);
                int src_y = maze.getY(src);
                int dst_x = maze.getX(dst);
                int dst_y = maze.getY(dst);

                float width = cellSize * (float) (dst_x - src_x + 1);
                float height = cellSize * (float) (dst_y - src_y + 1);

                painter.rectangle(1.f + (float) src_x * cellSize,
                        1.f + (float) src_y * cellSize,
                        width - 2.f, height - 2.f, ImColor.rgb(0, 255, 0)
                );
        }
}

private void paintWave(Maze2D maze, Painter painter)
{
        for(long edge : maze.getWave()) {
                int src_x = maze.getSrcX(edge);
                int src_y = maze.getSrcY(edge);

                painter.rectangle(1.f + (float) src_x * cellSize,
                        1.f + (float) src_y * cellSize,
                        cellSize - 2.f, cellSize - 2.f, ImColor.rgb(245, 169, 30)
                );
        }
}
}
