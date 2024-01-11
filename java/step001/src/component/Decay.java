package component;

public class Decay extends Opacity {
    private int lifeSteps;
    private int decaySteps;
    private float decayStep;

    public Decay(Painter painter) {
        super(painter);

        lifeSteps = 0;
        decaySteps = 0;
        decayStep = 0.f;
    }

    public int decay(float lifeTime, float decayTime, float deltaTime) {
        lifeSteps = (int) (0.5f + lifeTime / deltaTime);
        decaySteps = (int) (0.5f + decayTime / deltaTime);
        decayStep = getOpacity() * deltaTime / decayTime;

        if(decaySteps > lifeSteps)
            decaySteps = lifeSteps;

        return lifeSteps;
    }
    @Override
    protected Short action() {
        if(lifeSteps <= 0)
            return 0;

        --lifeSteps;

        if(lifeSteps <= decaySteps) {
            float opa = getOpacity();

            opa -= decayStep;

            if(opa < 0.f)
                opa = 0.f;

            setOpacity(opa);
        }

        return 1;
    }
}
