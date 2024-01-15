package component;

import service.Utils;

public class Decay extends Opacity {
        private float lifeTime;
        private float decayTime;
        private double lastTime;
        private int currentStep;

        public Decay(Painter painter, float lifeTime, float decayTime)
        {
                super(painter);

                this.lifeTime = lifeTime;
                this.decayTime = decayTime;
                lastTime = Utils.getTime();
                currentStep = 0;
        }

        @Override
        protected Short action()
        {
                double now = Utils.getTime();
                float delta = (float) (now - lastTime);

                if (delta < (lifeTime - decayTime))
                        return 1;

                if (delta > lifeTime) {
                        setOpacity(0.f);
                        ;

                        return 0;
                }

                double stepAngle = Math.toRadians(90. * Utils.getDeltaTime() / decayTime);
                float opa = (float) Math.cos(stepAngle * currentStep++);

                setOpacity(opa * getInitialOpacity());

                return 1;
        }
}
