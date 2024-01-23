package graph;

import typedefs.Bits;
import typedefs.PackedShort4;

import java.util.ArrayList;
import java.util.List;

public class Algo1 {
private long[] graph;
private short source;
private short destination;
private List<Short> path;
private Bits passed;
private long head;

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

        passed.set(source);
        head = PackedShort4.set(0L, 0, source);
        head = PackedShort4.set(head, 1, (short) 0);
        path.add(source);
}

public List<Short> algo1()
{
        short src = PackedShort4.get(head, 0);
        short i = PackedShort4.get(head, 1);
        short dst = PackedShort4.get(graph[src], i);

        path.add(dst);

        if(dst == destination)
                return path;

        
}
}
