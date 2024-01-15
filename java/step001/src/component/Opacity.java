package component;

import imgui.ImGui;

import java.util.function.Function;

public abstract class Opacity extends Component {
        static public Opacity NIL = createEmpty();
        protected Painter painter;
        private float initialOpacity;

        static private Opacity createEmpty()
        {
                return new Blink(Painter.NIL, 24.f * 3600.f);
        }

        public Opacity(Painter painter)
        {
                super();

                this.painter = painter;
                initialOpacity = getOpacity();
        }

        public void setOpacity(float opa)
        {
                painter.setOpacityFactor(opa);
        }

        public float getOpacity()
        {
                return painter.getOpacityFactor();
        }

        public float getInitialOpacity()
        {
                return initialOpacity;
        }
}
