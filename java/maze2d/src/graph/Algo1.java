package graph;

import typedefs.Bits;
import typedefs.PackedShort4;

import java.util.ArrayList;
import java.util.List;

public class Algo1 {
private long[] graph;
private short source;
private short destination;
private List<Long> path;
private Bits passed;
private short node1;
private short node2idx;
public Algo1()
{
        path = new ArrayList<>();
}

public void init(long[] graph, short src, short dst)
{
        this.graph = graph;
        this.source = src;
        this.destination = dst;
        path.clear();

        passed = new Bits(graph.length);
        passed.clear();

        node1 = source;
        node2idx = 0;

        push();
}
private void push()
{
        long edge = PackedShort4.set(0, 0, node1);
        edge = PackedShort4.set(edge, 1, node2idx);

        path.add(edge);
        passed.set(node1);
}
private void pop()
{
        if(path.isEmpty())
                return;

        long edge = path.getLast();
        path.removeLast();
        passed.clr(node1);

        node1 = PackedShort4.get(edge, 0);
        node2idx = PackedShort4.get(edge, 1);
        ++node2idx;
}

private boolean next()
{
        if(node2idx > 3) {
                pop();

                return false;
        }

        short node2 = PackedShort4.get(graph[node1], node2idx);

        if(node2 == destination)
                return true;

        if(node2 == GraphBuilder.EMPTY_NODE) {
                pop();

                return false;
        }

        if(passed.get(node2)) {
                ++node2idx;

                return false;
        }

        node1 = node2;
        node2idx = 0;
        push();

        return false;
}

public List<Long> getPath()
{
        return path;
}
}
