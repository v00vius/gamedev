package graph;

import typedefs.Bits;
import typedefs.FastRandom;

import java.util.*;

public class Maze2D1 {
private int cols;
private int rows;
private Bits visited;
private List<Edge> wave;
private Set<Edge> graph;
private int x;
private int y;
static private int[] dx = { 1, 0, -1, 0};
static private int[] dy = { 0, 1, 0, -1};
static private final FastRandom random = new FastRandom(2048);

public Maze2D1(int cols, int rows)
{
        this.cols = cols;
        this.rows = rows;

        visited = new Bits(this.cols, this.rows);
        wave = new ArrayList<>();
        graph = new HashSet<>();

        init(0, 0);
}

public void init()
{
        init(-1, 0);
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
        Edge edge;
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
                wave.add(new Edge(src, dst));
        }

        do {
                if(wave.isEmpty())
                        return false;

                int i = random.nextInt(wave.size());

                edge = wave.remove(i);
                dst = edge.getDst();
        } while (visited.get(dst));

        visited.set(dst);

        x = dst % cols;
        y = dst / cols;

        // edge: (src, dst)
        graph.add(edge);
        // edge: (dst, src)
        graph.add(new Edge(edge.getDst(), edge.getSrc()));

        return true;
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

public Set<Edge> getGraph()
{
        return graph;
}

public List<Edge> getWave()
{
        return wave;
}

@Override
public String toString()
{
        StringBuilder sb = new StringBuilder("graph: size " + graph.size() + '\n');
        int count = 0;

        if(!wave.isEmpty())
                sb.append(visited);

        for (Edge e : graph) {
                int src = e.getSrc();
                int dst = e.getDst();

                sb.append(count++)
                        .append(": (")
                        .append(src)
                        .append(',')
                        .append(dst)
                        .append(")\n");
        }

        return sb.toString();
}
static public class Edge implements Comparable<Edge> {
private int src;
private int dst;

        public Edge(int src, int dst)
        {
                this.src = src;
                this.dst = dst;
        }
        public Edge newReverse()
        {
                return new Edge(dst, src);
        }

        public int getSrc()
        {
                return src;
        }

        public void setSrc(int src)
        {
                this.src = src;
        }

        public int getDst()
        {
                return dst;
        }

        public void setDst(int dst)
        {
                this.dst = dst;
        }

        @Override
        public int compareTo(Edge e)
        {
                int cmp = src - e.src;

                if(0 == cmp)
                        cmp = dst - e.dst;

                return cmp;
        }

        @Override
        public boolean equals(Object o)
        {
//                if (this == o) return true;
//                if (o == null || getClass() != o.getClass()) return false;
                Edge edge = (Edge) o;

                return src == edge.src && dst == edge.dst;
        }

        @Override
        public int hashCode()
        {
//                return Objects.hash(src, dst);
                return 31 * (31 + src) + dst;
        }

        @Override
        public String toString()
        {
                return "(" + src + ',' + dst + ')';
        }
}

public static void main(String[] args)
{
        Maze2D1 maze = new Maze2D1(1_000, 1_000);

        int loops = 37;
        long avg = 0;

        System.out.println("# Building random maze of " + maze.getRows()
                                + 'x' + maze.getCols() + " elements ("
                                + maze.getRows() * maze.getCols() + ')'
        );

        maze.init();

        while (maze.wave()) {
//                        System.out.println(maze);
        }

//                System.out.println("#---------------------");
//                System.out.println(maze);

        System.out.println("# done.");

        AlgoDFS1 dfs = new AlgoDFS1(maze);

        System.out.println("# Going DFS...");

        for(int i = 0; i < loops; ++i) {
                long delta = System.currentTimeMillis();

                dfs.init(0, maze.getRows() * maze.getRows() - 1);

                while (!dfs.fail()) {
                        dfs.dfs();
//                        System.out.println(dfs);

                        if(dfs.found())
                                break;
                }

                delta = System.currentTimeMillis() - delta;
                avg += delta;
                System.out.println(i + ") delta: " + (float) delta / 1000.f + " secs, path: "
                                   + dfs.getPath().size() + " element(s), steps: " + dfs.getSteps());

//                System.out.println("#---------------------");
//                System.out.println(dfs);
        }

        avg /= loops;
        System.out.println("avg  = " + ((float) avg / 1000.f ) + " secs");

//        System.out.println("#---------------------");
//        System.out.println(dfs);
}
}
