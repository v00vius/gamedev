package component;

import component.mesh.Mesh;
import types.Vector2;

public class Position extends Component {
        static public Position NIL = createEmpty();
        private Mesh mesh;
        private Vector2 coordinate;

        static private Position createEmpty()
        {
                return new Position(Mesh.NIL, 0.f, 0.f);
        }

        public Position(Mesh mesh, float x, float y)
        {
                this.mesh = mesh;
                this.coordinate = new Vector2();
                setCoordinate(x, y);
        }

        public Vector2 setCoordinate(float x, float y)
        {
                return coordinate.set(x, y);
        }

        public Vector2 getCoordinate()
        {
                return coordinate;
        }

        @Override
        protected Short action()
        {
                return 0;
        }

        public Mesh getMesh()
        {
                return mesh;
        }
}
