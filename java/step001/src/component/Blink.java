package component;

import service.Utils;

public class Blink extends Opacity {
    private float period;
    private int currentStep;

    public Blink(Painter painter, float period) {
        super(painter);

        this.period = period;
        currentStep = 0;
    }

    @Override
    protected Short action() {
        double stepAngle = Math.toRadians(180. * Utils.getDeltaTime()/ period);
        float opa = (float) Math.cos(stepAngle * currentStep++);

        setOpacity(opa * getInitialOpacity());

        return 1;
    }
}
