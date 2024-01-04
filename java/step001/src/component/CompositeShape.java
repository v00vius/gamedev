package component;

import types.ListNode;

public class CompositeShape extends Shape {
    private ListNode shapes;

    public CompositeShape() {
        shapes = new ListNode();
    }
}
