package graph;

import typedefs.Bits;
import typedefs.FastEdge;

import java.util.*;

public class Maze2D1 {
private int cols;
private int rows;
private Bits visited;
private List<Long> wave;
private Set<Long> graph;
private int x;
private int y;
static private int[] dx = { 1, 0, -1, 0};
static private int[] dy = { 0, 1, 0, -1};
static private final Random random = new Random(3);

public Maze2D1(int cols, int rows)
{
        this.cols = cols;
        this.rows = rows;

        visited = new Bits(this.cols, this.rows);
        wave = new ArrayList<>();
        graph = new HashSet<>();

        init();
}

public void init()
{
        init(0, 0);
}
public void init(int start_x, int start_y)
{
        visited.clear();
        wave.clear();
        graph.clear();

        if(start_x < 0 || start_y < 0) {
                x = random.nextInt(cols);
                y = random.nextInt(rows);
        }
        else {
                x = start_x;
                y = start_y;
        }

        visited.set(x, y);

//        int node = index(x, y);

//        wave.add(FastEdge.create(node, node));
}

public boolean wave()
{
        long edge;
        int src;
        int dst;

        src = index(x, y);

        for (int i = 0; i < 4; ++i) {
                int px = x + dx[i];
                int py = y + dy[i];

                if(px < 0 || px >= cols ||
                   py < 0 || py >= rows)
                        continue;

                if(visited.get(px, py))
                        continue;

                dst = index(px, py);
                edge = FastEdge.create(src, dst);

                wave.add(edge);
        }

        do {
                if(wave.isEmpty())
                        return false;

                int i = random.nextInt(wave.size());
                edge = wave.remove(i);
                dst = FastEdge.getDst(edge);
        } while (visited.get(dst));

        visited.set(dst);

        x = dst % cols;
        y = dst / cols;

        add(edge);

        return true;
}
private void add(long edge)
{
        // add (a, b)
        graph.add(edge);

        int src = FastEdge.getSrc(edge);
        int dst = FastEdge.getDst(edge);
        // add (b, a)
        graph.add(FastEdge.create(dst, src));
}

private int index(int pos_x, int pos_y)
{
        return pos_y * cols + pos_x;
}

public int getCols()
{
        return cols;
}

public int getRows()
{
        return rows;
}

public Set<Long> getGraph()
{
        return graph;
}

@Override
public String toString()
{
        StringBuilder sb = new StringBuilder("graph: size " + graph.size() + '\n');
        int count = 0;

        if(!wave.isEmpty())
                sb.append(visited);

        for (long e : graph) {
                int src = FastEdge.getSrc(e);
                int dst = FastEdge.getDst(e);

                sb.append(count++)
                        .append(": (")
                        .append(src)
                        .append(',')
                        .append(dst)
                        .append(")\n");
        }

        return sb.toString();
}

public static void main(String[] args)
{
        Maze2D1 maze = new Maze2D1(2, 2);

        maze.init();

        while (maze.wave()) {
//                System.out.println(maze);
        }

        System.out.println("#---------------------");
        System.out.println(maze);
}
}
