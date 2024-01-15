package types;

public class Precision {
        private float epsilon;

        static public Precision create(float eps)
        {
                return new Precision(eps);
        }

        private Precision(float epsilon)
        {
                this.epsilon = epsilon;
        }

        public int compare(float f1, float f2)
        {
                if (Math.abs(f1 - f2) <= epsilon)
                        return 0;

                return f1 < f2 ? -1 : 1;
        }

        public boolean equals(float f1, float f2)
        {
                return 0 == compare(f1, f2);
        }
}
