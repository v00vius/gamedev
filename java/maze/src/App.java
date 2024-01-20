

public class App {
public static void main(String[] args)
{
        Maze2D maze = new Maze2D(1_000, 1_000);
        int totalIterations = 3;
        long avg = 0;

        for (int i = 0; i < totalIterations; ++i) {
                long start = System.currentTimeMillis();
                int p = maze.init(0, 0);

                while (p != -1) {
                        p = maze.step(p);
                }

                long end = System.currentTimeMillis() - start;

//                System.out.println(maze);

                avg += end;
                System.out.println(String.format("%03d) Delta time %s ms, %d edges", i, end, maze.getGraph().size()));

        }

        System.out.println(String.format("AVG Delta time %d ms (on %d iterations)", avg / totalIterations, totalIterations));

}
}
