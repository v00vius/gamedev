package component;

public class ComponentList extends Component {
    public ComponentList() {
        super();
        insertionCount = 0;
        removalCount = 0;
    }

    public int size() {
        return insertionCount - removalCount;
    }
    public boolean isEmpty() {
        // return this.getNext() == this;
        return size() == 0;
    }
    public int getInsertionCount() {
        return insertionCount;
    }
    public int getRemovalCount() {
        return removalCount;
    }
    public Component insert(Component node) {
        node.setNext(this.getNext());
        this.setNext(node);
        ++insertionCount;

        return this;
    }
    public Component remove(Component node) {
        Component start = this;

        while(this != start.getNext()) {
            if(start.getNext() == node) {
                start.setNext(node.getNext());
                node.setNext(node);
                ++removalCount;

                return start;
            }

            start = start.getNext();
        }

        return node;
    }

    private int insertionCount;
    private int removalCount;
}
