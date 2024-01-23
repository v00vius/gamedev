package gui;

public class Painter {
private PaintContext context;

public void setContext(PaintContext context)
{
        this.context = context;
}

public PaintContext getContext()
{
        return context;
}

public void rectangle(float x, float y, float width, float height, int color)
{
        float p1x = context.position.x + x;
        float p1y = context.position.y + y;
        float p2x = p1x + width;
        float p2y = p1y + height;

        context.draw.addRectFilled(p1x, p1y, p2x, p2y, color);
}
public void grid(float x, float y, int rows, int cols, float cellSize, int color)
{
        float size = (float)cols * cellSize;
        float pos = y - 1.f;

        for (int i = 0; i < rows + 1; ++i) {
                rectangle(x , pos,
                        size, 2.f, color
                );

                pos += cellSize;
        }

        size = (float)rows * cellSize;
        pos = x - 1.f;

        for (int i = 0; i < cols + 1; ++i) {
                rectangle(pos, y,
                        2.f, size, color
                );

                pos += cellSize;
        }
}
}
