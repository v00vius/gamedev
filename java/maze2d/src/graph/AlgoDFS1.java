package graph;

import typedefs.Bits;
import typedefs.FastEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AlgoDFS1 {
private int cols;
private int rows;
private Set<Long> graph;
private Bits visited;
private List<Long> path;
private int x;
private int y;
private int direction;
static private int[] dx = { 1, 0, -1, 0};
static private int[] dy = { 0, 1, 0, -1};


public AlgoDFS1(Maze2D1 maze)
{
        rows = maze.getRows();
        cols = maze.getCols();
        graph = maze.getGraph();
        visited = new Bits(cols, rows);
        path = new ArrayList<>();
}

public void init()
{
        x = y = 0;

        int src = index(x, y);

        visited.set(src);
        direction = 0;
}
private int index(int pos_x, int pos_y)
{
        return pos_y * cols + pos_x;
}

private void push()
{
        int src = index(x, y);
        long e = FastEdge.create(src, direction);

        path.add(e);
}

private void pop()
{
        if(path.isEmpty())
                return;

        path.removeLast();

        if(path.isEmpty())
                return;

        long e = path.getLast();

        int src = FastEdge.getSrc(e);

        direction = FastEdge.getDst(e);

}
public boolean dfs()
{
        if(direction > 3) {
                if(path.isEmpty())
                        return false;

                path.removeLast();

                long node = path.getLast();
                int src = FastEdge.getSrc(node);

                direction = FastEdge.getDst(node);
                ++direction;
                node = FastEdge.create(src, direction);

        }

        int px = x + dx[direction];
        int py = y + dy[direction];

        if(px < 0 || px >= cols ||
           py < 0 || py >= rows) {
                return true;
        }

        if(visited.get(px, py)) {
                return true;
        }

        int src = index(x, y);
        int dst = index(px, py);
        long edge = FastEdge.create(src, dst);

        if(graph.contains(edge)) {
                long node = FastEdge.create(dst, 0);

                path.add(node);
                direction = 0;

                return true;
        }

        return true;
}

}
