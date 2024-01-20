

import java.util.*;

public class Maze2D {
private final int rows;
private final int cols;
private Bits area;
static private FastRandom random = new FastRandom(1024);
private List<Long> wave;
private List<Long> graph;

public Maze2D(int rows, int cols)
{
        this.rows = rows;
        this.cols = cols;
        area = new Bits(2 + cols, 2 + rows);
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
        area.clear();

        for (int x = 0; x < area.getSize_x(); ++x) {
                area.set(x, 0);
                area.set(x, area.getSize_y() - 1);
        }

        for (int y = 1; y < area.getSize_y() - 1; ++y) {
                area.set(0, y);
                area.set(area.getSize_x() - 1, y);
        }


        wave.clear();
        graph.clear();

        int idx = (x0 == -1 && y0 == -1) ?
                  index(random.nextInt(cols), random.nextInt(rows)) :
                  index(x0, y0);

        long e = FastEdge.create(idx, idx);

        graph.add(e);
        area.set(idx);

        return FastEdge.getSrc(e);
}
private int index(int x, int y)
{
        return (2 + cols) * (1 + y) + x + 1;
}
private int getX(int idx)
{
        return (idx - 1) % (cols + 2);
}
private int getY(int idx)
{
        return (idx - 1) / (cols + 2) - 1;
}

private void addWave(int p, int p1)
{
        if(area.get(p))
                return;

        wave.add(FastEdge.create(p, p1));
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
                        return -1;

                int i = random.nextInt(wave.size());

                edge = wave.remove(i);
//                edge = wave.removeLast();

                src = FastEdge.getSrc(edge);
        }
        while (area.get(src));

        graph.add(edge);
        area.set(src);

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
                        .append(FastEdge.toString(e))
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
