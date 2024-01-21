import gui.Painter;
import imgui.ImColor;
import typedefs.Bits;
import typedefs.FastEdge;

import java.util.List;

public class MazePainter {
private float cellSize;
private Painter painter;
private int color;
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
        this.painter = painter;
        this.color = color;

        this.cols = maze.getCols();
        this.rows = maze.getRows();

        painter.grid(0.f, 0.f, rows, cols, cellSize, color);

        List<Long> graph = maze.getGraph();
        List<Long> wave = maze.getWave();

        for(long edge : graph) {
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

        for(long edge : wave) {
                int src_x = maze.getSrcX(edge);
                int src_y = maze.getSrcY(edge);

                painter.rectangle(1.f + (float) src_x * cellSize,
                        1.f + (float) src_y * cellSize,
                        cellSize - 2.f, cellSize - 2.f, ImColor.rgb(245, 169, 30)
                );
        }

}
}
