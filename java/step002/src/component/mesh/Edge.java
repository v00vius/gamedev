package component.mesh;

import types.Pair;

import java.util.Objects;

public class Edge extends Pair<Short, Short> implements Comparable<Edge> {
        public Edge(Short e1, Short e2)
        {
                super(e1, e2);

                if(first.compareTo(second) > 0) {
                        Short tmp = first;

                        first = second;
                        second = tmp;
                }
        }

        @Override
        public int hashCode()
        {
                return 31 * first.hashCode() + second.hashCode();
        }

        @Override
        public boolean equals(Object o)
        {
                if (this == o)
                        return true;

                if (o == null || getClass() != o.getClass())
                        return false;

                Edge e = (Edge) o;

                return first.equals(e.first) && second.equals(e.second);
        }

        @Override
        public int compareTo(Edge o)
        {
                int eq = first.compareTo(o.first);

                return eq == 0 ? second.compareTo(o.second) : eq;
        }

        @Override
        public String toString()
        {
                return "\nedge {" + first + ", " + second + "}";
        }
}
