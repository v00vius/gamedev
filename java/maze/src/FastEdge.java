public class FastEdge {
static private final int HALF_SIZE = Integer.SIZE / 2;
static public int create(int src, int dst)
{
        return (dst << HALF_SIZE) | src;
}
static public int getSrc(int edge)
{
        return edge & 0x0000FFFF;
}
static public int getDst(int edge)
{
        return edge >>> HALF_SIZE;
}
static public int setSrc(int edge, int src)
{
        return (edge & 0xFFFF0000) | src;
}
static public int setDst(int edge, int dst)
{
        return edge | (dst << HALF_SIZE);
}
static public String toString(int edge)
{
        int src = getSrc(edge);
        int dst = getDst(edge);

        return "(" + src + ',' + dst + ')';
}

public static void main(String[] args)
{
        for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                        int edge = create(i, j);
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
