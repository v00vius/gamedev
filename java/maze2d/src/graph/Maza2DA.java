package graph;

import typedefs.Bits;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maza2DA {
private int cols;
private int rows;
private Bits visited;
private List<Integer> wave;
private int x;
private int y;
static private int[] dx = { 1, 0, -1, 0};
static private int[] dy = { 0, 1, 0, -1};
static private Random random = new Random(3);

public Maza2DA(int cols, int rows)
{
        this.cols = cols;
        this.rows = rows;

        visited = new Bits(this.cols, this.rows);
        wave = new ArrayList<>();

        init();
}
private void init()
{
        visited.clear();
        x = y = 0;
        visited.set(x, y);
}

public boolean wave()
{
        for (int i = 0; i < 4; ++i) {
                int px = x + dx[i];
                int py = y + dy[i];

                if(px < 0 || px >= cols ||
                   py < 0 || py >= rows)
                        continue;

                if(visited.get(px, py))
                        continue;

                int id = index(px, py);

                wave.add(id);
        }

        if(wave.isEmpty())
                return false;

        int i;

        do {
                i = random.nextInt(wave.size());
                i = wave.remove(i);
        } while (visited.get(i));


        visited.set(i);

        x = i % cols;
        y = i / cols;

        return true;
}

private int index(int i, int j)
{
        return j * cols + i;
}
}
