package graph;

import typedefs.Bits;
import typedefs.PackedEdge;
import typedefs.FastRandom;

import java.util.*;

public class Maze2D {
private final int rows;
private final int cols;
private Bits visited;
static private FastRandom random = new FastRandom(1024);
private List<Long> wave;
private List<Long> graph;

public Maze2D(int rows, int cols)
{
        this.rows = rows;
        this.cols = cols;
        visited = new Bits(2 + cols, 2 + rows);
        wave = new ArrayList<>();
        graph = new LinkedList<>();
}
public int getRows()
{
        return rows;
}
public int getCols()
{
        return cols;
}
public List<Long> getGraph()
{
        return graph;
}
public List<Long> getWave()
{
        return wave;
}
public int init()
{
        return init(-1, -1);
}
public int init(int x0, int y0)
{
        visited.clear();

        for (int x = 0; x < visited.getSize_x(); ++x) {
                visited.set(x, 0);
                visited.set(x, visited.getSize_y() - 1);
        }

        for (int y = 1; y < visited.getSize_y() - 1; ++y) {
                visited.set(0, y);
                visited.set(visited.getSize_x() - 1, y);
        }

        wave.clear();
        graph.clear();

        int idx = (x0 == -1 && y0 == -1) ?
                  index(random.nextInt(cols), random.nextInt(rows)) :
                  index(x0, y0);

//        long e = FastEdge.create(idx, idx);

//        graph.add(e);
        visited.set(idx);

//        return FastEdge.getSrc(e);
        return idx;
}
private int index(int x, int y)
{
        return (2 + cols) * (1 + y) + x + 1;
}
// x = 0, y = 0, cols = 10, rows = 10
// idx = (2 + 10) * (1 + 0) + 0 + 1 = 12 + 1 = 13
// getX = (13 - 1) % (10 + 2) = 12 % 12 = 0
// getY = (13 - 1 ) / (10 + 2) - 1 = 12 / 12 - 1 = 1 - 1 = 0
//
// x = 2, y = 3
// idx = (2 + 10) * (1 + 3) + 2 + 1 = 12 * 4 + 3 = 48 + 3 = 51
// getX = (51 - 1) % (10 + 2) = 50 % 12 = 2
// getY = (51 - 1) / (10 + 2) - 1 = 50 / 12 - 1 = 4 - 1 = 2
public int getSrcX(long edge)
{
        return getX(PackedEdge.getSrc(edge));
}
public int getSrcY(long edge)
{
        return getY(PackedEdge.getSrc(edge));
}
public int getDstX(long edge)
{
        return getX(PackedEdge.getDst(edge));
}
public int getDstY(long edge)
{
        return getY(PackedEdge.getDst(edge));
}

public int getX(int idx)
{
        return (idx - 1) % (cols + 2);
}
public int getY(int idx)
{
        return (idx - 1) / (cols + 2) - 1;
}
private void addWave(int p, int p1)
{
        if(visited.get(p))
                return;

        wave.add(PackedEdge.create(p, p1));
}
public int step(int p1)
{
        addWave(p1 - 1, p1);
        addWave(p1 + 1, p1);
        addWave(p1 - cols - 2, p1);
        addWave(p1 + cols + 2, p1);

        long edge;
        int src;

        do {
                if(wave.isEmpty())
                        return 0;

                int i = random.nextInt(wave.size());

                edge = wave.remove(i);
//                edge = wave.removeLast();

                src = PackedEdge.getSrc(edge);
        }
        while (visited.get(src));

        graph.add(edge);
        visited.set(src);

        return src;
}

@Override
public String toString()
{
        StringBuilder s = new StringBuilder(String.format("Graph: %d edges\n", graph.size()));
        int i = 0;

        for (long e : graph) {
                s.append(i++)
                        .append(": ")
                        .append(PackedEdge.toString(e))
                        .append("\n");
        }

        return s.toString();
}

public class Edge {
        private final int src;
        private final int dst;

        public Edge(int src, int dst)
        {
                this.src = src;
                this.dst = dst;
        }

        public int getSrc()
        {
                return src;
        }

        public int getDst()
        {
                return dst;
        }

        @Override
        public int hashCode()
        {
                return src;
        }

        @Override
        public boolean equals(Object o)
        {
                return src == ((Edge)o).getSrc();
        }
}
}
