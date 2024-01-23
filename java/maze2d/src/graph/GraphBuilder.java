package graph;

import typedefs.FastEdge;
import typedefs.PackedShort4;

public class GraphBuilder {
static private final short EMPTY_NODE = -1;
static private final long EMPTY_LIST = PackedShort4.fill(0L, EMPTY_NODE);
private Maze2D maze;
private long[] graph;

public GraphBuilder()
{
}
public void init(Maze2D maze)
{
        this.maze = maze;
        graph = new long[maze.getCols() * maze.getRows()];

        for (int i = 0, len = graph.length; i < len; ++i)
                graph[i] = EMPTY_LIST;
}
public long[] getGraph()
{
        short bias = (short) (maze.getCols() - 3);

        for (long edge : maze.getGraph()) {
                short src = (short) FastEdge.getSrc(edge);
                short dst = (short) FastEdge.getDst(edge);

                src -= bias;
                dst -= bias;

                graph[src] = addNode(graph[src], dst);
                graph[dst] = addNode(graph[dst], src);
        }

        return graph;
}
private long addNode(long edge, short node)
{
        for (int i = 0; i < 4; ++i) {
                short existingNode = PackedShort4.get(edge, i);

                if(existingNode == EMPTY_NODE)
                        return PackedShort4.set(edge, i, node);
        }

        return EMPTY_LIST;
}
}
