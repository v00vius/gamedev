package component;

public class ComponentList extends ComponentNode {
    public ComponentList() {
        this.setNext(this);
    }
    public boolean isEmpty() {
        return this.getNext() == this;
    }

    public ComponentNode insert(ComponentNode node) {
        node.setNext(this.getNext());
        this.setNext(node);

        return this;
    }

    public boolean remove(ComponentNode node) {
        ComponentNode start = this;

        while(this != start.getNext()) {
            if(start.getNext() == node) {
                start.setNext(node.getNext());
                node.setNext(null);

                return true;
            }

            start = start.getNext();
        }

        return false;
    }

    @Override
    public void apply() {
        ComponentNode node = this.getNext();

        while(node != this.getNext()) {
            node.apply();
            node = node.getNext();
        }
    }
}
