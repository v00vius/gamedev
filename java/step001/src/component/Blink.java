package component;

public class Blink extends Opacity {
    private float step;

    public Blink(Painter painter) {
        super(painter);

        step = 0.f;
    }

    public void blink(float period, float deltaTime) {
        step = getOpacity() * 2.f * deltaTime / period;
    }

    @Override
    protected Short action() {
        float opacity = getOpacity();

        opacity -= step;

        if(opacity < 0.f) {
            opacity = 0.f;
            step = -step;
        }
        else if(opacity > 1.f) {
            opacity = 1.f;
            step = -step;
        }

        setOpacity(opacity);

        return 1;
    }
}
