import java.util.List;

public class MazePainter {
private float cellSize;

public MazePainter(float cellSize)
{
        this.cellSize = cellSize;
}
public void paint(Maze2D maze)
{
        int width = maze.getCols();
        int height = maze.getRows();
        List<Long> graph = maze.getGraph();
        List<Long> wave = maze.getWave();

        Bits openRight = new Bits(width, height);
        Bits openBottom = new Bits(width, height);

        for(long edge : graph) {
                int src = FastEdge.getSrc(edge);
                int dst = FastEdge.getDst(edge);
                int distance = dst - src;
                int src_x = maze.getX(src);
                int src_y = maze.getY(src);
                int dst_x = maze.getX(dst);
                int dst_y = maze.getY(dst);

                if(1 == distance)
                        openRight.set(src);
                else if (-1 == distance) {
                        openRight.set(src);
                } else if (-width == distance) {
                        openBottom.set(dst);
                } else if (width == distance) {
                        openBottom.set(src);
                }
        }
}
}
