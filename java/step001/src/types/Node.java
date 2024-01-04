package types;

public abstract class Node {
    private Node next;
    private int mask;

    public Node() {
        this.next = null;
        this.mask = 1;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public boolean isAlive() {
        return mask != 0;
    }
}
