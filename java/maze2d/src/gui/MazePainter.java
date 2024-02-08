package gui;

import graph.AlgoDFS;
import graph.Maze2D;
import imgui.ImColor;
import typedefs.PackedEdge;
import typedefs.PackedShort4;

public class MazePainter {
private float cellSize;
private int cols;
private int rows;

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
public void paint(AlgoDFS algo, Maze2D maze, Painter painter)
{
        cols = maze.getCols();
        rows = maze.getRows();

        long[] graph = algo.getGraph();
        int count = 0;

        for(long edge : algo.getPath()) {
                short n1 = PackedShort4.get(edge, 0);
                short n2idx = PackedShort4.get(edge, 1);
                short n2 = PackedShort4.get(graph[n1], n2idx);

                ++count;

                if(n2 < 0)
                        continue;

                int src_x = n1 % cols;
                int src_y = n1 / cols;
                int dst_x = n2 % cols;
                int dst_y = n2 / cols;

                int x = Math.min(src_x, dst_x);
                int y = Math.min(src_y, dst_y);

                float width = cellSize * (float) (Math.abs(dst_x - src_x) + 1);
                float height = cellSize * (float) (Math.abs(dst_y - src_y) + 1);

                painter.rectangle(1.f + (float) x * cellSize,
                        1.f + (float) y * cellSize,
                        width - 2.f, height - 2.f, ImColor.rgb(255,255,0)
                );

                if(count == 1) {
                        painter.rectangle(1.f + (float) x * cellSize,
                                1.f + (float) y * cellSize,
                                cellSize - 2.f, cellSize - 2.f, ImColor.rgb(0, 0, 255)
                        );

                } else if (count == algo.getPath().size() -  1) {
                        x = Math.max(src_x, dst_x);
                        y = Math.max(src_y, dst_y);

                        painter.rectangle(1.f + (float) x * cellSize,
                                1.f + (float) y * cellSize,
                                cellSize - 2.f, cellSize - 2.f, ImColor.rgb(0,0,255)
                        );
                }

/*
                painter.text(1.f + src_x * cellSize, 1.f + src_y * cellSize,
                        Integer.toString(n1), ImColor.rgb(0, 0, 0)
                );

                painter.text(1.f + src_x * cellSize, 20.f + src_y * cellSize,
                        Integer.toString(n2), ImColor.rgb(0, 0, 0)
                );
*/
        }
}
private void paintGraph(Maze2D maze, Painter painter)
{
        for(long edge : maze.getGraph()) {
                int src = PackedEdge.getSrc(edge);
                int dst = PackedEdge.getDst(edge);

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
