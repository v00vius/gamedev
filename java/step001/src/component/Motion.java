package component;

import types.Context;
import types.Node;

public class Motion implements Context{
    @Override
    public void workOn(Node node) {
        ((Shape)node).move();
    }
}
