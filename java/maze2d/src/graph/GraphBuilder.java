package graph;

import typedefs.PackedEdge;
import typedefs.PackedShort4;

public class GraphBuilder {
static public final short EMPTY_NODE = -1;
static public final long EMPTY_LIST = PackedShort4.fill(0L, EMPTY_NODE);
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
public long[] build()
{
        short bias = (short) (maze.getCols() - 3);

        for (long edge : maze.getGraph()) {
                int src = PackedEdge.getSrc(edge);
                int dst = PackedEdge.getDst(edge);

                int src_x = maze.getX(src);
                int src_y = maze.getY(src);
                int dst_x = maze.getX(dst);
                int dst_y = maze.getY(dst);

                short n1 = makeIndex(src_x, src_y);
                short n2 = makeIndex(dst_x, dst_y);

                graph[n1] = addNode(graph[n1], n2);
                graph[n2] = addNode(graph[n2], n1);
        }

        return graph;
}

public long[] getGraph()
{
        return graph;
}

private short makeIndex(int x, int y)
{
        return (short)(maze.getCols() * y + x);
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

@Override
public String toString()
{
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (long nodes : graph) {
                sb.append(count)
                        .append(":");

                for (int i = 0; i < 4; ++i) {
                        short node = PackedShort4.get(nodes, i);

                        if(node == EMPTY_NODE)
                                sb.append(" -");
                        else
                                sb.append(" ").append(node);
                }

                sb.append("\n");
                ++count;
        }

        return sb.toString();
}
}
