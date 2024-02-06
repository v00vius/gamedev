package graph;

import typedefs.Bits;
import typedefs.FastEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AlgoDFS1 {
private int cols;
private int rows;
private int destination;
private Set<Long> graph;
private Bits visited;
private List<Long> path;
private int x;
private int y;
private int direction;
static private int[] dx = { 1, 0, -1, 0};
static private int[] dy = { 0, 1, 0, -1};


public AlgoDFS1(Maze2D1 maze, int destination)
{
        rows = maze.getRows();
        cols = maze.getCols();
        this.destination = destination;
        graph = maze.getGraph();
        visited = new Bits(cols, rows);
        path = new ArrayList<>();
}

public void init()
{
        x = y = 0;
        path.clear();
        visited.clear();
        direction = 0;
        push();
}

public List<Long> getPath()
{
        return path;
}

private int index(int pos_x, int pos_y)
{
        return pos_y * cols + pos_x;
}

private void push()
{
        int src = index(x, y);
        long e = FastEdge.create(src, direction);

        visited.set(src);
        path.add(e);
}

private void pop()
{
        if(path.isEmpty())
                return;

        long e = path.removeLast();

        visited.clr(FastEdge.getSrc(e));

        if(path.isEmpty())
                return;

        int src = nextDirection();

        x = src % cols;
        y = src / cols;
}

private int nextDirection()
{
        long e = path.getLast();

        direction = FastEdge.getDst(e);
        ++direction;
        e = FastEdge.setDst(e, direction);
        path.set(path.size() - 1, e);

        return FastEdge.getSrc(e);
}

public boolean dfs()
{
        if(direction > 3) {
                pop();

                return true;
        }

        int px = x + dx[direction];
        int py = y + dy[direction];

        if(px < 0 || px >= cols ||
           py < 0 || py >= rows) {
                nextDirection();

                return true;
        }

        if(visited.get(px, py)) {
                nextDirection();

                return true;
        }

        int src = index(x, y);
        int dst = index(px, py);
        long edge = FastEdge.create(src, dst);

        if(graph.contains(edge)) {
                src = dst;
                direction = 0;
                x = src % cols;
                y = src / cols;

                push();

                return dst != destination;
        }

        nextDirection();

        return true;
}

@Override
public String toString()
{
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for(long e : path) {
                int src = FastEdge.getSrc(e);

                sb.append(count++)
                        .append(": ")
                        .append(src)
                        .append('\n');
        }

        return sb.toString();
}
}
