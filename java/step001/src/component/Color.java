package component;

public class Color extends ComponentNode {
    private int[] color = new int[4];

    public Color(int r, int g, int b, int opacity) {
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = opacity;
    }
    public Color(int r, int g, int b) {
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = (byte)255;
    }
    public int getColor() {
        return (color[3] << 24) | (color[2] << 16) | (color[1] << 8) | color[0];
    }
    public int setColor(int packedColor) {
        int prev = getColor();

        color[0] = (packedColor >>  0) & 255;
        color[1] = (packedColor >>  8) & 255;
        color[2] = (packedColor >> 16) & 255;
        color[3] = (packedColor >> 24) & 255;

        return prev;
    }
    @Override
    public void apply() {
    }
}
