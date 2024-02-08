package graph;

import typedefs.Bits;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AlgoDFS1 {
private int cols;
private int rows;
private int source;
private int destination;
private Set<Maze2D1.Edge> graph;
private Bits visited;
private List<Maze2D1.Edge> path;
private Maze2D1.Edge position;
static private int[] dx = { 1, 0, -1, 0};
static private int[] dy = { 0, 1, 0, -1};
private int steps;


public AlgoDFS1(Maze2D1 maze)
{
        rows = maze.getRows();
        cols = maze.getCols();
        graph = maze.getGraph();

        visited = new Bits(cols, rows);
        path = new ArrayList<>();
        position = null;
}

public void init()
{
        Random random = new Random(System.currentTimeMillis());

        int src = index(0, random.nextInt(rows));
        int dst = index(cols - 1, random.nextInt(rows));

        init(src, dst);
}
public void init(int source, int destination)
{
        steps = 0;
        this.source = source;
        this.destination = destination;

        path.clear();
        visited.clear();
        push(this.source);
}

public List<Maze2D1.Edge> getPath()
{
        return path;
}

private int index(int pos_x, int pos_y)
{
        return pos_y * cols + pos_x;
}

private void push(int node)
{
        position = new Maze2D1.Edge(node, 0);
        visited.set(node);
        path.add(position);
}

private void pop()
{
        if(path.size() < 2) {
                path.clear();

                return;
        }

        Maze2D1.Edge e = path.removeLast();

        visited.clr(e.getSrc());
        position = path.getLast();
}

public int getSteps()
{
        return steps;
}

public boolean dfs()
{
        if(position.getSrc() == destination)
                return true;

        ++steps;

        int direction = position.getDst();

        if(direction > 3) {
                pop();
                position.incDst();

                return false;
        }

        int x = position.getSrc() % cols;
        int y = position.getSrc() / cols;
        int px = x + dx[direction];
        int py = y + dy[direction];

        if(px < 0 || px >= cols ||
           py < 0 || py >= rows) {
                position.incDst();

                return false;
        }

        if(visited.get(px, py)) {
                position.incDst();

                return false;
        }

        int dst = index(px, py);
        Maze2D1.Edge edge = new Maze2D1.Edge(position.getSrc(), dst);

        if(graph.contains(edge)) {
                push(dst);

                return false;
        }

        position.incDst();

        return false;
}

@Override
public String toString()
{
        StringBuilder sb = new StringBuilder("Path\n");
        int count = 0;

        sb.append("  current position=")
                .append(position.getSrc())
                .append(" direction=")
                .append(position.getDst())
                .append('\n');

        sb.append(visited);

        for(Maze2D1.Edge e : path) {
                int src = e.getSrc();

                sb.append(count++)
                        .append(": ")
                        .append(src)
                        .append('\n');
        }

        return sb.toString();
}
}
