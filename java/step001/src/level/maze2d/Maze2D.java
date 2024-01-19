package level.maze2d;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Maze2D {
private final int rows;
private final int cols;
private Bits area;
static private Random rnd = new Random(31);
private List<Edge> wave;
private List<Edge> graph;

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

public List<Edge> getGraph()
{
        return graph;
}

public List<Edge> getWave()
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
                  index(rnd.nextInt(cols), rnd.nextInt(rows)) :
                  index(x0, y0);

        Edge e = new Edge(idx, idx);

        graph.add(e);
        area.set(idx);

        return e.getFrom();
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

        wave.add(new Edge(p, p1));
}
public int step(int p1)
{
        addWave(p1 - 1, p1);
        addWave(p1 + 1, p1);
        addWave(p1 - cols - 2, p1);
        addWave(p1 + cols + 2, p1);

        Edge edge;

        do {
                if(wave.isEmpty())
                        return -1;

                int i = rnd.nextInt(wave.size());

                edge = wave.remove(i);
        }
        while (area.get(edge.getFrom()));

        graph.add(edge);
        area.set(edge.getFrom());

        return edge.getFrom();
}

@Override
public String toString()
{
        StringBuilder s = new StringBuilder(String.format("Graph: %d edges\n", graph.size()));
        int i = 0;

        for (Edge e : graph) {
                s.append(i++).append(": ").append(e.getFrom()).append(" -> ").append(e.getTo()).append("\n");
        }

        return s.toString();
}

public class Edge {
        private final int from;
        private final int to;

        public Edge(int from, int to)
        {
                this.from = from;
                this.to = to;
        }

        public int getFrom()
        {
                return from;
        }

        public int getTo()
        {
                return to;
        }

        @Override
        public int hashCode()
        {
                return from;
        }

        @Override
        public boolean equals(Object o)
        {
                return from == ((Edge)o).getFrom();
        }
}
}
