package types;

import component.Action;

public class ListNode extends Node {
    private int insertionCount;
    private int removalCount;

    public ListNode() {
        super();

        setNext(this);
        insertionCount = 0;
        removalCount = 0;
    }

    public int size() {
        return insertionCount - removalCount;
    }

    public boolean isEmpty() {
        return this.getNext() == this;
        // return size() == 0;
    }

    public int getInsertionCount() {
        return insertionCount;
    }

    public int getRemovalCount() {
        return removalCount;
    }

    public Node insert(Node node) {
        node.setNext(this.getNext());
        this.setNext(node);
        ++insertionCount;

        return this;    // assert this.next == node
    }

    public Node remove(Node node) {
        Node begin = this;

        while(this != begin.getNext()) {
            if(node == begin.getNext()) {
                begin.setNext(node.getNext());
                node.setNext(null);
                ++removalCount;

                return begin;
            }

            begin = begin.getNext();
        }

        return node;
    }

    void apply(Action ctx) {
        Node begin = this;

        while(this != begin.getNext()) {
            Node nextNode = begin.getNext();

            if(!nextNode.isAlive()) {
                begin.setNext(nextNode.getNext());
                nextNode.setNext(nextNode);
                ++removalCount;

                continue;
            }

//            ctx.action(nextNode);
            begin = nextNode;
        }                  
    }
}
