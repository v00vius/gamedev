package types;

public class Color {
    private int[] color = new int[4];

    public Color() {
        set(255, 255, 255, 255);
    }
    public Color(Color c) {
        for (int i = 0; i < color.length; i++)
            color[i] = c.color[i];
    }
    public Color(int c) {
        set(c);
    }
    public Color(int r, int g, int b, int opacity) {
        set(r, g, b, opacity);
    }
    public Color(int r, int g, int b) {
        set(r, g, b, 255);
    }

    public int get() {
        return (color[3] << 24) | (color[2] << 16) | (color[1] << 8) | color[0];
    }

    public int set(int r, int g, int b, int opacity) {
        int prev = get();

        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = opacity;

        return prev;
    }

    public int set(int packedColor) {
        return set((packedColor >>  0) & 255,
                        (packedColor >>  8) & 255,
                        (packedColor >> 16) & 255,
                        (packedColor >> 24) & 255
                );
    }

    public int getR() {
        return color[0];
    }

    public int getG() {
        return color[1];
    }

    public int getB() {
        return color[2];
    }

    public int getA() {
        return color[3];
    }

    public int setA(int a) {
        color[3] = a;

        return get();
    }

    public int setA(float opacityFactor) {
        int a = (int)((float)getA() * opacityFactor);

        if(a < 0)
            return setA(0);

        if(a > 255)
            return setA(255);

        return setA(a);
    }
}
