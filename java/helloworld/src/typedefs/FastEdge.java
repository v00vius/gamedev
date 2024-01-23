package typedefs;

public class FastEdge {
static private final int HALF_SIZE = Long.SIZE / 2;
static public long create(int src, int dst)
{
        return ((long)dst << HALF_SIZE) | src;
}
static public int getSrc(long edge)
{
        return (int)edge;
}
static public int getDst(long edge)
{
        return (int)(edge >>> HALF_SIZE);
}
static public long setSrc(long edge, int src)
{
        return (edge & 0xFFFFFFFF00000000L) | src;
}
static public long setDst(long edge, int dst)
{
        return edge | ((long)dst << HALF_SIZE);
}
static public String toString(long edge)
{
        int src = getSrc(edge);
        int dst = getDst(edge);

        return "(" + src + ',' + dst + ')';
}

public static void main(String[] args)
{
        for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                        long edge = create(i, j);
                        int src = getSrc(edge);
                        int dst = getDst(edge);

                        System.out.println(toString(edge));
                        System.out.println("src=" + src);
                        System.out.println("dst=" + dst);

                        edge = setDst(0, dst);
                        edge = setSrc(edge, src);
                        System.out.println("recreated one:" + toString(edge) + '\n');
                }
        }
}
}
