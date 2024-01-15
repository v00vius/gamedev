package component;

import types.Enabled;

import java.util.LinkedList;
import java.util.List;

public abstract class Component extends Enabled {
        static private int creationCount;

        public Component()
        {
                super();
                ++creationCount;
        }

        public Short frame()
        {
                return isEnabled() ? action() : 1;
        }

        static public int createdTotal()
        {
                return creationCount;
        }

        protected abstract Short action();
}
