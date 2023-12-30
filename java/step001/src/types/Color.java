package types;

public class Color {
    private int[] color = new int[4];

    public Color() {
        setColor(255, 255, 255, 255);
    }
    public Color(Color c) {
        for (int i = 0; i < color.length; i++)
            color[i] = c.color[i];
    }
    public Color(int c) {
        setColor(c);
    }
    public Color(int r, int g, int b, int opacity) {
        setColor(r, g, b, opacity);
    }
    public Color(int r, int g, int b) {
        setColor(r, g, b, 255);
    }

    public int getColor() {
        return (color[3] << 24) | (color[2] << 16) | (color[1] << 8) | color[0];
    }
    public int setColor(int r, int g, int b, int opacity) {
        int prev = getColor();

        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = opacity;

        return prev;
    }
    public int setColor(int packedColor) {
        return setColor((packedColor >>  0) & 255,
                        (packedColor >>  8) & 255,
                        (packedColor >> 16) & 255,
                        (packedColor >> 24) & 255
                );
    }
}
