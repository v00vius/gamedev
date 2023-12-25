package component;

public abstract class ComponentNode implements Component {
    private ComponentNode next;

    public ComponentNode() {
        next = this;
    }

    public ComponentNode getNext() {
        return next;
    }

    public void setNext(ComponentNode next) {
        this.next = next;
    }

//    @Override
//    public void apply() {
//
//    }
}
