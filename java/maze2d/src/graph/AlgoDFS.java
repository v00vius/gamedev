package graph;

import typedefs.Bits;
import typedefs.FastEdge;
import typedefs.PackedShort4;

import java.util.ArrayList;
import java.util.List;

public class AlgoDFS {
private long[] graph;
private short source;
private short destination;
private List<Long> path;
private Bits visited;
private short node1;
private short node2idx;
public AlgoDFS()
{
        path = new ArrayList<>();
}

public void init(long[] graph, short src, short dst)
{
        this.graph = graph;
        this.source = src;
        this.destination = dst;
        path.clear();

        visited = new Bits(graph.length);
        visited.clear();

        node1 = source;
        node2idx = 0;

        push();
}
private void push()
{
        long edge = PackedShort4.set(0, 0, node1);
        edge = PackedShort4.set(edge, 1, node2idx);

        path.add(edge);
        visited.set(node1);
}
public boolean step()
{
        if(next())
                return true;

        return path.isEmpty();
}
private void pop()
{
        if(path.isEmpty())
                return;

        path.removeLast();
        visited.clr(node1);

        long edge = path.getLast();

        node1 = PackedShort4.get(edge, 0);
        node2idx = PackedShort4.get(edge, 1);

        updateEdge(edge);
}

public boolean next()
{
        if(node2idx > 3) {
                pop();

                return false;
        }

        short node2 = PackedShort4.get(graph[node1], node2idx);

        if(node2 == destination) {
                return true;
        }

        if(node2 == GraphBuilder.EMPTY_NODE) {
                pop();

                return false;
        }

        if(visited.get(node2)) {

                long edge = path.getLast();

                updateEdge(edge);

                return false;
        }

        node1 = node2;
        node2idx = 0;
        push();

        return false;
}

private void updateEdge(long edge)
{
        ++node2idx;
        edge = PackedShort4.set(edge, 1, node2idx);
        path.set(path.size() - 1, edge);
}

public List<Long> getPath()
{
        return path;
}

public long[] getGraph()
{
        return graph;
}

@Override
public String toString()
{
        StringBuilder sb = new StringBuilder("Path\n");
        int count = 0;

        for (long link : path) {
                int n1 = PackedShort4.get(link, 0);
                int i = PackedShort4.get(link, 1);
                int n2 = PackedShort4.get(graph[n1], i);

                sb.append("  ")
                        .append(count)
                        .append(": ")
                        .append(n1)
                        .append('-')
                        .append(n2)
                        .append('\n');

                ++count;
        }

        return sb.toString();
}
}
